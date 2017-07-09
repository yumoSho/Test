package com.glanway.jty.service.product.impl;

import com.glanway.jty.service.product.GoodsSearchService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author vacoor
 * @beta
 */
@Service
public class SolrGoodsSearchServiceImpl implements GoodsSearchService {
    private static final Logger LOG = LoggerFactory.getLogger(SolrGoodsSearchServiceImpl.class);

    public static final String MIX_VAL_DELIMITER = "::";        // 混合值分隔符  123::中文::345::语言
    public static final String CAT_FIELD = "cat";               // 分类字段
    public static final String IMGS_FIELD = "imgs";               //图片字段
    public static final String LABEL_ID_FIELD = "labelId";        //标签ID字段
    public static final String LABEL_PATH_FIELD = "labelPath";    //标签图片字段
    public static final String CAT_VAL_PREFIX = CAT_FIELD + "_";
    public static final String PROVINCE_FIELD = "province";       // 银行字段
    public static final String PROVINCE_VAL_PREFIX = PROVINCE_FIELD + "_";

    public static final String BRAND_FIELD = "brand";           // 品牌字段
    public static final String BRAND_VAL_FIELD = "brandName";   // 品牌字段
    public static final String PRICE_FIELD = "price";           // 价格字段
    public static final String DYN_PROPS_FIELD = "props";       // 动态属性列表字段
    public static final String DYN_PROP_PREFIX = "prop_";       // 动态属性字段前缀
    public static final String DYN_PROP_VAL_PREFIX = "prop_v_"; // 动态属性值字段前缀
    public static final String SORT_DELIMITER = "-";
    public static final String SORT_DESC = "desc";
    public static final String PROP_VALUE_PATH_DELIMITER = ":";
    public static final String CAT_PATH_PREFIX = "cat_path_";

    @Autowired
    private SolrServer solrServer;
    private boolean catStrict = true;

    public SolrGoodsSearchServiceImpl() {
        String[] urls = {};
        solrServer = new LBHttpSolrServer(null, urls);
    }

    /**
     * 商品筛选说明:
     * <p/>
     * 属性筛选的入口是分类, 只有选择了分类才允许进行属性筛选
     * 1. 如果没有选择分类, 大多数做法是尝试获取最佳匹配分类z,(匹配度大于某个值?具体算法不清楚, 好像都会优先匹配分类)
     * 无法匹配最佳分类, 则只允许通过 category, brand, price 进行筛选
     * 2. 当存在具体分类(匹配到或给定)， 则在上面基础上允许通过商品属性筛选
     *
     * @param kw        搜索关键字
     * @param cat       商品分类
     * @param brand     商品品牌
     * @param provinceId 省id
     * @param sPrice    开始价格
     * @param ePrice    结束价格
     * @param ppaths    商品属性路径, 格式 属性key:属性值:key
     * @param offset    记录偏移量
     * @param rows      记录总数
     * @param orders    排序, 格式 field-dir, eg: price-desc
     * @return
     */
    @Override
    public Map<String, Object> search(String kw, String cat, String brand, Long provinceId,Long labelId, Double sPrice, Double ePrice, String[] ppaths, int offset, int rows, String[] orders) {
        Map<String, Object> ret = Maps.newHashMap();
        ppaths = null != ppaths ? ppaths.clone() : new String[0];
        orders = null != orders ? orders.clone() : new String[0];
        boolean hasKeyword = StringUtils.hasText(kw);   // 是否存在关键词
        boolean hasCate = StringUtils.hasText(cat);     // 是否存在分类

        // 没有任何可搜索条件
            if (!hasCate && !hasKeyword && 1 > ppaths.length) {
            // throw new IllegalArgumentException("no query condition found! (keyword or category or brand or property path)");
        }

        offset = 0 > offset ? 0 : offset;   // 结果偏移量
        rows = 0 > rows ? 0 : rows;         // 返回结果数
        // 转义Solr 特殊字符: * + – && || ! ( ) { } [ ] ^ " ~ * ? : \ 空格 (空格表示 OR),
        kw = hasKeyword ? ClientUtils.escapeQueryChars(kw) : "*:*";     // 如果没有关键词, 则查询所有记录
        List<String> groupFields = Lists.newArrayList();

        try {
            final SolrQuery query = new SolrQuery(kw).setFacet(true)
                    .setStart(offset)
                    .setRows(rows)
                    .setIncludeScore(true); // 包含匹配打分

            // 排序处理, 格式 {field}-{dir}
            for (String order : orders) {
                if (StringUtils.hasText(order)) {
                    String[] sort = order.split(Pattern.quote(SORT_DELIMITER), 2);
                    String field = sort[0];
                    String dir = sort[sort.length - 1];
                    query.addSort(field, SORT_DESC.equals(dir) ? SolrQuery.ORDER.desc : SolrQuery.ORDER.asc);
                }
            }
            if(null != provinceId && 0l != provinceId){
                query.addFilterQuery(PROVINCE_FIELD+":"+provinceId);
            }
            if(null != labelId && 0l != labelId){
                query.addFilterQuery(LABEL_ID_FIELD+":"+labelId);
            }

            // 分组: 品牌筛选
            groupFields.add(BRAND_FIELD);
            // 如果指定品牌, 添加品牌条件
            if (StringUtils.hasText(brand)) {
                query.addFilterQuery(BRAND_FIELD + ":" + ClientUtils.escapeQueryChars(brand));
            }

            // 价格区间筛选
            if (null != sPrice || null != ePrice) {
                query.addFilterQuery(String.format("%s:[%s TO %s]", PRICE_FIELD, null==sPrice?"*":sPrice, null==ePrice?"*":ePrice));
            }

            // 属性筛选
            for (String propPath : ppaths) {
                if (StringUtils.hasText(propPath)) {
                    String[] pv = propPath.split(Pattern.quote(PROP_VALUE_PATH_DELIMITER), 2);
                    // 如果 property path 有效
                    if (2 == pv.length) {
                        query.addFilterQuery(DYN_PROP_PREFIX + ClientUtils.escapeQueryChars(pv[0]) + ":" + ClientUtils.escapeQueryChars(pv[1]));
                    }
                }
            }



            // 添加分类 facet
            query.addFacetField(CAT_FIELD);

            // 包含分类, 添加对分类下属性的分组 (当没有选择分类时不显示属性)
            if (hasCate) {
                query.addFilterQuery(CAT_FIELD + ":" + ClientUtils.escapeQueryChars(cat));  // 添加分类过滤
                //
                // update 20150414 统计子分类中商品数量
                query.addFacetField(CAT_PATH_PREFIX + cat);

                // 通过 facet 获取所有属性
                SolrQuery propsQuery = new SolrQuery(query.getQuery()).setFilterQueries(query.getFilterQueries())
                        .setFacet(true)
                        .setRows(0)
                        .addFacetField(DYN_PROPS_FIELD);
                QueryResponse propsResp = solrServer.query(propsQuery, SolrRequest.METHOD.POST);
                FacetField props = propsResp.getFacetField(DYN_PROPS_FIELD);
                for (FacetField.Count count : props.getValues()) {
                    String prop = count.getName();
                    long size = count.getCount();
                    // fq = "{!tag=aa}" + fq;  // 给 fq 添加 tag = aa, 用于 facet 时排除该 filter
                    // propKey = "{!ex=aa}" + propKey; facet 时排除 tag = aa 的 filter
                    if (0 < size) {
                        groupFields.add(DYN_PROP_PREFIX + prop); // 添加属性分组
                    }
                }
            } else if (catStrict) {
                // 必须选择分类模式下, 当没有分类默认为全部分类
                query.addFilterQuery(CAT_FIELD + ":*");
            }

            // ----------------------  处理查询结果 -----------------------------

            // TODO 高亮
            query.setHighlight(true);
            query.setHighlightSimplePre("<em style=\"color:red;\">");
            query.setHighlightSimplePost("</em>");
            query.addHighlightField("title");
            final QueryResponse response = solrServer.query(query);

            // TODO 子分类 facet  (当前分类为父分类时需要进一步选择子分类)
            Map<String, String> children = Maps.newHashMap();
            FacetField facetField = response.getFacetField(CAT_PATH_PREFIX + cat);
            if (null != facetField) {
                List<FacetField.Count> countValues = facetField.getValues();
                for (FacetField.Count countValue : countValues) {
                    String name = countValue.getName();
                    long count = countValue.getCount();
                    if (0 < count) {
                        String[] pairs = name.split(Pattern.quote(MIX_VAL_DELIMITER), 2);
                        if (2 == pairs.length) {
                            children.put(pairs[0], pairs[1] + "(" + count + ")");
                        }
                    }
                }
            }
            ret.put("children", children);  // 子分类
            /*
            FacetField facetField = response.getFacetField(cat);
            if (null != facetField) {
                List<FacetField.Count> countValues = facetField.getValues();
                for (FacetField.Count countValue : countValues) {
                    String name = countValue.getName();
                    long count = countValue.getCount();
                }
            }
            */

            // 获取当前条件下的相关分类信息 rel_cats
            Map<String/*cat id*/, NamedKV<String, NamedKV<String, ?>>/*cat*/> allCats = Maps.newHashMap();
            Map<String/*cat id*/, NamedKV<String, NamedKV<String, ?>>/*cat*/> pCats = Maps.newHashMap();

            // 获取所有的相关分类 ID
            List<FacetField.Count> catCountValues = response.getFacetField(CAT_FIELD).getValues();
            for (FacetField.Count catCountValue : catCountValues) {
                String catId = catCountValue.getName();
                long catCount = catCountValue.getCount();

                if (1 > catCount) { // 该分类商品数量 < 1
                    continue;
                }

                /**
                 * 查询该分类 id 的名称及父名称
                 * 存储为
                 *   cat        cat_[cat_id]
                 * [cat id]     [cat name]:[parent id]:[parent name]
                 */
                String catTextKey = CAT_VAL_PREFIX + catId;

                // 查询分类名称及父分类
                SolrQuery catQuery = new SolrQuery(catTextKey + ":*").setRows(1);  // only one
                SolrDocumentList results = solrServer.query(catQuery).getResults();
                if (0 < results.size()) {
                    SolrDocument doc = results.get(0);
                    // 获取分类名称及父分类: [cat name]::[parent id]::[parent name]
                    String text = (String) doc.get(catTextKey);
                    String[] pairs = text.split(Pattern.quote(MIX_VAL_DELIMITER), 3);

                    NamedKV<String, NamedKV<String, ?>> c = NamedKV.create(catId, pairs[0]);
                    allCats.put(catId, c);  // 添加当前分类到所有分类列表

                    if (2 < pairs.length) {
                        // 获取父分类
                        NamedKV<String, NamedKV<String, ?>> p = allCats.get(pairs[1]);
                        if (null == p) {
                            p = NamedKV.create(pairs[1], pairs[2]);
                            allCats.put(p.getKey(), p);
                            // pCats.put(p.getKey(), p);
                        }
                        p.addValue(c);              // 添加当前分类到父分类中
                        pCats.put(p.getKey(), p);   // 当前分类已经是父分类, 添加到父分类列表
                    } else { // 没有父分类, 则当前分类就是父分类
                        pCats.put(catId, c);
                    }
                }
            }

            // ----------------------------------------------------------

            // 执行分组查询(品牌, 属性)
            SolrQuery groupQuery = new SolrQuery(query.getQuery()).addFilterQuery(query.getFilterQueries())
                    .setParam("group", true)
                    .setParam("group.limit", "1")   // 每个分组内返回1条记录(每个品牌下返回几条记录)
                    .setParam("group.field", groupFields.toArray(new String[groupFields.size()]))
                    .setRows(1024);                 // 此时 rows 用于限定返回多少个组(eg: 对品牌分组最多返回多少个品牌)

            // 存放转换后的所有分组属性
            Map<String/*key*/, NamedKV<String, NamedKV<String, ?>>/*props*/> groupedProps = Maps.newHashMap();

            GroupResponse groupResp = solrServer.query(groupQuery).getGroupResponse();
            for (GroupCommand fieldGroup : groupResp.getValues()) {     // 遍历所有分组信息
                String groupName = fieldGroup.getName();                // 分组字段名
                List<Group> groups = fieldGroup.getValues();

                NamedKV<String, NamedKV<String, ?>> namedProp = null;
                for (Group g : groups) {                    // 遍历 groupName 所有分组
                    String groupValue = g.getGroupValue();  // 组名称, eg: 三星
                    SolrDocumentList docs = g.getResult();  // 该组下的文档

                    if (null == groupValue) {
                        continue;
                    }

                    if (0 < docs.size()) {
                        SolrDocument doc = docs.get(0);
                        // 如果当前是以品牌分组, 获取品牌名称
                        if (BRAND_FIELD.equals(groupName)) {
                            String brandName = (String) doc.get(BRAND_VAL_FIELD);

                            if (null == namedProp) {
                                namedProp = NamedKV.create(BRAND_FIELD, BRAND_FIELD);
                            }
                            namedProp.addValue(NamedKV.create(groupValue, brandName));
                            continue;
                        }

                        // 如果是以属性进行分组 (groupName 格式为 prop_{prop_id})
                        if (groupName.startsWith(DYN_PROP_PREFIX)) {
                            // 格式: {prop_name}_{prop_value_name}
                            String text = (String) doc.get(DYN_PROP_VAL_PREFIX + groupValue);
                            String[] pairs = text.split(Pattern.quote(MIX_VAL_DELIMITER), 2);
                            String propKey = groupName.substring(DYN_PROP_PREFIX.length()); // 属性 Key
                            String propName = pairs[0];                    // 属性名称
                            String valueName = pairs[pairs.length - 1];    // 属性值名称

                            if (null == namedProp) {
                                namedProp = NamedKV.create(propKey, propName);
                            }
                            namedProp.addValue(NamedKV.create(groupValue, valueName));
                        }
                    }
                }
                if (null != namedProp) {
                    groupedProps.put(namedProp.getKey(), namedProp);
                }
            }
            ret.put("sp",sPrice);
            ret.put("ep",ePrice);
            ret.put("labelId",labelId);
            ret.put("cat", allCats.get(cat));       // 当前搜索的分类
            ret.put("pCats", pCats);                // 所有父分类
            ret.put("allCats", allCats);            // 所有分类
            ret.put("groupedProps", groupedProps);  // 所有属性分组   (包括品牌)
            ret.put("results", response.getResults());  // 搜索结果
            // TODO 高亮
            ret.put("hl", response.getHighlighting());

            // 擦, 额外翻译下字段
            List<NamedPropertyPath> namedPPaths = Lists.newArrayList();
            for (String ppath : ppaths) {
                String[] kv = ppath.split(Pattern.quote(":"), 2);
                NamedKV<String, NamedKV<String, ?>> namedProp = groupedProps.get(kv[0]);

                if (null == namedProp) {
                    continue;
                }
                for (NamedKV<String, ?> value : namedProp.getValues()) {
                    if (kv[kv.length - 1].equals(value.getKey())) {
                        namedPPaths.add(new NamedPropertyPath(ppath, namedProp.getName() + ":" + value.getName()));
                        break;
                    }
                }
            }
            ret.put("ppaths", namedPPaths);

            // 简单的还可以, 复杂的还得自己来
            // List<IndexedGoods> beans = response.getBeans(IndexedGoods.class);
        } catch (SolrServerException sse) {
            sse.printStackTrace();
            LOG.error("invoke solr server error", sse);
        }

        return ret;

    }





    public static class NamedPropertyPath {
        private final String path;
        private final String name;

        public NamedPropertyPath(String path, String name) {
            this.path = path;
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public String getName() {
            return name;
        }
    }

    /* *********************************
     *        getter / setter
     * *********************************/

    public SolrServer getSolrServer() {
        return solrServer;
    }

    public void setSolrServer(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    /**
     * 命名键值
     *
     * @param <K>
     * @param <V>
     */
    public static class NamedKV<K, V> {
        private final K key;
        private final String name;
        private List<V> values;

        public static <K, V> NamedKV<K, V> create(K key, String name) {
            return new NamedKV<K, V>(key, name);
        }

        public static <K, V> NamedKV<K, V> create(K key, String name, List<V> values) {
            return new NamedKV<K, V>(key, name, values);
        }

        public NamedKV(K key, String name) {
            this(key, name, null);
        }

        public NamedKV(K key, String name, List<V> values) {
            this.key = key;
            this.name = name;
            this.values = values;
        }

        public K getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public List<V> getValues() {
            values = null == values ? Lists.<V>newArrayList() : values;
            return values;
        }

        public NamedKV addValue(V v) {
            getValues().add(v);
            return this;
        }
    }

}
