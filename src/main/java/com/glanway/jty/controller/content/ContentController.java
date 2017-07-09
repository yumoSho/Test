package com.glanway.jty.controller.content;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.content.CommonPro;
import com.glanway.jty.entity.content.ContentManagement;
import com.glanway.jty.entity.content.News;
import com.glanway.jty.entity.content.NewsType;
import com.glanway.jty.entity.content.SupportCategory;
import com.glanway.jty.entity.content.SupportContent;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.service.content.CommonProService;
import com.glanway.jty.service.content.ContentManagementService;
import com.glanway.jty.service.content.NewsService;
import com.glanway.jty.service.content.NewsTypeService;
import com.glanway.jty.service.content.SupportCategoryService;
import com.glanway.jty.service.content.SupportContentService;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.google.common.collect.Maps;



/**
 * 前台内容管理 内容显示类
 * @author songzhe
 *
 */
@Controller
@RequestMapping("/contentManagement")
public class ContentController extends BaseController{

	@Autowired
	private ContentManagementService contentManagementService;
	
/*	@Autowired
	private AnnounceService announceService;*/
	
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
		
//		List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
//		modelMap.put("advertisementTypes",JSONUtil.getJSONString(dictionaries).toString());
//		ContentManagement contentManagement = contentManagementService.find(id);
//		modelMap.addAttribute("contentManagement", contentManagement);
		
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
		return "pc/contentManagement/content";
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
		return "pc/contentManagement/newsList";
	}
	
	/**
	 * 公告分页列表页面
	 * @param page 当前页
	 * @param size 每页显示的记录
	 * @param modelMap
	 * @return
	 */
/*	@RequestMapping("/announceList")
	public String announceList(@RequestParam(required = false, defaultValue = "1") int page,@RequestParam(required = false, defaultValue = "9") int size,
			ModelMap modelMap){
		
		Filters filters = Filters.create();
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setPageSize(size);
        Sort sort = new Sort();
        sort.desc("createdDate");
        pageRequest.setSort(sort);
        Page<Announce> announces = announceService.findPage(filters, pageRequest);
			modelMap.put("announces", announces);

		
		return "/contentManagement/announceList";
	}
	*/
	/**
	 * 跳转帮助中心页面(暂时不用)
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("helpCenter")
	public String helpCenter(Long id,ModelMap modelMap){
		
		List<SupportCategory> supportCategories = supportCategoryService.findAllByBankId();
		List<SupportContent> supportContents = supportContentService.findAllByBankId();

		modelMap.addAttribute("supportCategories", supportCategories);
		modelMap.addAttribute("supportContents", supportContents);
		//所需数据已在DnsInterceptor.java加载
		modelMap.addAttribute("helpDetail", "main");

		
		return "pc/contentManagement/helpCenter";
	}
	
	/**
	 * 跳转帮助中心 常见问题列表
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("helpCenterCommonPro")
	public String helpCenterCommonPro(Long id,ModelMap modelMap,@RequestParam(required = false, defaultValue = "1") int page,@RequestParam(required = false, defaultValue = "10") int size){
		
		List<SupportCategory> supportCategories = supportCategoryService.findAllByBankId();
		List<SupportContent> supportContents = supportContentService.findAllByBankId();
		Dictionary dictionaryCommonPro = dictionaryService.findBySuperDicCodeAndSubCode("HELPCENTER", "commonPro");
		modelMap.addAttribute("supportCategories", supportCategories);
		modelMap.addAttribute("supportContents", supportContents);
		modelMap.addAttribute("dictionaryCommonPro", dictionaryCommonPro);
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
		modelMap.addAttribute("commonProPage", true);
		return "pc/contentManagement/commonPros";
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
		
		return "pc/contentManagement/commonProsDetail";
	}
	@RequestMapping("about")
	public String about(Long id,ModelMap modelMap){
		
	/*	Dictionary dicAbout = dictionaryService.findBySuperDicCodeAndSubCode(Constants.DT_NRGL, Constants.DT_ABOUT);
		
		ContentManagement contentManagement = contentManagementService.find(dicAbout.getId());
		modelMap.addAttribute("contentManagement", contentManagement);*/
		return "/contentManagement/content";
	}
	
	/**
	 * 跳转帮助中心详情
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("helpDetail")
	public String helpDetail(Long id,ModelMap modelMap){
		
		List<SupportCategory> supportCategories = supportCategoryService.findAllByBankId();
		List<SupportContent> supportContents = supportContentService.findAllByBankId();
		Dictionary dictionaryCommonPro = dictionaryService.findBySuperDicCodeAndSubCode("HELPCENTER", "commonPro");
		modelMap.addAttribute("supportCategories", supportCategories);
		modelMap.addAttribute("supportContents", supportContents);
		modelMap.addAttribute("dictionaryCommonPro", dictionaryCommonPro);

		modelMap.addAttribute("supportContentId", id);
		modelMap.addAttribute("supportContent", supportContentService.find(id));
	
		return "pc/contentManagement/helpDetail";
	}
	
	/**
	 * 页面动态跳转（暂用）
	 * @param pageName（jsp页面名称）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/{pageName}")
	public String redirect(@PathVariable(value="pageName")String pageName,ModelMap modelMap,Long id){
		
		if("newsList".equals(pageName)){
			List<News> newss = newsService.findAll();
//			List<News> real_news = removeArticleHtml(newss);
			modelMap.put("newss", newss);
			
		}else if("newsDetail".equals(pageName)){
			News news = newsService.find(id);
		
//				newsService.hitAccumulate(news);
			
			modelMap.put("news", news);
//			modelMap.put("navBar", true);
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
		
		return "pc/contentManagement/"+pageName;
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

		return new ModelAndView("redirect:/contentManagement/"+pageName);
	}
	
	
}
