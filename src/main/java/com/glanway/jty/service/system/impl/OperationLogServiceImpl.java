package com.glanway.jty.service.system.impl;

import com.glanway.jty.dao.system.OperationLogDao;
import com.glanway.jty.entity.system.OperationLog;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.system.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
* </b>OperationLog Service
*  @author  generator
*  @Time     2016-04-14
*  @version 1.0
*/
@Service("operationLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLog,Long> implements OperationLogService{
    @Autowired
    private OperationLogDao operationLogdao;
}
