package com.glanway.jty.service.customer.impl;

import com.glanway.jty.controller.admin.customer.GradeDetailVo;
import com.glanway.jty.dao.customer.GradeDao;
import com.glanway.jty.dao.customer.GradeDetailDao;
import com.glanway.jty.entity.customer.Grade;
import com.glanway.jty.entity.customer.GradeDetail;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.customer.GradeService;
import org.simpleframework.xml.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
@Service
@Transient
public class GradeServiceImpl extends BaseServiceImpl<Grade,Long> implements GradeService {
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private GradeDetailDao gradeDetailDao;

    @Override
    public void mySave(Grade grade, GradeDetailVo gradeDetailVo){
        if(null != gradeDetailVo && (gradeDetailVo.getCategoryId().size() > 0) && (gradeDetailVo.getCategoryId().size() == gradeDetailVo.getDiscount().size())){
            //保存会员等级
            gradeDao.save(grade);
            //保存会员等级分类折扣
            saveDetail(grade,gradeDetailVo);
        }else{
            throw new CustomException("输入数据异常");
        }
    }

    @Override
    public void myUpdate(Grade grade, GradeDetailVo gradeDetailVo) {
        if(null != gradeDetailVo && (gradeDetailVo.getCategoryId().size() > 0) && (gradeDetailVo.getCategoryId().size() == gradeDetailVo.getDiscount().size())){
            super.update(grade);
            //删除该会员的所有分类折扣
            gradeDetailDao.deleteByGradId(grade.getId());
            //保存会员等级分类折扣
            saveDetail(grade,gradeDetailVo);
        }else{
            throw new CustomException("输入数据异常");
        }
    }

    //保存会员等级分类折扣
    public void saveDetail(Grade grade,GradeDetailVo gradeDetailVo){
        List<Long> categoryList = gradeDetailVo.getCategoryId();
        List<String> discount = gradeDetailVo.getDiscount();
        //保存会员等级分类折扣
        for(int i=0;i < categoryList.size(); i++ ){
            Long categoryId = categoryList.get(i);
            GradeDetail gdl = new GradeDetail();
            gdl.setGradeId(grade.getId());
            gdl.setCategoryId(categoryId);
            gdl.setDiscount(new BigDecimal(discount.get(i)));
            gradeDetailDao.save(gdl);
        }
    }
}
