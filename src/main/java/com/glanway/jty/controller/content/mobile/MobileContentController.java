package com.glanway.jty.controller.content.mobile;


import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.content.*;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.service.content.*;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 前台内容管理 内容显示类
 * @author songzhe
 *
 */
@Controller
@RequestMapping("/mobile/contentManagement/")
public class MobileContentController extends BaseController {

	@Autowired
	private ContentManagementService contentManagementService;
	
	@Autowired
	private NewsService newsService;

	@Autowired
	private NewsTypeService newsTypeService;

	@Autowired
	private SupportCategoryService supportCategoryService;
	
	@Autowired
	private SupportContentService supportContentService;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private CommonProService commonProService;
	
	/**
	 * 通过ID动态获取内容管理的内容
	 * @param pageName
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("content/{pageName}")
	public String index(@PathVariable(value="pageName")String pageName,Long id,ModelMap modelMap){

		if("coupon".equals(pageName)){
			Dictionary dictionary = dictionaryService.findBySuperDicCodeAndSubCode("NRGL", "YHJZQ");
			Map<String,Object> paramMap = Maps.newConcurrentMap();
			paramMap.put("dictionaryId", dictionary.getId());
			ContentManagement contentManagement = contentManagementService.findOne(paramMap);
			modelMap.put("contentManagement", contentManagement);
		}else if("partner".equals(pageName)){
			Dictionary dictionary = dictionaryService.findBySuperDicCodeAndSubCode("NRGL", "HZRZ");
			Map<String,Object> paramMap = Maps.newConcurrentMap();
			paramMap.put("dictionaryId", dictionary.getId());
			ContentManagement contentManagement = contentManagementService.findOne(paramMap);
			modelMap.put("contentManagement", contentManagement);
		}
		return "/mobile/contentManagement/content";
	}
	
	/**
	 * 资讯分类列表页面
	 * @param page 当前页
	 * @param size 每页显示的记录
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("newsList")
	public String newsList(@RequestParam(required = false, defaultValue = "1") int page,@RequestParam(required = false, defaultValue = "10") int size,
			ModelMap modelMap,Integer type,String newskeyword){

		Filters filters = null;
		if(type==null){
			filters = Filters.create();
		}else{
			filters = Filters.create().eq("a.nTypeId", type);
		}

		if(newskeyword != null){
			filters.like("title", newskeyword);
		}
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(page);

		pageRequest.setPageSize(size);
		Sort sort = new Sort();
		sort.desc("sortNum");
		sort.desc("createdDate");
		pageRequest.setSort(sort);
		Page<News> newss = newsService.findPage(filters, pageRequest);
		modelMap.put("newss", newss);
//			modelMap.put("navBar", true);//显示导航条
		modelMap.put("_newsType", type);



		Filters newsTypesFilters = Filters.create().eq("A.activated", true);
		PageRequest newsTypesPageRequest = new PageRequest();
		pageRequest.setPage(100);
		Sort newsTypesSort = new Sort();
		newsTypesSort.desc("sortNum");
		newsTypesPageRequest.setSort(newsTypesSort);
		List<NewsType> newsTypes = newsTypeService.findPage(newsTypesFilters, newsTypesPageRequest).getData();
		modelMap.put("newsTypes", newsTypes);
		modelMap.addAttribute("commonText", "新闻资讯");
		return "/mobile/contentManagement/newsList";
	}
	
	
	/**
	 * 手机端公告列表页面，显示更多
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
    @RequestMapping("news/list")
    public Page<News> newsListAJAX(@RequestParam(defaultValue="1")Integer page,
                                    @RequestParam( defaultValue="10")Integer pageSize){
//        Filters filters = Filters.create().eq("bankId", this.bankId).lt("startDate", new Date()).gt("endDate", new Date());
        Filters filters = Filters.create();
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        Sort sort = new Sort();
//        sort.desc("sortNum");
        sort.desc("createdDate");
        pageRequest.setSort(sort);
        Page<News> newss = newsService.findPage(filters, pageRequest);
		
        return newss;
    }
	
	@RequestMapping("/newsDetail")
	public String newsDetail(ModelMap modelMap,Long id,Integer type){
		
	
			News news = newsService.find(id);
		
//			newsService.hitAccumulate(news);
			
			modelMap.put("news", news);
//			modelMap.put("navBar", true);


		PageRequest pageRequest = new PageRequest();
		Sort sort = new Sort();
		sort.desc("sortNum");
		sort.desc("createdDate");
		pageRequest.setSort(sort);
		Filters newsTypesFilters = Filters.create().eq("A.activated", true);
		PageRequest newsTypesPageRequest = new PageRequest();
		Sort newsTypesSort = new Sort();
		newsTypesSort.desc("sortNum");
		newsTypesPageRequest.setSort(newsTypesSort);
		List<NewsType> newsTypes = newsTypeService.findPage(newsTypesFilters, newsTypesPageRequest).getData();
		modelMap.put("newsTypes", newsTypes);
		modelMap.addAttribute("commonText", "新闻详情");
		modelMap.put("_newsType", type);
		
		return "/mobile/contentManagement/newsDetail";
	}
	
/*	*//**
	 * 公告分页列表页面
	 * @param page 当前页
	 * @param size 每页显示的记录
	 * @param modelMap
	 * @return
	 *//*
	@RequestMapping("/announceList")
	public String announceList(@RequestParam(required = false, defaultValue = "1") int page,@RequestParam(required = false, defaultValue = "6") int size,
			ModelMap modelMap){
		
		Filters filters = Filters.create().eq("isShow", 0);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setPageSize(size);
        Sort sort = new Sort();
        sort.desc("createdDate");
        pageRequest.setSort(sort);
        Page<Announce> announces = announceService.findPage(filters, pageRequest);
			modelMap.put("announces", announces);
			modelMap.put("common", "网站公告");
		
		return "/mobile/contentManagement/announceList";
	}*/
/*
	*//**
	 * 手机端公告列表页面，显示更多
	 * @param page
	 * @param pageSize
	 * @return
	 *//*
	@ResponseBody
    @RequestMapping("/announceList/list")
    public Page<Announce> announceListAJAX(@RequestParam(defaultValue="1")Integer page,
                                    @RequestParam( defaultValue="6")Integer pageSize){
//        Filters filters = Filters.create().eq("bankId", this.bankId).lt("startDate", new Date()).gt("endDate", new Date());
        Filters filters = Filters.create().eq("isShow", 0);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        Sort sort = new Sort();
        sort.desc("createdDate");
        pageRequest.setSort(sort);
        Page<Announce> announces = announceService.findPage(filters, pageRequest);
		
        return announces;
    }*/
	
/*	*//**
	 * 公告详情
	 * @param modelMap
	 * @param id
	 * @return
	 *//*
	@RequestMapping("/announceDetail")
	public String announceDetail(ModelMap modelMap,Long id){
		
	
			Announce announce = announceService.find(id);
		
//			newsService.hitAccumulate(news);
			
			modelMap.put("announce", announce);
//			modelMap.put("navBar", true);
			modelMap.put("common", "公告详情");	
	
		
		return "/mobile/contentManagement/announceDetail";
	}*/
	/**
	 * 跳转帮助中心页面
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("helpCenter")
	public String helpCenter(Long id,ModelMap modelMap,HttpServletRequest request){
		
//		List<SupportCategory> supportCategories = supportCategoryService.findAllByBankId(this.bankId);
//		List<SupportContent> supportContents = supportContentService.findAllByBankId(this.bankId);
//		 //判断访问设备
//		 UserAgent ua = UserAgent.parse(request);
//		modelMap.addAttribute("supportCategories", supportCategories);
//		modelMap.addAttribute("supportContents", supportContents);
		//所需数据已在DnsInterceptor.java加载
		
		List<SupportCategory> fullSupportCategorys = supportCategoryService.findAllCategoryAndContentsByBankId();
		modelMap.addAttribute("fullSupportCategorys", fullSupportCategorys);



		/*List<SupportCategory> supportCategories = supportCategoryService.findAllByBankId();
		List<SupportContent> supportContents = supportContentService.findAllByBankId();*/
		Dictionary dictionaryCommonPro = dictionaryService.findBySuperDicCodeAndSubCode("HELPCENTER", "commonPro");
	/*	modelMap.addAttribute("supportCategories", supportCategories);
		modelMap.addAttribute("supportContents", supportContents);*/
		modelMap.addAttribute("dictionaryCommonPro", dictionaryCommonPro);
		modelMap.addAttribute("commonText", "帮助中心首页");
		return "/mobile/contentManagement/helpCenter";
	}
	
	/**
	 * 跳转帮助中心详情
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("helpDetail")
	public String helpDetail(Long id,ModelMap modelMap,HttpServletRequest request){
		
//		List<SupportCategory> supportCategories = supportCategoryService.findAllByBankId(this.bankId);
//		List<SupportContent> supportContents = supportContentService.findAllByBankId(this.bankId);
//		SupportContent supportContent = null;
//		
//		for (SupportContent sptContent : supportContents) {
//			if(id == sptContent.getId()){
//				supportContent = sptContent;
//			}
//		}
//		
//		 
//		modelMap.addAttribute("supportCategories", supportCategories);
//		modelMap.addAttribute("supportContents", supportContents);
//		modelMap.addAttribute("supportContent", supportContent);
		SupportContent supportContent = supportContentService.find(id);
		modelMap.addAttribute("supportContentId", id);
		modelMap.addAttribute("supportContent", supportContent);
		modelMap.addAttribute("commonText", supportContent.getTitle());
		return "/mobile/contentManagement/helpDetail";
	}
	
	/**
	 * 页面动态跳转（暂用）
	 * @param pageName（jsp页面名称）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/{pageName}")
	public String redirect(@PathVariable(value="pageName")String pageName,ModelMap modelMap,Long id,HttpServletRequest request){
		
		
		
		if("newsList".equals(pageName)){
			List<News> newss = newsService.findAll();
//			List<News> real_news = removeArticleHtml(newss);
			modelMap.put("newss", newss);
			
		}else if("newsDetail".equals(pageName)){
			News news = newsService.find(id);
		
//				newsService.hitAccumulate(news);
			
			modelMap.put("news", news);
			modelMap.put("navBar", true);
		}
		/*else if("announceList".equals(pageName)){
			List<Announce> announces = announceService.findAll();
			modelMap.put("announces", announces);
		}else if("announceDetail".equals(pageName)){
			Announce announce = announceService.find(id);
			
				announceService.hitAccumulate(announce);
			
			modelMap.put("announce", announce);
		}*/
//		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
//		modelMap.put("advertisementTypes",JSONUtil.getJSONString(dictionaries).toString());
		
//		UserAgent ua = UserAgent.parse(request);
	
		
		return "/mobile/contentManagement/"+pageName;
	}
	
	/**
	 * 判断要跳转的目标页面，并绑定所属银行编号
	 * @param pageName
	 * @param modelMap
	 * @param attr
	 * @return
	 */
	@RequestMapping("content/dynamic/{pageName}")
	public ModelAndView dynamicPageName(@PathVariable(value="pageName")String pageName,ModelMap modelMap,RedirectAttributes attr){
		
		if("contactUs".equals(pageName)){
//			attr.addAttribute("id", this.bankId);
			   return new ModelAndView("redirect:/contentManagement/content/contactUs");
		}

		return new ModelAndView("redirect:/contentManagement/mobile/"+pageName);
	}

	/**
	 * 跳转帮助中心 常见问题列表
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("helpCenterCommonPro")
	public String helpCenterCommonPro(Long id,ModelMap modelMap,@RequestParam(required = false, defaultValue = "1") int page,@RequestParam(required = false, defaultValue = "10") int size){

		/*List<SupportCategory> supportCategories = supportCategoryService.findAllByBankId();
		List<SupportContent> supportContents = supportContentService.findAllByBankId();
		Dictionary dictionaryCommonPro = dictionaryService.findBySuperDicCodeAndSubCode("HELPCENTER", "commonPro");
		modelMap.addAttribute("supportCategories", supportCategories);
		modelMap.addAttribute("supportContents", supportContents);
		modelMap.addAttribute("dictionaryCommonPro", dictionaryCommonPro);*/
		//所需数据已在DnsInterceptor.java加载
//		modelMap.addAttribute("helpDetail", "main");


		Filters filters = Filters.create().eq("display", true);
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(page);
		pageRequest.setPageSize(size);
		Sort sort = new Sort();
		sort.asc("sortNum");
		pageRequest.setSort(sort);
		Page<CommonPro> commonPros = commonProService.findPage(filters, pageRequest);
		modelMap.addAttribute("commonPros", commonPros);
		modelMap.addAttribute("commonText", "常见问题");
		return "mobile/contentManagement/commonPros";
	}

	/**
	 * 跳转帮助中心 常见问题详情
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("commonProDetail")
	public String commonProDetail(Long id,ModelMap modelMap){


		List<SupportCategory> supportCategories = supportCategoryService.findAllByBankId();
		List<SupportContent> supportContents = supportContentService.findAllByBankId();
		Dictionary dictionaryCommonPro = dictionaryService.findBySuperDicCodeAndSubCode("HELPCENTER", "commonPro");
		modelMap.addAttribute("supportCategories", supportCategories);
		modelMap.addAttribute("supportContents", supportContents);
		modelMap.addAttribute("dictionaryCommonPro", dictionaryCommonPro);

		modelMap.addAttribute("commonPro", commonProService.find(id));
		modelMap.addAttribute("commonProPage", true);

		return "mobile/contentManagement/commonProsDetail";
	}

	/**
	 * 手机端公告列表页面，显示更多
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("helpCenterCommon/list")
	public Page<CommonPro> helpCenterCommon(@RequestParam(defaultValue="1")Integer page,
								   @RequestParam( defaultValue="10")Integer pageSize){
//        Filters filters = Filters.create().eq("bankId", this.bankId).lt("startDate", new Date()).gt("endDate", new Date());
		Filters filters = Filters.create();
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(page);
		pageRequest.setPageSize(pageSize);
		Sort sort = new Sort();
//        sort.desc("sortNum");
		sort.desc("createdDate");
		pageRequest.setSort(sort);
		Page<CommonPro> commonPros = commonProService.findPage(filters, pageRequest);

		return commonPros;
	}
}
