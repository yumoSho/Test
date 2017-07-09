package com.glanway.jty.dao.customer;


import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.customer.GradeDetail;

/**
* </b>会员 Dao
*  @author  generator
*  @Time     2016-04-12
*  @version 1.0
*/
public interface GradeDetailDao extends BaseDao<GradeDetail,Long> {

    void deleteByGradId(Long gradeId);

}
