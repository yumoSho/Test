package com.glanway.jty.service.product.impl;

import com.glanway.jty.dao.product.CategoryDao;
import com.glanway.jty.entity.product.Brand;
import com.glanway.jty.entity.product.BrandCategory;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.BrandCategoryService;
import com.glanway.jty.service.product.BrandService;
import com.glanway.jty.service.product.CategoryService;
import com.glanway.jty.utils.CacheUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 分类service
 */
@Service
@Transactional
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {

    private CategoryDao categoryDao;
    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CacheUtil cacheUtil;

/*    @Autowired
    private GoodsService goodsService ;*/


    @Autowired
    public void setCategoryDao(CategoryDao categoryDao) {
        super.setCrudDao(categoryDao);
        this.categoryDao = categoryDao;
    }

    /**
     * <p>名称：save</p>
     * <p>描述：保存分类 和 品牌分类</p>
     * @author：LiuJC
     * @param category
     */
    @Override
    public void save(Category category) {
        category.setCategoryCode(getCategoryCode());
        String path = category.getPath();
        String pathNames = category.getPathNames();
        Long nextId = categoryNextId();
        nextId = null==nextId?0:nextId;
        if(StringUtils.isEmpty(path)){
            category.setPath(String.valueOf(nextId+1));
        }else{
            category.setPath(category.getPath()+","+String.valueOf(nextId+1));
        }
        if(StringUtils.isEmpty(pathNames)){
            category.setPathNames("/$"+category.getName());
        }else{
            category.setPathNames(pathNames+"/$"+category.getName());
        }
        super.save(category);

        // 保存分类品牌关系
        List<Brand> brands = category.getBrands();
        if(!CollectionUtils.isEmpty(brands)) {
            for (Brand brand : brands) {
                if (null == brand) {
                    continue;
                }
                BrandCategory brandCategory = new BrandCategory();
                brandCategory.setBrand(brand);
                brandCategory.setCategory(category);
                brandCategory.setDeleted(false);
                brandCategoryService.save(brandCategory);
            }
        }
    }

    /**
     * <p>名称：update</p>
     * <p>描述：更新分类 和品牌分类'</p>
     * @author：LiuJC
     * @param category
     */
    @Override
    public void update(Category category) {
        String path = category.getPath();
        String pathNames = category.getPathNames();
        if(StringUtils.isEmpty(path)){
            category.setPath(String.valueOf(category.getId()));
        }else{
            category.setPath(category.getPath()+","+String.valueOf(category.getId()));
        }
        if(StringUtils.isEmpty(pathNames)){
            category.setPathNames("/$"+category.getName());
        }else{
            category.setPathNames(pathNames+"/$"+category.getName());
        }
        super.update(category);
        brandCategoryService.deleteBrandCateById(category.getId());
        List<Brand> brands = category.getBrands();
        if(!CollectionUtils.isEmpty(brands)) {
            for (Brand brand : brands) {
                if (null == brand) {
                    continue;
                }
                BrandCategory brandCategory = new BrandCategory();
                brandCategory.setBrand(brand);
                brandCategory.setCategory(category);
                brandCategory.setDeleted(false);
                brandCategoryService.save(brandCategory);
            }
        }
    }

    /**
     * <p>名称：delete</p>
     * <p>描述：删除分类</p>
     * @author：LiuJC
     * @param longs
     */
    @Override
    public void delete(Long[] longs) {
        super.delete(longs);
        if(null!= longs && 0!=longs.length){
            for (int i = 0; i < longs.length; i++) {
                brandCategoryService.deleteBrandCateById(longs[i]);
            }
        }

    }

    /**
     * <p>名称：delete</p>
     * <p>描述：删除</p>
     * @author：LiuJC
     * @param category
     */
    @Override
    public void delete(Category category) {
        Boolean flag = this.categoryHaveBeenUsedForGoods(category.getId());
        if(null != flag && flag){
            throw new CustomException("有分类已被商品引用不能被删除");
        }
        Boolean hasChild = this.categoryHaveChild(category.getId());
        if(null != hasChild && hasChild){
            throw new CustomException("有分类已包含子分类");
        }
        super.delete(category);
    }

    /**
     * <p>名称：findCategoryTree</p>
     * <p>描述：树查询</p>
     * @author：LiuJC
     * @return List<Category>
     */
    @Override
    public List<Category> findCategoryTree() {
        List<Category> categories = categoryDao.findTree();
        Category root = new Category();
        root.setId(null);
        buildCategoryTree(root, categories);
        return root.getChildren();
    }

    /**
     * <p>名称：buildCategoryTree</p>
     * <p>描述：构建树形</p>
     * @author：LiuJC
     * @param root 父
     * @param categories 子树集合
     */
    protected void buildCategoryTree(Category root, List<Category> categories) {
        if (root == null || categories == null || categories.size() < 1) {
            return;
        }
        Long rid = root.getId();
        List<Category> parents = Lists.newArrayList();
        Iterator<Category> it = categories.iterator();
        while (it.hasNext()) {
            Category category = it.next();
            Category parent = category.getParent();
            Long pid = parent != null  ? parent.getId() : null;
            pid = pid!=null&&pid>0 ? pid : null;

            if (rid == pid || (rid != null && rid.equals(pid))) {
                List<Category> children = root.getChildren();
                if (children == null) {
                    children = Lists.newArrayList();
                    root.setChildren(children);
                }
                children.add(category);
                it.remove();

                parents.add(category);
            }
        }
        for (Category parent : parents) {
            buildCategoryTree(parent, categories);
        }
    }

    /**
     * <p>名称：categoryHaveBeenUsedForGoods</p>
     * <p>描述：分类是否被商品引用</p>
     * @author：LiuJC
     * @param cid
     * @return
     */
    @Override
    public Boolean categoryHaveBeenUsedForGoods(Long cid) {
        return categoryDao.categoryHaveBeenUsedForGoods(cid);
    }

    /**
     * <p>名称：categoryNextId</p>
     * <p>描述：获取下个ID</p>
     * @author：LiuJC
     * @return
     */
    @Override
    public Long categoryNextId() {
        return categoryDao.categoryNextId();
    }

    /**
     * <p>名称：categoryHaveChild</p>
     * <p>描述：是否还有子分类</p>
     * @author：LiuJC
     * @param cid
     * @return
     */
    @Override
    public Boolean categoryHaveChild(Long cid) {
        return categoryDao.categoryHaveChild(cid);
    }

    /**
     * <p>名称：getCategoryCode</p>
     * <p>描述：分类code</p>
     * @author：LiuJC
     * @return
     */
    private String getCategoryCode(){
        Long nextId = categoryNextId();
        nextId=null==nextId?0:nextId;
        return "C"+(nextId+1002);
    }

    /**
     * <p>名称：topCategory</p>
     * <p>描述：顶级分类</p>
     * @author：LiuJC
     * @param offset
     * @param maxResults
     * @return
     */
    public List<Category> topCategory(Integer offset,Integer maxResults){
        HashMap<String, Object> findFitler = Maps.newHashMap();
        findFitler.put("_offset",offset);
        findFitler.put("_maxResults",maxResults);
        return categoryDao.topSeven(findFitler);
    }

    /**
     * <p>名称：updateIndexCategoryAndBrand</p>
     * <p>描述：品牌分类推荐</p>
     * @author：LiuJC
     */
    @Override
    public void updateIndexCategoryAndBrand() {
        List<Category> cacheCategoryTree = findCategoryTree();
        if (!CollectionUtils.isEmpty(cacheCategoryTree)) {
            for (Category tree : cacheCategoryTree) {
                tree.setBrands(brandService.findTopBrandRecommend(0, 6, tree.getId()));
            }

        }
        if(!CollectionUtils.isEmpty(cacheCategoryTree)){
            cacheUtil.setCacheValue("categoryTreeList",cacheCategoryTree);
        }
    }



    /**
     * <p>名称：getParmsList</p>
     * <p>描述：并排分类树（名字不知道怎么起了）</p>
     * @author：LiuJC
     * @param websiteCategoryTree
     * @param longListMap
     * @date：2016/7/11 10:18
     * @return Map<Long, List<Long>>  Map<一级分类, List<所有子级分类>>
     */
    public Map<Long, List<Long>> getParmsList(List<Category> websiteCategoryTree, Map<Long,List<Long>> longListMap){
        for (Category c:websiteCategoryTree){
            List<Long> longs = new ArrayList<>();
            longs.add(c.getId());
            getChilds(c, longs);
            longListMap.put(c.getId(),longs);
        }
        return longListMap;
    }

    /**
     * <p>名称：getChilds</p>
     * <p>描述：获取子分类</p>
     * @author：LiuJC
     * @param c 分类
     * @param longs 子分类集合
     * @date：2016/7/11 10:23
     */
    private void getChilds(Category c, List<Long> longs) {
        if(!CollectionUtils.isEmpty(c.getChildren())){
            for(Category cat:c.getChildren()){
                getChilds(cat,longs);
                longs.add(cat.getId());
            }
        }
    }

    /**
     * <p>名称：getCategoryTree</p>
     * <p>描述：分类树 从缓存中取得</p>
     * @author：LiuJC
     * @return
     */
    public List<Category> getCategoryTree() {
        List<Category> cacheCategoryTree = (List<Category>) cacheUtil.getCacheValue("categoryTreeList");
        if (null == cacheCategoryTree) {
            cacheCategoryTree = findCategoryTree();
            if (!CollectionUtils.isEmpty(cacheCategoryTree)) {
                cacheUtil.setCacheValue("categoryTreeList", cacheCategoryTree);
            }
            return cacheCategoryTree;
        }
        return cacheCategoryTree;
    }

}
