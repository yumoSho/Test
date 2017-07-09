package com.glanway.jty.service.product.impl;


import com.glanway.gone.util.StringUtils;
import com.glanway.jty.dao.product.IndexingGoodsDao;
import com.glanway.jty.dto.IndexedGoods;
import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.entity.product.*;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.service.product.IndexingService;
import com.glanway.jty.utils.StringUtil;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.beans.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.glanway.jty.service.product.impl.SolrGoodsSearchServiceImpl.*;

/**
 *
 * @author vacoor
 */
@Service
public class IndexingServiceImpl extends IndexingSupport implements IndexingService {
    private static final Logger LOG = LoggerFactory.getLogger(IndexingServiceImpl.class);

    @Autowired
    private GoodsService goodsService;

    private static class GoodsIndexedBean {
        @Field
        private String id;      // 商品id
        @Field
        private String sn;   // 商品编码
        @Field
        private String title;   //标题
        @Field
        private String intro;   //副标题
        @Field
        private String image;   // 主图
        @Field
        private String price;   // 销售价格
        @Field
        private Date onSellDate;   // 上架时间
        @Field
        private String weight;//重量
        @Field("sales")
        private Long sales;         // 销量
        @Field("brand")
        private String brandId; //品牌id
        @Field("brandName")
        private String brandName;   // 品牌名称
        @Field("cat")
        private List<String> catIds = Lists.newArrayList(); // 分类
        @Field("cat_*")
        private Map<String, String> catsAndParent = Maps.newHashMap();  // 分类
        @Field("province")
        private List<String> provinceIds = Lists.newArrayList(); // 省
        @Field("province_*")
        private Map<String, String> province = Maps.newHashMap();  //
        @Field("labelPath")
        private String labelPath;//标签图片路径
        @Field("labelId")
        private String labelId;//标签图片id
        @Field("imgs")
        private List<String> imgs = Lists.newArrayList(); // 图片集合
        @Field("props")
        private List<Long> propKeys = Lists.newArrayList();   // 属性
        @Field("prop_*")
        private Map<String, Long> propAndValueMap = Maps.newHashMap();    // 属性
        @Field("prop_v_*")
        private Map<String, String> propAndValueNames = Maps.newHashMap();  // 属性值

    }

    @Autowired
    private IndexingGoodsDao indexingGoodsDao;

    @Override
    protected Object getIndexedBean(String id) {
        Long lid = null;
        try {
            lid = Long.valueOf(id);
        } catch (NumberFormatException nfe) {
            // ignore
        }
        return null != lid ? toIndexedBean(indexingGoodsDao.find(lid)) : null;
    }

    @Override
    protected long countIndexedBeans() {
        return indexingGoodsDao.count();
    }

    @Override
    protected List<?> getPagedIndexedBeans(int offset, int size) {
        Map<String, Object> paramsMap = Maps.newHashMap();

        paramsMap.put(IndexingGoodsDao.OFFSET, offset);
        paramsMap.put(IndexingGoodsDao.MAX_RESULTS, size);
        List<IndexedGoods> many = indexingGoodsDao.findMany(paramsMap);
        return Lists.transform(indexingGoodsDao.findMany(paramsMap), new Function<IndexedGoods, Object>() {
            @Override
            public Object apply(IndexedGoods indexedGoods) {
                return toIndexedBean(indexedGoods);
            }
        });
    }

    protected Object toIndexedBean(IndexedGoods g) {//********************
        if (null == g || null == g.getPrice()) {
            return null;
        }
        GoodsIndexedBean bean = new GoodsIndexedBean();
        bean.id = g.getId() + "";
        bean.sn = g.getSn();
        bean.title = g.getTitle();
        if (!StringUtils.hasText(bean.title)) {
            bean.title = g.getProductTitle();
        }
        bean.intro = g.getIntro();
        if (!StringUtils.hasText(bean.intro)) {
            bean.intro = g.getProductTitle();
        }
        bean.image = g.getImage();


        Label label= g.getLabel();
        if(null != label && StringUtil.notEmpty(label.getLabelPath()) ){
            bean.labelId = label.getId().toString();
            bean.labelPath = label.getLabelPath();
        }

        bean.price = g.getPrice().toString();
        bean.onSellDate = g.getOnSellDate();
        bean.weight = g.getWeight().toString();
        bean.sales = g.getSales();
        bean.brandId = g.getBrandId().toString();
        bean.brandName = g.getBrandName();

        // 添加所有分类
        List<Category> cats = g.getCats();
        if (null != cats) {
            for (Category cat : cats) {
                /*
                for (; null != cat; cat = cat.getParent()) {
                    Long catId = cat.getId();
                    String catName = cat.getName();
                    Category p = cat.getParent();
                    String value = null == p ? catName : catName + MIX_VAL_DELIMITER + p.getId() + MIX_VAL_DELIMITER + p.getName();

                    bean.catIds.add(catId + "");
                    bean.catsAndParent.put(CAT_VAL_PREFIX + catId, value);
                }
                */
                String path = cat.getPath();
                String pathNames = cat.getPathNames();
                if (null == path || null == pathNames) {
                    continue;
                }
                String[] ids = path.split(",");
                String[] names = pathNames.split("/\\$");
                int len = ids.length;
                if(names.length>1){
                    if(names[0].isEmpty()) {
                        String []namesTemp = new String[names.length-1];
                        for (int i = 1; i < names.length; i++) {
                            namesTemp[i-1]=names[i];
                        }
                        names=namesTemp;
                    }
                }

                // 不合法数据
                if (ids.length != names.length) {
                    len = Math.min(ids.length, names.length);
                }

                // for (int i = 0; i < len; i++) {
                for (int i = len - 1; i > -1; i--) {
                    String catId = ids[i];

                    if (!StringUtils.hasText(catId)) {
                        continue;
                    }

                    String catName = names[i];
                    /*
                    Category p = cat.getParent();
                    String value = null == p ? catName : catName + MIX_VAL_DELIMITER + p.getId() + MIX_VAL_DELIMITER + p.getName();
                    */
                    String value;
                    if (0 < i) {
                        String pid = ids[i - 1];
                        String pname = names[i - 1];
                        value = catName + MIX_VAL_DELIMITER + pid + MIX_VAL_DELIMITER + pname;
                    } else {
                        value = catName;
                    }

                    bean.catIds.add(catId + "");
                    bean.catsAndParent.put(CAT_VAL_PREFIX + catId, value);
                }
            }
        }
        List<ProductImg> imgs = g.getImgs();
        if(!CollectionUtils.isEmpty(imgs)){
            for (ProductImg img : imgs) {
                if(StringUtil.notEmpty(img.getPath())){
                    bean.imgs.add(img.getPath()+"");
                }
            }
        }

        //银行
        List<HatProvince> provinces = g.getProvinces();
        if (null != provinces) {
            for (HatProvince bank : provinces) {
                bean.provinceIds.add(bank.getId() + "");
                bean.province.put(PROVINCE_VAL_PREFIX + bank.getId(), bank.getProvinceName());
            }
        }


        List<AttributeValue> values = g.getPropVals();
        if (!CollectionUtils.isEmpty(values))
        {
            for (AttributeValue value : values) {
                Attribute prop = value.getAttribute();
                if (null == prop) {
                    continue;
                }

                Long sid = prop.getId();
                String sname = prop.getName();
                Long vid = value.getId();
                String firstValue = value.getValue();

                bean.propKeys.add(sid);
                bean.propAndValueMap.put(DYN_PROP_PREFIX + sid, vid);
                bean.propAndValueNames.put(DYN_PROP_VAL_PREFIX + vid, sname + MIX_VAL_DELIMITER + firstValue);
            }
        }
        return bean;
    }



    @Override
    public void updateIndex(String id, boolean delete) {
        doUpdate(id, delete);
    }


    @Override
    @PostConstruct
    public void rebuildIndex() {
        LOG.info("rebuild index....");
        doReindex();
    }

    @Override
    public void truncate() {
        doTruncate();
    }

    @Autowired
    @Override
    public void setSolrServer(SolrServer solrServer) {
        super.setSolrServer(solrServer);
    }
}
