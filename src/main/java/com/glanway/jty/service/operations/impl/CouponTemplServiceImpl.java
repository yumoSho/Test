/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: sh
 * FileName: CouponTemplServiceImpl.java
 * PackageName: com.glanway.jty.service.operations.impl
 * Date: 2016/6/2110:33
 **/
package com.glanway.jty.service.operations.impl;

import com.glanway.jty.entity.operations.CouponTempl;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.operations.CouponTemplService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>名称: </p>
 * <p>说明: TODO</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/6/2110:33
 * @version: 1.0
 */
@Service
@Transactional
public class CouponTemplServiceImpl extends BaseServiceImpl<CouponTempl,Long> implements CouponTemplService{
}
