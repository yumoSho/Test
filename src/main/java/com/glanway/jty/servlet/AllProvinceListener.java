/*
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: jintianyuan
 * FileName: AllProvinceListener.java
 * PackageName: com.glanway.jty.servlet
 * Date: 2016/8/2215:20
 *
*/
package com.glanway.jty.servlet;
/**
 * Created by chao on 2016/8/22.
 */


import com.glanway.jty.common.Constants;
import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.service.logistics.HatProvinceService;
import com.glanway.jty.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/*
 * <p>名称: </p>
 * <p>说明: 把所有省放入application域中</p>
 * <p>修改记录：（2016/8/22 15:21 - LiuJC - 创建）</p>
 *
 * @author：LiuJC
 * @date：2016/8/2215:20
 * @version: 1.0
  */
public class AllProvinceListener implements ServletContextListener {

    private  HatProvinceService hatProvinceService;

    private CacheUtil cacheUtil;

    private static  WebApplicationContext context;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        context = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        hatProvinceService = (HatProvinceService) context.getBean("hatProvinceServiceImpl");
        cacheUtil = (CacheUtil) context.getBean("cacheUtil");
        List<HatProvince> hatProvinces = hatProvinceService.listAllProvince();
        HatProvince hatProvince = hatProvinces.get(8);
        hatProvinces.remove(8);
        hatProvinces.add(0,hatProvince);
        cacheUtil.setCacheValue(Constants.ALL_PROVINCE, hatProvinces);
        ServletContext sct=servletContextEvent.getServletContext();
        sct.setAttribute(Constants.ALL_PROVINCE,hatProvinces);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        cacheUtil.removeCacheValue(Constants.ALL_PROVINCE);
        ServletContext sct=servletContextEvent.getServletContext();
        sct.removeAttribute(Constants.ALL_PROVINCE);

    }
}
