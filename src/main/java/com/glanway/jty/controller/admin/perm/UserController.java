/*
 * Copyright (c) 2005, 2014  glanway.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.glanway.jty.controller.admin.perm;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.common.Constants;
import com.glanway.jty.common.json.JSONUtil;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.perm.Role;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.perm.RoleService;
import com.glanway.jty.service.perm.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 后台用户管理
 *
 * @author wentan
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 添加后台用户
     *
     * @return 添加页面
     */
    @RequestMapping("add")
    public String add() {
        return "admin/adminUser/add";
    }

    @ResponseBody
    @RequestMapping("roleTree")
    public List<Map<String, Object>> loadRolesTree() {
        List<Role> rlist = roleService.findAll();
        List<Map<String, Object>> nodes = Lists.newArrayList();
        for (int i = 0; i < rlist.size(); i++) {
            Map<String, Object> node = Maps.newHashMap();
            node.put("id", rlist.get(i).getId());
            node.put("name", rlist.get(i).getName());
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * 返回列表页
     *
     * @return
     */
    @RequestMapping("index")
    public String index(ModelMap map) throws Exception {
        List<Dictionary> dictionaries = dictionaryService.findBySuperDicCode(Constants.DT_YHLX);
        map.put("userTypes", JSONUtil.getJSONString(dictionaries).toString());
        return "admin/adminUser/index";
    }

    /**
     * 保存后台用户
     *
     * @param
     * @return
     */
    @RequestMapping("save")
    public String save(User user, String roleIds) {
        user.setUserName(user.getUserName().trim());
        user.setPassword(user.getPassword().trim());
        userService.saveAdminUser(user, roleIds);
        return "redirect:/admin/user/index";
    }

    /**
     * 异步请求后台用户列表
     *
     * @param filters
     * @param pageable
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Page<User> list(@Qualifier("tu.") Filters filters, Pageable pageable, Map map) {
        return userService.findPage(filters, pageable);
    }

    /**
     * 删除用户
     *
     * @param
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Message delete(@RequestParam("id") Long[] ids) {
        Message message = Message.success();
        try {
            userService.delUsers(ids);
        } catch (CustomException e) {
            message = message.fail(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return message;
    }

    @RequestMapping("checkIsUserExists")
    @ResponseBody
    public Map<String, Boolean> checkIsUserExists(String username) {
        return userService.checkIsUserExists(username);
    }


    /**
     * 跳转到后台用户更改编辑界面
     *
     * @return 编辑页面
     */
    @RequestMapping("edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView();
        User user = userService.find(id);
        mav.addObject("user", user);
        mav.setViewName("admin/adminUser/edit");
        return mav;
    }

    @RequestMapping("getRoleIdsByUserId")
    @ResponseBody
    public Long[] getRoleIdsByUserId(Long userId) {
        return userService.findUserToRoleIds(userId);
    }

    /**
     * 后台用户修改提交
     *
     * @param user
     * @return
     */
    @RequestMapping("update")
    public String update(User user, String roleIds) {
        userService.updateUser(user, roleIds);
        return "redirect:/admin/user/index";
    }

    /**
     * 后台用户修改页面用户名验证
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping("checkIsAnotherUserExists")
    @ResponseBody
    public Boolean isAnotherUserExists(String username, String userId) {
        return userService.isAnotherUserExists(username, userId);
    }

}
