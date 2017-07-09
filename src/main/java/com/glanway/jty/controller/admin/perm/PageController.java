package com.glanway.jty.controller.admin.perm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glanway.jty.utils.AdminUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glanway.jty.entity.perm.Module;
import com.glanway.jty.entity.perm.Page;
import com.glanway.jty.service.perm.ModuleService;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.google.common.collect.ImmutableMap;

/**
 * 
 *********************************************** 
 * 页面管理控制层
 * date: 2015年9月29日 下午6:05:59 <br/>
 *
 * @author wuqi
 * @version 
 * @since JDK 1.7
 * @change Log:
 *************************************************
 */

@Controller
@RequestMapping("/admin/page")
public class PageController {
	
    @Autowired
    private com.glanway.jty.service.perm.PageService PageService;
    
    @Autowired
    private ModuleService ModuleService;
    @Autowired
    private AdminUserUtil adminUserUtil;
    
    /**
     *返回列表页面
     *@return
     */
    @RequestMapping(value = "index",method= RequestMethod.GET)
    public String index(){
        return "admin/adminPage/index";
    }
    
    /**
     * 新增页面
     * @return
     */
    @RequestMapping("add")
    public String add(HttpServletRequest request,HttpServletResponse response,Model model){
    	com.glanway.gone.spring.bind.domain.Page<Module> mlist= ModuleService.findPage(null,null);
        model.addAttribute("mlist",mlist);
        return "admin/adminPage/add";
    }
    
    /**
	 * 向后台请求数据列表
	 * @param filters
	 * @param pageable
	 * @return
	 */
    @RequestMapping("list")
    @ResponseBody
    public com.glanway.gone.spring.bind.domain.Page<Page> list(
            @Qualifier("page.")
            Filters filters,
            @Qualifier("module.")
            Filters moduleFilters,
            Pageable pageable
            ){
        for(Sort.Order order : pageable.getSort()) {
            if(pageable.toString().indexOf("page.name")!=-1)
            	order.setProperty("name");
            if(pageable.toString().indexOf("page.url")!=-1)
                order.setProperty("url");
            if(pageable.toString().indexOf("page.remark")!=-1)
                order.setProperty("remark");
            if(pageable.toString().indexOf("module.name")!=-1)
                order.setProperty("tm_name");
        }
        return PageService.findPage(filters, moduleFilters, pageable);
    }
    
    /**
     * 删除
     */
    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("id") Long[] id){
    	PageService.remove(id);
    	return ImmutableMap.<String, Object>of("success", true);
    }
    
    /**
     * 新增页面
     */
    @RequestMapping("save")
    public String save(Page adminPage){
    	PageService.savePage(adminPage);
    	return "redirect:/admin/page/index";
    }
    
    /**
     * 进入编辑页面
     */
    @RequestMapping("edit/{id}")
    public String edit(@PathVariable(value="id") Long id, ModelMap map){
        Page Page= PageService.find(id);
        com.glanway.gone.spring.bind.domain.Page<Module> mlist= ModuleService.findPage(null,null);
        map.put("Page", Page);
        map.put("mlist", mlist);
        return "admin/adminPage/edit";
    }

    /**
     * 更新编辑
     */
    @RequestMapping("update")
    @ResponseBody
    public ModelAndView update(Page adminPage){
        ModelAndView mav = new ModelAndView();
        adminPage.setCreatedBy(Long.valueOf(adminUserUtil.getCurrentUser()));
        PageService.update(adminPage);
        mav.setViewName("redirect:/admin/page/index");
        return mav;
    }
    
    /**
     * 检查是否重名
     */
    @RequestMapping("checkIsPageExists")
    @ResponseBody
    public Map<String, Boolean> checkIsPageExists(String name){
    	return PageService.checkIsPageExists(name);
    }
    
}
