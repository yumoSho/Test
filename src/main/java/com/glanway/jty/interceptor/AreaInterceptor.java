/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: jintianyuan
 * FileName: AreaInterceptor.java
 * PackageName: com.glanway.jty.interceptor
 * Date: 2016/8/2217:23
 **/
package com.glanway.jty.interceptor;/**
 * Created by chao on 2016/8/22.
 */

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.common.Constants;
import com.glanway.jty.entity.content.SupportCategory;
import com.glanway.jty.entity.content.SupportContent;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.marketing.CategoryRecommend;
import com.glanway.jty.entity.platform.Advertisement;
import com.glanway.jty.entity.platform.Link;
import com.glanway.jty.entity.platform.SeoSetting;
import com.glanway.jty.service.content.SupportCategoryService;
import com.glanway.jty.service.content.SupportContentService;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.marketing.CategoryRecommendService;
import com.glanway.jty.service.platform.AdvertisementService;
import com.glanway.jty.service.platform.LinkService;
import com.glanway.jty.service.platform.SeoSettingService;
import com.glanway.jty.utils.CookieUtil;
import com.glanway.jty.utils.PropertiesUtil;
import com.glanway.jty.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p>名称: AreaInterceptor</p>
 * <p>说明: 区域选择</p>
 * <p>修改记录：（2016/8/22 18:20 - LiuJC - 创建）</p>
 *
 * @author：LiuJC
 * @date：2016/8/2217:23
 * @version: 1.0
 */
public class AreaInterceptor implements HandlerInterceptor {
    @Autowired
    private MemberService memberService;
    @Autowired
    private CategoryRecommendService categoryRecommendService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
	private SupportCategoryService supportCategoryService;
    @Autowired
	private SupportContentService supportContentService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private SeoSettingService seoSettingService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
       /*seo*/
        Map param = new HashMap();
        param.put("url",request.getRequestURI());
        List<SeoSetting> all = seoSettingService.findByList(param);
        if(null != all && all.size() > 0){
            request.setAttribute("seo",all.get(0));
        }
        boolean isAjax = ("XMLHttpRequest".equals(request.getHeader("X-Requested-with"))) ? true : false;
        if(isAjax){
          return  true;
        }
        HttpSession session = request.getSession();
        Member oldMember = (Member) session.getAttribute(Constants.MEMBER);
        Member member = null;
        if(null != oldMember){
            member = memberService.find(oldMember.getId());
            member.setPassword(null);
        }
        if(null == member ){
            String provinceId = CookieUtil.getCookieValue(request,  Constants.COOKIE_STRING);
            if(StringUtil.notEmpty(provinceId)){
                String newProvinceId = request.getParameter(Constants.COOKIE_STRING);
                if(StringUtil.notEmpty(newProvinceId)){
                    if(!provinceId.equals(newProvinceId)){
                        CookieUtil.setCookie(response,  Constants.COOKIE_STRING, newProvinceId);
                        session.setAttribute( Constants.COOKIE_STRING,newProvinceId);
                    }
                }else{
                    session.setAttribute( Constants.COOKIE_STRING,provinceId);
                }
            }else{
                String newProvinceId = request.getParameter(Constants.COOKIE_STRING);
                if(StringUtil.notEmpty(newProvinceId)){
                    session.setAttribute( Constants.COOKIE_STRING,newProvinceId);
                    CookieUtil.setCookie(response,  Constants.COOKIE_STRING, newProvinceId);
                }else{
                    session.setAttribute( Constants.COOKIE_STRING,"0");
                    CookieUtil.setCookie(response,  Constants.COOKIE_STRING, "0");
                }
            }
        }else if(null == member.getProvinceId()){
            String provinceId = CookieUtil.getCookieValue(request,  Constants.COOKIE_STRING);

            if(StringUtil.notEmpty(provinceId)){
                String newProvinceId = request.getParameter(Constants.COOKIE_STRING);
                if(StringUtil.notEmpty(newProvinceId)){
                    if(!newProvinceId.equals(provinceId)){
                        CookieUtil.setCookie(response,  Constants.COOKIE_STRING, newProvinceId);
                    }
                    session.setAttribute( Constants.COOKIE_STRING,newProvinceId);
                }else{
                    session.setAttribute( Constants.COOKIE_STRING,provinceId);
                    CookieUtil.setCookie(response,  Constants.COOKIE_STRING, provinceId);
                }
                member.setProvinceId(StringUtil.notEmpty(newProvinceId)?Long.valueOf(provinceId):Long.valueOf(provinceId));
            }else{
                session.setAttribute( Constants.COOKIE_STRING,provinceId);
                String newProvinceId = request.getParameter(Constants.COOKIE_STRING);
                CookieUtil.setCookie(response,  Constants.COOKIE_STRING, newProvinceId);
                member.setProvinceId(Long.valueOf(newProvinceId));
                session.setAttribute( Constants.COOKIE_STRING,provinceId);
            }
            memberService.update(member);
            Member memberNew = memberService.find(member.getId());
            memberNew.setPassword(null);
            session.setAttribute(Constants.MEMBER,memberNew);
        }else{
            String newProvinceId = request.getParameter(Constants.COOKIE_STRING);
            if(StringUtil.notEmpty(newProvinceId) ){
                String provinceId = CookieUtil.getCookieValue(request,  Constants.COOKIE_STRING);
                if(StringUtil.notEmpty(provinceId)){
                    member.setProvinceId(Long.valueOf(newProvinceId));
                    session.setAttribute( Constants.COOKIE_STRING,newProvinceId);
                    CookieUtil.setCookie(response,  Constants.COOKIE_STRING, newProvinceId);
                }else{
                    member.setProvinceId(0l);
                    CookieUtil.setCookie(response,  Constants.COOKIE_STRING, "0");
                    session.setAttribute( Constants.COOKIE_STRING,"0");
                }
                memberService.update(member);
                Member memberNew = memberService.find(member.getId());
                memberNew.setPassword(null);
                session.setAttribute(Constants.MEMBER,memberNew);
                /*CookieUtil.setCookie(response,  Constants.COOKIE_STRING, newProvinceId);
                member.setProvinceId(Long.valueOf(newProvinceId));
                session.setAttribute( Constants.COOKIE_STRING,newProvinceId);
                memberService.update(member);
                session.setAttribute(Constants.MEMBER,memberService.find(member.getId()));*/
            }else{
                String provinceId = CookieUtil.getCookieValue(request,  Constants.COOKIE_STRING);
                if(StringUtil.notEmpty(provinceId)){
                    member.setProvinceId(Long.valueOf(provinceId));
                    session.setAttribute( Constants.COOKIE_STRING,provinceId);
                    CookieUtil.setCookie(response,  Constants.COOKIE_STRING, provinceId);
                }else{
                    member.setProvinceId(0l);
                    CookieUtil.setCookie(response,  Constants.COOKIE_STRING,"0");
                    session.setAttribute( Constants.COOKIE_STRING,"0");
                }
                memberService.update(member);
                Member memberNew = memberService.find(member.getId());
                memberNew.setPassword(null);
                session.setAttribute(Constants.MEMBER,memberNew);
            }
        }

        /**------------------分类推荐--------------------------*/
        Sort sort = Sort.create().asc("sort");
        List<CategoryRecommend> categoryRecommends = categoryRecommendService.findMany((Filters) null, sort);
        request.setAttribute("categoryRecommends",categoryRecommends);

        Dictionary dic_ad = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_GGPOS, Constants.DT_GGPOS_ITOP);

        Filters filters=Filters.create().eq("pos", dic_ad.getDicCode()).eq("statusType",1).eq("deviceType",1).eq("deleted",0);
        sort.clear();
        sort.desc("sortNum");
        PageRequest pageRequest = new PageRequest(1,1,sort);
        List<Advertisement> advertisement = advertisementService.findMany(filters, pageRequest);
        if(!CollectionUtils.isEmpty(advertisement)){
            request.setAttribute("top",advertisement.get(0));
        }

        /**------------------foot 帮助信息显示--------------------------*/
        List<SupportCategory> supportCategories = supportCategoryService.findAllByBankId();
		List<SupportContent> supportContents = supportContentService.findAllByBankId();
		Dictionary dictionaryCommonPro = dictionaryService.findBySuperDicCodeAndSubCode("HELPCENTER", "commonPro");
		
		List<Link> links = linkService.findAllByBankId();

		if(supportCategories != null && supportCategories.size() > 4){
			supportCategories = supportCategories.subList(0, 5);
		}

        //加载网站配置信息
        if(session.getAttribute("simpleWebsite") == null){
            Properties websiteConf = PropertiesUtil.getConfig(Constants.WEBSITE_CONF);
            session.setAttribute("simpleWebsite",websiteConf);
        }
        //加载用户注册协议
        if(session.getAttribute("regConf") == null){
            Properties regConf = PropertiesUtil.getConfig(Constants.REG_CONF);
            session.setAttribute("regConf",regConf);
        }

        //加载用户返利协议
        if(session.getAttribute("rtMoneyConf") == null){
            Properties rtMoneyConf = PropertiesUtil.getConfig(Constants.RT_MONEY_CONF);
            session.setAttribute("rtMoneyConf",rtMoneyConf);
        }
		
		request.setAttribute("simpleSupportCategories", supportCategories);
		request.setAttribute("simpleSupportContents", supportContents);
		request.setAttribute("simpleDictionaryCommonPro", dictionaryCommonPro);
		request.setAttribute("simpleLinks", links);
//		request.setAttribute("simpleWebsite", websiteConf);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
