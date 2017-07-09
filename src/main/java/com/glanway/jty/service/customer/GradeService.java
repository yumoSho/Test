package com.glanway.jty.service.customer;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.controller.admin.customer.GradeDetailVo;
import com.glanway.jty.entity.customer.Grade;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.service.BaseService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
* </b>Member Service
*  @author  generator
*  @Time     2016-04-12
*  @version 1.0
*/
public interface GradeService extends BaseService<Grade,Long> {

    void mySave(Grade grade, GradeDetailVo gradeDetailVo);

    void myUpdate(Grade grade, GradeDetailVo gradeDetailVo);


}
