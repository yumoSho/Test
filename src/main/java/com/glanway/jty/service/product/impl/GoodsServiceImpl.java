package com.glanway.jty.service.product.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.product.GoodsDao;
import com.glanway.jty.dao.product.ProductDao;
import com.glanway.jty.entity.customer.Grade;
import com.glanway.jty.entity.customer.GradeDetail;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.marketing.ActivityGoods;
import com.glanway.jty.entity.marketing.ActivityMgr;
import com.glanway.jty.entity.marketing.DiscountGoods;
import com.glanway.jty.entity.marketing.ProductPackage;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.customer.GradeService;
import com.glanway.jty.service.marketing.ActivityGoodsService;
import com.glanway.jty.service.marketing.DiscountGoodsService;
import com.glanway.jty.service.marketing.ProductPackagesService;
import com.glanway.jty.service.product.CategoryService;
import com.glanway.jty.service.product.GoodsService;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * 商品service实现类
 */
@Service
@Transactional
public class GoodsServiceImpl extends BaseServiceImpl<Goods,Long> implements GoodsService {
    Logger logger = Logger.getLogger(GoodsServiceImpl.class);

    @Autowired
    private ActivityGoodsService activityGoodsService;

    @Autowired
    private DiscountGoodsService discountGoodsService;

    @Autowired
    private ProductPackagesService productPackagesService;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public void setBrandDao(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
        super.setCrudDao(goodsDao);
    }

    /**
     * <p>名称：findGoodsDetail</p>
     * <p>描述：查询商品信息</p>
     *
     * @param goodsId
     * @return
     * @author：LiuJC
     */
    public Goods findGoodsDetail(Long goodsId) {
        Filters filters = Filters.create().eq("id", goodsId);
        Map paramsMap = this.createParamsMap();
        paramsMap = this.resolveFiltersToParamsMap(filters, paramsMap);
        paramsMap = this.needSplitCascadeProperty() ? this.resolveCascade(paramsMap) : paramsMap;
        List<Goods> goodses = goodsDao.findGoodsDetail(paramsMap);
        return (!CollectionUtils.isEmpty(goodses) && goodses.size() == 1 ? goodses.get(0) : null);
    }

    /**
     * <p>名称：findGoodsMany</p>
     * <p>描述：商品分页查询</p>
     *
     * @param filters  过滤条件
     * @param pageable 分页条件
     * @return
     * @author：LiuJC
     */
    public Page<Goods> findGoodsMany(Filters filters, Pageable pageable) {
        int count = this.count(filters);
        List data = null;
        if (count > 0) {
            Map paramsMap = this.createParamsMap();
            paramsMap = this.resolveFiltersToParamsMap(filters, paramsMap);
            paramsMap = this.resolvePageableToParamsMap(pageable, paramsMap);
            data = goodsDao.findGoodsDetail(paramsMap);
        } else {
            data = Collections.emptyList();
        }
        return new SimplePage(pageable, data, count);

    }


    /**
     * <p>名称：getStock</p>
     * <p>描述：获取商品库存</p>
     *
     * @author：LiuJC
     */
    @Override
    public Integer getStock(Long goodsId) {
        return goodsDao.getStock(goodsId);
    }

    /*
     * <p>名称：updateStock</p>
     * <p>描述：更新商品库存</p>
     * @author：LiuJC
     * @param num 商品数量
     * @param goodsId 商品Id
     * @param flag  为true  或者为 null  为增加库存   为false 为减库存
     * */
    public void updateStock(Integer num, Long goodsId, Boolean flag) {
        if (null == num || null == goodsId) {
            return;
        }
        Goods saleNumAndStock = goodsDao.getSaleNumAndStock(goodsId);
        Long stock = 0l;
        if (null != saleNumAndStock) {
            stock = saleNumAndStock.getStock();
        } else {
            throw new CustomException("未找到商品");
        }

        HashMap<String, Object> findMap = Maps.newHashMap();
        if (null == flag || flag) {
            findMap.put("num", num);
            findMap.put("goodsId", goodsId);
            findMap.put("op", "+");
        } else {
            if (stock >= num) {
                findMap.put("num", num);
                findMap.put("goodsId", goodsId);
                findMap.put("op", "-");
            } else {
                throw new CustomException("商品数量超限");
            }
        }
        goodsDao.updateStock(findMap);
    }

    /*
   * <p>名称：updateSaleNum</p>
   * <p>描述：更新商品销量</p>
   * @author：LiuJC
   * @param num 商品数量
   * @param goodsId 商品Id
   * @param flag  为true  或者为 null  为增加销量   为false 为减销量
   * */
    public void updateSaleNum(Integer num, Long goodsId, Boolean flag) {
        if (null == num || null == goodsId) {
            return;
        }

        Goods saleNumAndStock = goodsDao.getSaleNumAndStock(goodsId);
        Long realSales = 0l;
        if (null != saleNumAndStock && null != saleNumAndStock.getProduct()) {
            realSales = saleNumAndStock.getProduct().getRealSales();
        } else {
            throw new CustomException("未找到商品");
        }
        HashMap<String, Object> findMap = Maps.newHashMap();
        if (null == flag || flag) {
            findMap.put("num", num);
            findMap.put("goodsId", goodsId);
            findMap.put("op", "+");
        } else {
            if (realSales >= num) {
                findMap.put("num", num);
                findMap.put("goodsId", goodsId);
                findMap.put("op", "-");
            } else {
                throw new CustomException("商品销量不足");
            }

        }
        goodsDao.updateSaleNum(findMap);
        goodsDao.updateRealSaleNum(findMap);
    }


    /**
     * <p>名称：findPageAlertStock</p>
     * <p>描述：库存警戒查询</p>
     *
     * @author：LiuJC
     */
    public Page<Goods> findPageAlertStock(Filters baseFilters, Pageable pageable) {
        baseFilters = new IterateNamingTransformFilters(baseFilters);
        Map<String, Object> paramsMap = createParamsMap();
        if (null != baseFilters) {
            paramsMap.put(GoodsDao.FILTERS_PROP, baseFilters);
        }
        if (null != pageable) {
            paramsMap.put(ProductDao.OFFSET_PROP, pageable.getOffset());
            paramsMap.put(ProductDao.MAX_RESULTS_PROP, pageable.getPageSize());

            Sort sort = pageable.getSort();
            if (null != sort) {
                paramsMap.put(ProductDao.SORT_PROP, new IterateNamingTransformSort(sort));
            }
        }
        int total = goodsDao.alertStockCount(paramsMap);
        List<Goods> data = total > 0 ? goodsDao.alertStockFindMany(paramsMap) : Collections.<Goods>emptyList();

        return new SimplePage<Goods>(pageable, data, total);
    }

    /**
     * <p>名称：getGoodsByOrderDetailNoCommented</p>
     * <p>描述：未评论商品</p>
     *
     * @return
     * @author：LiuJC
     */
    @Override
    public Page<Goods> getGoodsByOrderDetailNoCommented(Filters filters, Pageable pageable) {
        filters = new IterateNamingTransformFilters(filters);
        Map<String, Object> paramsMap = createParamsMap();
        if (null != filters) {
            paramsMap.put(GoodsDao.FILTERS_PROP, filters);
        }
        if (null != pageable) {
            paramsMap.put(ProductDao.OFFSET_PROP, pageable.getOffset());
            paramsMap.put(ProductDao.MAX_RESULTS_PROP, pageable.getPageSize());

            Sort sort = pageable.getSort();
            if (null != sort) {
                paramsMap.put(ProductDao.SORT_PROP, new IterateNamingTransformSort(sort));
            }
        }
        int total = goodsDao.getGoodsByOrderDetailNoCommentedCount(paramsMap);
        List<Goods> data = total > 0 ? goodsDao.getGoodsByOrderDetailNoCommented(paramsMap) : Collections.<Goods>emptyList();

        return new SimplePage<Goods>(pageable, data, total);
    }

    /**
     * 查询商品
     *
     * @param filters
     * @param pageable
     * @return
     */
    @Override
    public Page<Goods> findGoodsList(Filters filters, Pageable pageable) {
        filters = new IterateNamingTransformFilters(filters);
        Map<String, Object> paramsMap = createParamsMap();
        if (null != filters) {
            paramsMap.put(GoodsDao.FILTERS_PROP, filters);
        }
        if (null != pageable) {
            paramsMap.put(ProductDao.OFFSET_PROP, pageable.getOffset());
            paramsMap.put(ProductDao.MAX_RESULTS_PROP, pageable.getPageSize());

            Sort sort = pageable.getSort();
            if (null != sort) {
                paramsMap.put(ProductDao.SORT_PROP, new IterateNamingTransformSort(sort));
            }
        }
        int total = goodsDao.findGoodsListCount(paramsMap);
        List<Goods> data = total > 0 ? goodsDao.findGoodsList(paramsMap) : Collections.<Goods>emptyList();

        return new SimplePage<>(pageable, data, total);
    }

    /**
     * <p>名称：calcRealSalesPrice</p>
     * <p>描述：商品的价格计算有效期 </p>
     *
     * @param g 商品
     * @return
     * @author：LiuJC
     */
    @Override
    public void calcRealSalesPrice(Goods g) {
        if (null == g) {
            return;
        } else {
            ActivityGoods activityGoods = g.getActivityGoods();
            if (null == activityGoods || null == activityGoods.getDiscount() || null == activityGoods.getStartDate() ||
                    null == activityGoods.getEndDate()) {
                g.setPromotePrice(g.getPrice());
            } else {
                BigDecimal discount = activityGoods.getDiscount();
                Date endDateGoods = activityGoods.getEndDate();
                Date startDateGoods = activityGoods.getStartDate();
                Calendar beginDate = Calendar.getInstance();
                beginDate.setTime(startDateGoods);
                //结束时间
                Calendar endDate = Calendar.getInstance();
                endDate.setTime(endDateGoods);
                //当前时间
                Calendar cal = Calendar.getInstance();
                if (cal.after(beginDate) && cal.before(endDate)) {
                    g.setPromotePrice(g.getPrice().multiply(discount.divide(new BigDecimal(10))).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                } else {
                    g.setPromotePrice(g.getPrice());
                }
            }
        }
    }

    @Override
    public double calcsCore(Long id) {
        return goodsDao.calcsCore(id);
    }

    @Override
    public List<Goods> selectGoodsByCategoryId(Long categoryId, int maxResult, Long provinceId) {
        HashMap<String, Object> findMap = Maps.newHashMap();
        findMap.put("categoryId", categoryId);
        findMap.put("maxResult", maxResult);
        findMap.put("provinceId", provinceId);
        return goodsDao.selectGoodsByCategoryId(findMap);
    }

    /**
     * 计算会员价（除活动商品）
     *
     * @param session
     * @param goodses
     */
    public void calcMemberPriceAtList(HttpSession session, List<Goods> goodses) {
        for (Goods goodse : goodses) {
            if (null != goodse || null != goodse.getPrice()) {
                calcMemberPriceAtEveryOne(goodse, null, null, session);
            }
        }
    }

    /**
     * <p>名称：calcMemberPriceAtEveryOne</p>.
     * <p>描述：单一商品单个打折计算</p>
     *
     * @param goods     商品
     * @param goodsFrom 折扣来源
     * @param otherId   折扣来源ID
     * @param session   会花
     * @author：LiuJC
     */
    public void calcMemberPriceAtEveryOne(Goods goods, Integer goodsFrom, Long otherId, HttpSession session) {
        Member member = (Member) session.getAttribute(Constants.MEMBER);
        Long memberGrade = null != member && null != member.getGradeId() ? member.getGradeId() : null;
        if (null == goodsFrom || 0 == goodsFrom) {
            getMemberPrice(goods, memberGrade);
        } else if (Constants.GOODS_FROM_HY.equals(goodsFrom)) {//会员折扣
            getMemberPrice(goods, memberGrade);
        } else if (Constants.GOODS_FROM_HD.equals(goodsFrom)) {//活动折扣
            if (null != otherId) {
                ActivityGoods activityGoods = activityGoodsService.calcPrice(otherId,goods.getId());
                if (null != activityGoods) {
                    ActivityMgr activity = activityGoods.getActivity();
                    goods.setGoodsFrom(Constants.GOODS_FROM_HD);
                    goods.setPromotePrice(goods.getPrice().multiply(new BigDecimal(0.1).multiply(activity.getDiscount())).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                } else {
                    getMemberPrice(goods, memberGrade);
                }
            } else {
                getMemberPrice(goods, memberGrade);
            }
        } else if (Constants.GOODS_FROM_XSQG.equals(goodsFrom)) {//限时抢购
            if (null != otherId) {
                Date date = new Date();
                Filters filters = Filters.create().lt("startDate", date).gt("endDate", date).eq("ag.id", otherId).eq("ag.goodsId",goods.getId());
                List<DiscountGoods> discountGoodses = discountGoodsService.findMany(filters, (PageRequest) null);
                if (CollectionUtils.isEmpty(discountGoodses)) {
                    getMemberPrice(goods, memberGrade);
                } else if (1 == discountGoodses.size()) {
                    DiscountGoods discountGoods = discountGoodses.get(0);
                    BigDecimal discount = discountGoods.getDiscount();
                    if (null != discountGoods || null != discount) {
                        goods.setGoodsFrom(Constants.GOODS_FROM_XSQG);
                        goods.setPromotePrice(goods.getPrice().multiply(new BigDecimal(0.1).multiply(discount)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                    } else {
                        getMemberPrice(goods, memberGrade);
                    }
                } else {
                    getMemberPrice(goods, memberGrade);
                }
            } else {
                getMemberPrice(goods, memberGrade);
            }
        } else if (Constants.GOODS_FROM_TC.equals(goodsFrom)) {//套餐
            ProductPackage productPackage = productPackagesService.getPackageById(otherId);
            if (null != productPackage) {
                goods.setGoodsFrom(Constants.GOODS_FROM_TC);
                goods.setPromotePrice(goods.getPrice());
            } else {
                getMemberPrice(goods, memberGrade);
            }
        } else {
            System.out.println("可能出错了");
        }


    }

    /**
     * <p>名称：getMemberPrice</p>
     * <p>描述：活动打折过时,计算会员折扣</p>
     *
     * @param goods       商品
     * @param memberGrade 会员等级
     * @author：LiuJC
     */
    private void getMemberPrice(Goods goods, Long memberGrade) {
        if (null != memberGrade) {
            Grade grade = gradeService.find(memberGrade);
            if (null == grade) {
                goods.setPromotePrice(goods.getPrice());
                goods.setGoodsFrom(null);
                return;
            }
            List<GradeDetail> details = grade.getDetails();
            if (CollectionUtils.isEmpty(details)) {
                goods.setPromotePrice(goods.getPrice());
                goods.setGoodsFrom(null);
                return;
            }
            Category category = goods.getProduct().getCategory();
            if (null == category) {
                System.out.println("商品分类为空");
                goods.setPromotePrice(goods.getPrice());
                goods.setGoodsFrom(null);
                return;
            }
            BigDecimal tempDiscount = null;
            Boolean flag = false;
            /*--------------------循环查找分类及子类------------------------------*/
            a:
            for (GradeDetail detail : details) {
                Long detailCategoryId = detail.getCategoryId();
                if (null == detailCategoryId) {
                    System.out.println("等级分类折扣为空");
                } else {
                    if (detailCategoryId.equals(category.getId())) {
                        tempDiscount = detail.getDiscount();
                        flag = true;
                        break a;
                    } else {
                        List<Category> categoryTree = categoryService.getCategoryTree();
                        for (Category cate : categoryTree) {
                            if( flag == true){
                                break a;
                            }
                            if (detailCategoryId.equals(cate.getId())) {
                                flag = checkChildIsEquals(category, detail, cate);
                                if(flag){
                                    tempDiscount=  detail.getDiscount();
                                    break a;
                                }
                            }
                        }
                    }
                }
            }
            if (flag) {
                goods.setPromotePrice(goods.getPrice().multiply(tempDiscount).multiply(new BigDecimal(0.1)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                goods.setGoodsFrom(Constants.GOODS_FROM_HY);
            } else {
                goods.setGoodsFrom(null);
                goods.setPromotePrice(goods.getPrice());
            }
        } else {
            goods.setPromotePrice(goods.getPrice());
            goods.setGoodsFrom(null);
        }
    }

    private Boolean checkChildIsEquals(Category category, GradeDetail detail, Category cate) {
        List<Category> categories = cate.getChildren();
        if (!CollectionUtils.isEmpty(categories)) {
            for (Category child : categories) {
                if (child.getId().equals(category.getId())) {
                    return true;
                } else {
                    return checkChildIsEquals(category, detail, child);
                }
            }
        }
        return false;
    }
}