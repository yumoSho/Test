package com.glanway.jty.controller.admin.perm;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.utils.AdminUserUtil;
import com.glanway.jty.service.perm.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glanway.jty.entity.perm.Module;
import com.glanway.jty.entity.perm.SimpleDataTree;
import com.glanway.jty.service.perm.ModuleService;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.google.common.collect.Maps;

/**
 * @author wuqi
 *
 */
@Controller
@RequestMapping("admin/module")
public class ModuleController extends BaseController{

	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private PageService pageService;

	@Autowired
	private AdminUserUtil adminUserUtil;
	
	@RequestMapping("index")
	public String index(){
		return "admin/adminModule/index";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Page<Module> list(Filters filters, Pageable pageable){
		Page<Module> mlist = moduleService.findPage(filters, pageable);
		return mlist;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Message delete(@RequestParam("id") Long[] ids){
		  moduleService.remove(ids);
		return Message.success();
	}
	
	@RequestMapping("add")
	public String add(){
		return "admin/adminModule/add";
	}
	
	@RequestMapping("save")
	public String save(Module module){
		module.setDeleted(false);
		module.setCreatedDate(new Date(System.currentTimeMillis()));
//		module.setLastModifiedDate(new Date(System.currentTimeMillis()));
		module.setCreatedBy(Long.valueOf(adminUserUtil.getCurrentUser()));
		moduleService.save(module);
		return "redirect:/admin/module/index";
	}
	
	@RequestMapping("checkIsModuleExists")
	@ResponseBody
	public Map<String, Boolean> checkIsModuleExists(String name){
		return moduleService.checkIsModuleExists(name);
	}
	
	@RequestMapping("hasAnnotherModule")
	@ResponseBody
	public Map<String, Boolean> hasAnnotherModule(String[] moduleIdAndName){
		Map<String, Boolean> map = moduleService.hasAnnotherModule(moduleIdAndName);
		return map;
	}

	@RequestMapping("edit/{id}")
	public String edit(@PathVariable(value="id") Long id, ModelMap map){
		map.put("module", moduleService.find(id));
		return "admin/adminModule/edit";
	}
	
	@RequestMapping("update")
	public String update(Module module) {
		module.setLastModifiedBy(Long.valueOf(adminUserUtil.getCurrentUser()));
		module.setLastModifiedDate(new java.util.Date());
		moduleService.update(module);
		return "redirect:/admin/module/index";
	}

	@RequestMapping("tree")
	@ResponseBody
	public List<SimpleDataTree> getTree(Long roleId){
		List<com.glanway.jty.entity.perm.Page> pageList = new ArrayList<com.glanway.jty.entity.perm.Page>();
		if (null != roleId) {
			pageList = pageService.getPagesByRoleId(roleId);
		}
		List<Module> modules =  moduleService.findAll();
		Map<String, Object> map = Maps.newHashMap();
		for (Module module:modules) {
			map.put("moduleId", module.getId());
			List<com.glanway.jty.entity.perm.Page> pages = pageService.findMany(map);
			module.setPages(pages);
		}
		List<SimpleDataTree> datas = new ArrayList<SimpleDataTree>();
		long pId = 1;
		long sId = 100;
		for (Module module:modules) {
			SimpleDataTree data = new SimpleDataTree();
			data.setId(pId);
			data.setpId(null);
			data.setIsParent(true);
			data.setNocheck(false);
			data.setName(module.getName());
			data.setModelId(module.getId());
			datas.add(data);
			List<com.glanway.jty.entity.perm.Page> pages = module.getPages();
			for (com.glanway.jty.entity.perm.Page page : pages) {
				SimpleDataTree sdata = new SimpleDataTree();
				sdata.setId(sId);
				sdata.setpId(pId);
				sdata.setName(page.getName());
				sdata.setModelId(page.getId());
				//判断是否有已经被选中的节点
				if (null != pageList) {
					for (com.glanway.jty.entity.perm.Page sub : pageList) {
						if (page.getId().equals(sub.getId())) {
							sdata.setChecked(true);
							data.setOpen(true);
							break;
						}
					}
				}
				datas.add(sdata);
				sId++;
			}
			pId++;
		}
		return datas;
	}
}
