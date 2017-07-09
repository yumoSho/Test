package com.glanway.jty.controller.admin.perm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glanway.jty.entity.perm.Role;
import com.glanway.jty.service.perm.RoleService;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;

@Controller
@RequestMapping("/admin/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	/**
	 * 页面显示
	 * @param map
	 * @param filter
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("index")
	public String list(ModelMap map, Filters filter, Pageable pageSize){
		return "admin/adminRole/index";
	}
	
	/**
	 * 访问数据
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("list")
    @ResponseBody
    public Page<Role> list(Filters filters, Pageable pageable) {
        return roleService.findPage(filters, pageable);
    }
	
	/**
	 * 添加页面显示
	 * @return
	 */
	@RequestMapping("add")
    public String add(){
        return "admin/adminRole/add";
    }
	
	/**
	 * 插入数据
	 * @param role
	 * @param pageIds
	 * @return
	 */
	@RequestMapping("save")
	public String save(Role role, String pageIds){
		roleService.saveRole(role, pageIds);
		return "redirect:/admin/role/index";
	}
	
	/**
	 * 删除数据
	 * @param ID
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") Long[] ids){
		Map<String, Object> map = new HashMap<String, Object>();
		roleService.remove(ids);
		map.put("success", true);
		return map;
	}
	
	@RequestMapping("checkIsRoleExists")
	@ResponseBody
	public Map<String, Boolean> checkIsExists(String name){
		return roleService.checkIsRoleExists(name);
	}
	
	@RequestMapping("hasAnnotherRole")
	@ResponseBody
	public Map<String, Boolean> hasAnnotherRole(String[] roleIdAndName){
		return roleService.hasAnnotherRole(roleIdAndName);
	}
	
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable(value="id") Long id, ModelMap map){
		Role role = roleService.getRoleById(id);
		map.put("role", role);
		return "admin/adminRole/edit";
	}
	
	@RequestMapping("update")
	public String update(Role role, String pageIds){
		roleService.updateRole(role, pageIds);
		return "redirect:/admin/role/index";
	}
	@ResponseBody
	@RequestMapping("all")
	public List<Role> all(){
		return roleService.findAll();
	}
}
