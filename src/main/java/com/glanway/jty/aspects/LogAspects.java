package com.glanway.jty.aspects;

import com.glanway.jty.annotation.Logger;
import com.glanway.jty.entity.system.OperationLog;
import com.glanway.jty.service.system.OperationLogService;
import com.glanway.jty.utils.AdminUserUtil;
import com.glanway.jty.utils.IPUtil;
import com.glanway.jty.utils.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 日志切面
 *
 * @author tianxuan
 * @Time 2016/4/13
 */

@Component
@Aspect
@Order(99)
public class LogAspects {
    @Autowired
    private AdminUserUtil adminUserUtil;

    @Autowired
    private OperationLogService operationLogService;

    @Pointcut("execution(* com.glanway..*.*(..))")
    public void expression() {
    }

    /**
     * 环绕通知
     * @param pjd  关注点
     * @param log   日志注解
     * @return      方法执行结果
     * @throws Throwable  方法抛出的异常
     */
    @Around("expression()&&@annotation(log)")
    public Object around(ProceedingJoinPoint pjd, Logger log) throws Throwable {
        Object result= null;
        Long userId = null;// Long.valueOf(adminUserUtil.getCurrentUser());
        Object[] args = pjd.getArgs();
        String loginName = "";
        HttpServletRequest  request = null;
        if(args.length ==  4 || StringUtil.notEmpty((String)args[0]) ){
            loginName = (String)args[0];
            request = (HttpServletRequest)args[3];
        }
        OperationLog operationLog = new OperationLog(log.value(), log.operateType(), userId);
        operationLog.setOtherFiled(loginName);
        operationLog.setOperateIp(IPUtil.getIp(request));
        try {
            result = pjd.proceed();
            operationLog.setOperateResult(OperationLog.SUCCESS);
        } catch (Throwable throwable) {
            operationLog.setOperateResult(OperationLog.ERROR);
            throw throwable;
        }finally {
            operationLogService.save(operationLog);
        }
        return result;
    }
}
