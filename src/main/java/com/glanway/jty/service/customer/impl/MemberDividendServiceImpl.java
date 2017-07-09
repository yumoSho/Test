package com.glanway.jty.service.customer.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.admin.customer.GradeDetailVo;
import com.glanway.jty.dao.customer.GradeDao;
import com.glanway.jty.dao.customer.GradeDetailDao;
import com.glanway.jty.dao.customer.MemberDao;
import com.glanway.jty.dao.customer.MemberDividendDao;
import com.glanway.jty.dao.marketing.ProductPackagesDao;
import com.glanway.jty.dao.order.OrderDao;
import com.glanway.jty.entity.customer.*;
import com.glanway.jty.entity.marketing.ProductPackage;
import com.glanway.jty.entity.order.Order;
import com.glanway.jty.entity.order.OrderDetail;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.customer.BalancePaymentDetailService;
import com.glanway.jty.service.customer.GradeService;
import com.glanway.jty.service.customer.MemberDividendService;
import com.glanway.jty.service.product.CategoryService;
import com.glanway.jty.service.product.GoodsService;
import org.simpleframework.xml.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
@Service
@Transient
public class MemberDividendServiceImpl extends BaseServiceImpl<MemberDividend,Long> implements MemberDividendService {
    @Autowired
    private MemberDividendDao memberDividendDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private BalancePaymentDetailService balancePaymentDetailService;

    @Autowired
    private ProductPackagesDao productPackagesDao;

    @Override
    public void mySave( GradeDetailVo gradeDetailVo) {
        if((gradeDetailVo.getCategoryId().size() > 0) && (gradeDetailVo.getCategoryId().size() == gradeDetailVo.getDiscount().size())){
            //删除该会员的所有分类折扣
            memberDividendDao.deleteAll();
            //保存会员等级分类折扣
            List<Long> categoryList = gradeDetailVo.getCategoryId();
            List<String> discount = gradeDetailVo.getDiscount();
            for(int i=0;i < categoryList.size(); i++ ){
                Long categoryId = categoryList.get(i);
                MemberDividend gdl = new MemberDividend();
                gdl.setCategoryId(categoryId);
                gdl.setDiscount(new BigDecimal(discount.get(i)));
                memberDividendDao.save(gdl);
            }
        }else{
            throw new CustomException("输入数据异常");
        }
    }


    @Override
    public List<MemberDividend> findAll() {
        return memberDividendDao.findAll();
    }


    @Override
    public void recharge(Long memberId, Long orderId,String costWay) {
        Member member = memberDao.find(memberId);
        Long rmId = member.getRecommendedId();
        if(null != rmId){
            Member recommen = memberDao.find(rmId);
            if(null != recommen){
                Order order = orderDao.getOrderById(orderId);
                List<OrderDetail> details = order.getOrderDetails();
                BigDecimal totalPrice = new BigDecimal(0);
                /*如果是配件 就取主商品*/
                if(null != order.getOtherId()){
                    Long otherId = order.getOtherId();
                    ProductPackage productPackage = productPackagesDao.getPackageById(otherId);
                    Goods primaryGoods = productPackage.getPrimaryGoods();

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setSellPrice(primaryGoods.getPrice().doubleValue());
                    orderDetail.setGoodsId(primaryGoods.getId());

                    totalPrice = getMemberPrice(orderDetail);
                }else {
                    /*其它够购 */
                    for(OrderDetail detail :details){
                        Integer goodsFrom = detail.getGoodsFrom();
                        if(Constants.GOODS_FROM_TC != goodsFrom) {
                            BigDecimal price  = getMemberPrice(detail);
                            if(null != price){
                                totalPrice = totalPrice.add(price);
                            }
                        }
                    }
                }
                if(totalPrice.doubleValue() > 0) {
                    balancePaymentDetailService.recharge(rmId,memberId, BalancePaymentDetail.TYPE_REBATE,totalPrice,null,costWay);
                }
            }
        }
    }



    /**
     * <p>名称：getMemberPrice</p>
     * <p>描述：活动打折过时,计算会员折扣</p>
     *
     * @param orderDetail       订单详情
     * @author：LiuJC
     */
    private BigDecimal getMemberPrice(OrderDetail orderDetail) {
        List<Goods> goodsList = goodsService.findMany(Filters.create().eq("id", orderDetail.getGoodsId()), Sort.create());
        Goods goods = goodsList.get(0);
        //所有的分类优惠
        List<MemberDividend> all = this.findAll();

        //没有设置折扣
         if (CollectionUtils.isEmpty(all)) {
                return null;
         }

            //商品的直接分类
            Category category = goods.getProduct().getCategory();
            if (null == category) {
                return null;
            }

            BigDecimal tempDiscount = null;
            Boolean flag = false;
            /*--------------------循环查找分类及子类------------------------------*/
            a:
            for (MemberDividend dividend : all) {
                Long detailCategoryId = dividend.getCategoryId();
                if (null == detailCategoryId) {
                    System.out.println("折扣的分类为空");
                } else {
                    if (detailCategoryId.equals(category.getId())) {
                        tempDiscount = dividend.getDiscount();
                        flag = true;
                        break a;
                    } else {
                        List<Category> categoryTree = categoryService.getCategoryTree();
                        for (Category cate : categoryTree) {
                            if( flag == true){
                                break a;
                            }
                            if (detailCategoryId.equals(cate.getId())) {
                                flag = checkChildIsEquals(category, dividend, cate);
                                if(flag){
                                    tempDiscount=  dividend.getDiscount();
                                    break a;
                                }
                            }
                        }
                    }
                }
            }
            if (flag) {
                BigDecimal price =  new BigDecimal(orderDetail.getSellPrice()).multiply(tempDiscount).multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                return  new BigDecimal(orderDetail.getGoodsNum()).multiply(price);
            }
        return null;
    }


    private Boolean checkChildIsEquals(Category category, MemberDividend detail, Category cate) {
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
