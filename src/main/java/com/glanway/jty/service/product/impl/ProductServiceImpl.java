package com.glanway.jty.service.product.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.dao.product.*;
import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.entity.product.*;
import com.glanway.jty.entity.product.Product;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.service.product.IndexingService;
import com.glanway.jty.service.product.ProductService;
import com.glanway.jty.utils.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ucar.unidata.util.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 产品service
 */
@Service
@Transactional
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {
    public static final String DEFAULT_PRODUCT_IMAGE_PATH = "storage/images/default.jpg";

    private String defaultProductImagePath = DEFAULT_PRODUCT_IMAGE_PATH;

    private ProductDao productDao;
    @Autowired
    private ProductImageDao productImageDao;
    @Autowired
    private AttributeValueDao attributeValueDao;
    @Autowired
    private ParameterValueDao parameterValueDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private SpecValueDao specValueDao;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private IndexingService indexingService;

    @Autowired
    public void setProuductDao(ProductDao prouductDao) {
        super.setCrudDao(prouductDao);
        this.productDao = prouductDao;
    }

    /**
     * 更新或保存商品
     * @param product
     */
    @Override
    public void saveOrUpdate(Product product) {

        doSaveOrUpdate(product, null == product.getId());

    }

    /**
     * 保存商品
     * @param pro product
     * @param create 是否创建商品
     */
    protected void doSaveOrUpdate(Product pro, boolean create) {
        Product original = create ? null : find(pro.getId());
        if (!create && null == original) {
            throw new CustomException("不能发现要更新数据");
        }
        //设置是否上下架
        setIsPutway(pro, original);
        //预处理图片
        // 预处理图片, 清理无效图片信息或使用默认图片
        List<ProductImg> images = pro.getProductImgs();
        pro.setProductImgs(images = doCleanSortOrCreateDefaultImage(images, true));
        pro.setImage(0 < images.size() ? images.get(0).getPath() : null);   // 默认图片
        //属性
        List<AttributeValue> attributeValues = pro.getAttributeValues();
        //参数
        List<ParameterValue> parameterValues = pro.getParameterValues();


        List<Goods> items = null;
        if(create){
            //新增方法
            pro.setStock(null == pro.getStock() ? 0 : pro.getStock());
            pro.setAlertStock(null == pro.getAlertStock() ? 0 : pro.getAlertStock());
            pro.setPrice(null == pro.getPrice() ? new BigDecimal(0) : pro.getPrice());
            pro.setRealSales(0l);
            pro.setRealSales(null == pro.getRealSales()?0l:pro.getRealSales());
            // 预处理 SKU
            items =  doCleanOrCreateDefaultItem(pro,create);
            super.save(pro);
        }else{
            //编辑方法
            //获取原来的Goods List
            if (null != original) {

                Filters filters = Filters.create().eq("productId", original.getId());

                original.setGoods(goodsService.findMany(filters,(Pageable)null));
            }

            items = doCleanOrCreateDefaultItem(pro,create);
            super.update(pro);
        }
        List<ProductImg> originalImages = null != original ? original.getProductImgs() : null;
        List<AttributeValue> originalAttributeValues = null != original ? original.getAttributeValues() : null;
        List<ParameterValue> originalParameterValues = null != original ? original.getParameterValues() : null;
        List<Goods> originalItems = null != original ? original.getGoods() : null;


        // 保存商品 - 图片
        doSaveOrUpdateDiffImages(images, originalImages, pro, null);
        // 保存商品 - 属性值
        doSaveOrUpdateDiffAttributeValues(attributeValues, originalAttributeValues, pro);
        // 保存商品 - 参数值
        doSaveOrUpdateDiffParameterValues(parameterValues, originalParameterValues, pro);
        for (Goods g : items) {
            doCleanSortOrCreateDefaultImage(g.getProductImgs(), true);
        }
        doSaveOrUpdateDiffItems(items, originalItems, pro);
        /*-------------------------------------- 更新购买区域-------------------------------------------------------*/
        if(null !=pro.getId()){
            productDao.deletedAreasByProductId(pro.getId());
        }
        List<HatProvince> areas = pro.getAreas();
        for (HatProvince hp:areas){
            Map<String,Object> map = new  HashMap<String,Object>();
            map.put("id",null);
            map.put("hatProvince",hp);
            map.put("product",pro);
            productDao.addProductAreas(map);
        }
        /*--------------------------------------TODO 更新索引-------------------------------------------------------*/
          indexingService.rebuildIndex();


    }

    private void setIsPutway(Product pro, Product original) {
        Boolean onSell = pro.getIsPutaway();
        onSell = null != onSell && onSell;
        pro.setIsPutaway(onSell);
        // 上下架时间更新
        Boolean originalOnSell = null;
        if(null != original){
            originalOnSell = original.getIsPutaway();
        }
        if(null != originalOnSell &&originalOnSell){
            originalOnSell = true;
        }else{
            originalOnSell = false;
        }
        if (originalOnSell && !onSell) {
            //下架时间
            pro.setSalesOffDate(new Date());
        } else if (!originalOnSell && onSell) {
            // 下架 -> 上架, 更新上架时间
            pro.setRegisterDate(new Date());
        }
    }

    /**
     * <p>名称：doCleanSortOrCreateDefaultImage</p>.
     * <p>描述：清理无效图片信息, 如果没有则使用默认图片</p>
     * @author：LiuJC
     * @param images 图片
     * @param needSort 是否需要排序
     * @return 图片集合
     */
    protected List<ProductImg> doCleanSortOrCreateDefaultImage( List<ProductImg> images, final boolean needSort) {
        images = null != images ? images : Lists.<ProductImg>newArrayList();

        int order = 0;
        for (Iterator<ProductImg> it = images.iterator(); it.hasNext(); ) {
            ProductImg img = it.next();
            // 如果对象是空或者没有有效路径则清理
            if (null == img || (null == img.getId() && !StringUtils.hasText(img.getPath()))) {
                it.remove();
                continue;
            }
            if (needSort) {
                img.setSort(order++);
            }
        }
        // 没有有效图片, 使用默认图片
        // if (!images.iterator().hasNext()) {
        if (1 > images.size()) {
            ProductImg img = doCreateDefaultImage();
            img.setSort(0);
            images.add(img);
        }
        return images;
    }

    // 创建默认图片
    protected ProductImg doCreateDefaultImage() {
        ProductImg img = new ProductImg();
        img.setPath(getDefaultProductImagePath());
        return img;
    }

    /**
     * 获取默认图片
     * @return 图片地址
     */
    public String getDefaultProductImagePath() {
        return defaultProductImagePath;
    }

    /**
     * 设置模型图片地址
     * @param defaultProductImagePath  默认图片地址
     */
    public void setDefaultProductImagePath(String defaultProductImagePath) {
        this.defaultProductImagePath = defaultProductImagePath;
    }

    /**
     * <p>名称：doCleanOrCreateDefaultItem</p>
     * <p>描述：预处理 SKU, 拷贝属性等等</p>
     * @author：LiuJC
     * @param pro 产品
     * @param create 是否新增
     * @return 商品集合
     */
    protected List<Goods> doCleanOrCreateDefaultItem(final Product pro, final Boolean create) {
        if (null == pro) {
            return Collections.emptyList();
        }
        //规格
        Boolean specEnabled = pro.getEnableSpecs();
        //商品
        List<Goods> items = pro.getGoods();
        //规格不为空同时开启规格
        specEnabled = null != specEnabled && specEnabled;

        items = null != items ? items : Lists.<Goods>newArrayList();
        // 没有启用规格 或 当前没有添加 SKU, 创建默认 SKU
        if (!specEnabled || (1 == items.size() && CollectionUtils.isEmpty(items.get(0).getSpecValues()))
                || 1>items.size()) {
            final Goods g = doCreateDefaultItem(pro, create);
            if(items.size() !=1){
                items.clear();
                items.add(g);
            }else if(items.size()==1){
                g.setId(items.get(0).getId());
                items.set(0,g);
            }
        } else {
            for (Iterator<Goods> it = items.iterator(); it.hasNext(); ) {
                final Goods item = it.next();
                if (null == item) {
                    it.remove();
                    continue;
                }
                if(StringUtil.notEmpty(item.getTitle()) && null!= item.getStock() && null!=item.getPrice()){
                    item.setProduct(pro);
                    item.setDeleted(false);
                }else{
                    it.remove();
                    continue;
                }

//                item.setHopeCostPrice(pro.getHopeCostPrice());
            }
        }
        return items;
    }

    /**
     * <p>名称：doCreateDefaultItem</p>.
     * <p>描述：创建默认商品.</p>
     * @author：LiuJC
     * @param pro 产品
     * @param create 是否新增
     * @return 商品
     */
    protected Goods doCreateDefaultItem(final Product pro, final Boolean create) {
        final Goods goods = new Goods();
        goods.setProduct(pro);
        goods.setTitle(pro.getTitle());
        goods.setIntro(pro.getIntro());
        goods.setSn(pro.getSn());     // 直接使用作为 code
        goods.setPrice(pro.getPrice());
        goods.setStock(pro.getStock());
        goods.setAlertStock(pro.getAlertStock());
        goods.setProductImgs(pro.getProductImgs());
        goods.setImage(pro.getImage());// 主图
        goods.setIsDefault(true);
        goods.setDeleted(false);
        return goods;
    }



    // 保存或更新有差异的图片

    /**
     * <p>名称：doSaveOrUpdateDiffImages</p>.
     * <p>描述：保存或更新有差异的图片</p>
     * @author：LiuJC
     * @param images 新图片
     * @param original 旧图片
     * @param pro 产品
     * @param item 单品
     */
    protected void doSaveOrUpdateDiffImages(final List<ProductImg> images, final List<ProductImg> original,
                                            final Product pro, final Goods item) {
        new DiffHandler<ProductImg>() {
            @Override
            protected void doBothHas(final ProductImg left,final ProductImg right) {
                left.setProduct(pro);
                left.setGoods(item);
                productImageDao.update(left);
            }

            @Override
            protected void doOnlyLeftHas(final ProductImg left) {
                left.setProduct(pro);
                left.setGoods(item);
                left.setId(null);
                productImageDao.save(left);
            }

            @Override
            protected void doOnlyRightHas(final ProductImg right) {
                productImageDao.delete(right);
            }
        }.doSwitch(images, original);
    }

    //

    /**
     * <p>名称：doSaveOrUpdateDiffAttributeValues</p>.
     * <p>描述：保存或更新差异的商品 - 属性值关系</p>
     * @author：LiuJC
     * @param values 属性值新
     * @param original 属性值旧
     * @param pro 产品
     */
    protected void doSaveOrUpdateDiffAttributeValues(final List<AttributeValue> values,
                                                     final List<AttributeValue> original, final Product pro) {
        // 删除自定义属性值
        attributeValueDao.deleteProductCustomAttributeValueByProductId(pro.getId());
        attributeValueDao.deleteProductAttributeValue(pro.getId());

        if (null != values) {
            for (final AttributeValue value : values) {
                if (null != value) {
                    final Map<String, Object> paramsMap = Maps.newHashMap();


                    // 值不为空，则为自定义属性值
                    if (null != value.getValue()) {
                        value.setId(null);
                        attributeValueDao.save(value);
                        final Attribute attr = value.getAttribute();
                        paramsMap.put("ATTRIBUTE_ID", null != attr ? attr.getId() : null);
                    }

                    if (null != value.getId()) {
                        paramsMap.put("productId", pro.getId());
                        paramsMap.put("attributeValueId", value.getId());
                        paramsMap.put("deleted", false);
                        attributeValueDao.saveProductAttributeValue(paramsMap);
                    }
                }
            }
        }
    }


    /**
     * <p>名称：doSaveOrUpdateDiffParameterValues</p>
     * <p>描述：保存 参数值</p>
     * @author：LiuJC
     * @param original 旧参数
     * @param values 新参数值
     * @param pro 产品
     */
    protected void doSaveOrUpdateDiffParameterValues(final List<ParameterValue> values, final List<ParameterValue> original, final Product pro) {
        new DiffHandler<ParameterValue>() {
            @Override
            protected void doBothHas(final ParameterValue left, final ParameterValue right) {
                left.setProduct(pro);
                parameterValueDao.update(left);
            }

            @Override
            protected void doOnlyLeftHas(final ParameterValue left) {
                left.setProduct(pro);
                parameterValueDao.save(left);
            }

            @Override
            protected void doOnlyRightHas(final ParameterValue right) {
                parameterValueDao.delete(right);
            }
        }.doSwitch(values, original);
    }

    /**
     * <p>名称：doSaveOrUpdateDiffItems</p>.
     * <p>描述：// 保存或更新有差异的 SKU</p>
     * @author：LiuJC
     * @param values 新商品
     * @param original 旧商品
     * @param pro 产品
     */
    protected void doSaveOrUpdateDiffItems(final List<Goods> values, final List<Goods> original, final Product pro) {
        new DiffHandler<Goods>() {
            @Override
            protected void doBothHas(final Goods left, final Goods right) {
                left.setProduct(pro);
                doUpdateItem(left, right);
            }

            @Override
            protected void doOnlyLeftHas(final Goods left) {
                left.setProduct(pro);
                if (null == left.getIsDefault()) {
                    left.setIsDefault(false);
                }
                doSaveItem(left);
            }

            @Override
            protected void doOnlyRightHas(final Goods right) {
                right.setProduct(pro);
                doDeleteItem(right);
                super.doOnlyRightHas(right);
            }
        }.doSwitch(values, original);
    }

    /**
     * <p>名称：doUpdateItem</p>.
     * <p>描述：更新单品</p>
     * @author：LiuJC
     * @param item 新商品
     * @param original 旧商品
     */
    protected void doUpdateItem(final Goods item, final Goods original) {
        applyProductPropertiesIfNecessary(item);
        item.setSvStr(buildSkuStr(item.getSpecValues()));
        goodsService.update(item);
        //todo 加序号
        doSaveOrUpdateDiffImages(item.getProductImgs(), original.getProductImgs(), null, item);
        // UPDATE
        goodsDao.deleteGoodsSpecValues(item.getId());
        saveGoodsSpecValues(item, item.getSpecValues());
    }

    //

    /**
     * <p>名称：applyProductPropertiesIfNecessary</p>.
     * <p>描述：应用 Product 信息 如果 SKU 信息为空</p>
     * @author：LiuJC
     * @date：2016/6/30 16:04
     * @param item 商品
     */
    protected void applyProductPropertiesIfNecessary(final Goods item) {
        final Product pro = item.getProduct();
        if (null == pro) {
            return;
        }

        if (!StringUtils.hasText(item.getTitle())) {
            item.setTitle(pro.getTitle());
        }
        if (!StringUtils.hasText(item.getSn())) {
            item.setSn(pro.getSn());
        }
        if (null == item.getPrice()) {
            item.setPrice(pro.getPrice());
        }

        List<ProductImg> images = item.getProductImgs();
        if (null == images || 1 > images.size()) {
            item.setProductImgs(images = pro.getProductImgs());
        }
        if (null == item.getImage()) {
            final ProductImg img = null != images && 0 < images.size() ? images.get(0) : null;
            item.setImage(null != img ? img.getPath() : null);
        }
    }

    /**
     * <p>名称：buildSkuStr</p>.
     * <p>描述：sku字符串</p>
     * @author：LiuJC
     * @param specValues 规格值
     * @return 规格值字符串
     */
    protected String buildSkuStr(final List<SpecValue> specValues) {
        if (null == specValues) {
            return null;
        }
        final StringBuilder buff = new StringBuilder();
        for ( SpecValue sv : specValues) {
            if (null == sv.getSpec()) {
                sv = specValueDao.find(sv.getId());
            }
            if (null != sv && null != sv.getSpec()) {
                buff.append(sv.getSpec().getId()).append(":").append(sv.getId()).append(";");
            }
        }
        return 1 > buff.length() ? null : buff.toString();
    }

    /**
     * <p>名称：saveGoodsSpecValues</p>.
     * <p>描述：保存商品规格值</p>
     * @author：LiuJC
     * @param goods 商品
     * @param values 规格值
     */
    private void saveGoodsSpecValues(final Goods goods, final List<SpecValue> values) {
        if (null != values) {
            for (final SpecValue value : values) {
                final Map<String, Object> paramsMap = createParamsMap();
                paramsMap.put("goodsId", goods.getId());
                paramsMap.put("specValueId", value.getId());
                specValueDao.saveGoodsSpecValue(paramsMap);
            }
        }
    }

    /**
     * <p>名称：doSaveItem</p>
     * <p>描述：保存单品</p>
     * @author：LiuJC
     * @param item 单品
     */
    protected void doSaveItem(final Goods item) {
        applyProductPropertiesIfNecessary(item);
        item.setSvStr(buildSkuStr(item.getSpecValues()));
        item.setDeleted(false);
        goodsService.save(item);
        doSaveOrUpdateDiffImages(item.getProductImgs(), null, null, item);
        saveGoodsSpecValues(item, item.getSpecValues());
    }

    /**
     * <p>名称：doDeleteItem</p>.
     * <p>描述：删除商品</p>
     * @author：LiuJC
     * @param item 单个商品
     */
    protected void doDeleteItem(final Goods item) {
        productImageDao.deleteGoodsImage(item.getId());
        goodsDao.deleteGoodsSpecValues(item.getId());
        goodsDao.delete(item);

    }

    @Override
    public Page<Product> findPage(Filters baseFilters, Filters brandFilters, Filters modelFilters,
            Filters categoryFilters, Filters labelFilters,
            final Pageable pageable) {
        baseFilters = new IterateNamingTransformFilters(baseFilters);
        brandFilters = new IterateNamingTransformFilters(brandFilters);
        modelFilters = new IterateNamingTransformFilters(modelFilters);
        labelFilters = new IterateNamingTransformFilters(labelFilters);
        categoryFilters = new IterateNamingTransformFilters(categoryFilters);


        final Map<String, Object> paramsMap = createParamsMap();
        if (null != baseFilters) {
            paramsMap.put(ProductDao.PRODUCT_FILTERS_PROP, baseFilters);
        }
        if (null != brandFilters) {
            paramsMap.put(ProductDao.BRAND_FILTERS_PROP, brandFilters);
        }
        if (null != modelFilters) {
            paramsMap.put(ProductDao.MODEL_FILTERS_PROP, modelFilters);
        }
        if (null != categoryFilters) {
            paramsMap.put(ProductDao.CATEGORY_FILTERS_PROP, categoryFilters);
        }
        if (null != labelFilters) {
            paramsMap.put(ProductDao.LABEL_FILTERS_PROP, labelFilters);
        }

        if (null != pageable) {
            paramsMap.put(ProductDao.OFFSET_PROP, pageable.getOffset());
            paramsMap.put(ProductDao.MAX_RESULTS_PROP, pageable.getPageSize());

            final Sort sort = pageable.getSort();
            if (null != sort) {
                paramsMap.put(ProductDao.SORT_PROP, new IterateNamingTransformSort(sort));
            }
        }

        final int total = count(paramsMap);
        final List<Product> data = total > 0 ? findMany(paramsMap) : Collections.<Product>emptyList();

        return new SimplePage<Product>(pageable, data, total);
    }


    @Override
    public void delete(final Product product) {
        final Long pid = product.getId();
        attributeValueDao.deleteProductAttributeValue(pid); // 属性值
        parameterValueDao.deleteParameterValueByProductId(pid);
        productImageDao.deleteProductImage(pid);
        goodsDao.deleteProductGoods(pid);
        super.delete(product);
    }


    /**
     * <p>名称：save</p>.
     * <p>描述：多产品保存</p>
     * @author：LiuJC
     * @date：2016/6/30 15:32
     * @param products 多产品集合
     */
    public void save(final List<Product> products){
        for(final Product p:products){
            save(p);
        }
    }



    /**
     * 是否单品.
     * @param productId 产品id
     * @return 是否是单品
     */
    public boolean isSingleProduct(final Long productId){
        final int count =productDao.isSingleProduct(productId);
        if(0!=count){
            return true;
        }
        return false;
    }


    /**
     * 商品下架.
     * @param ids 商品ID
     */
    public void offLoading(final Long[] ids){
        productDao.offLoding(ids);
    }

    /**
     * 商品上架.
     * @param ids 商品id
     */
    public void onSell(final Long[] ids){
        productDao.onSell(ids);
    }


}