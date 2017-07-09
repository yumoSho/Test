package com.glanway.jty.controller.admin.perm;


import com.glanway.jty.annotation.Logger;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.perm.Module;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.service.perm.PermissionService;
import com.glanway.jty.service.perm.UserService;
import com.glanway.jty.utils.AdminUserUtil;
import com.glanway.jty.utils.CacheUtil;
import com.glanway.jty.utils.IPUtil;
import com.glanway.jty.utils.PassWordSaltUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhuhaodong
 *
 */
@Controller("adminLoginController")
@RequestMapping("/admin")
public class LoginController extends BaseController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AdminUserUtil adminUserUtil;
	@Autowired
	private CacheUtil cacheUtil;
	@Autowired
	private PermissionService permissionService;
    @Autowired
    private PassWordSaltUtil passWordSaltUtil;
	/**
	 * 登陆首页
	 * @return
	 */
	@RequestMapping("")
	public String LoginPage(HttpServletRequest request){
		/*-
		 * update yangchanghe 2015.11.21
		 * 如果已经登录跳转到首页
		 */
		if (null != getCurrentUser(request)) {
			return "redirect:/admin/homePage";
		}
		return "admin/login";
	}
	
	/**
	 * 登陆验证
	 * @param loginName
	 * @param password
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@Logger("后台用户登录")
	@RequestMapping("login")
	public String login(String loginName, String password, ModelMap modelMap, HttpServletRequest request){
		if (null == loginName) {
			modelMap.put("errorMsg", "用户名不能为空");
			return "admin/login";
		}
		if (null == password) {
			modelMap.put("errorMsg", "密码不能为空");
			return "admin/login";
		}
		Map<String, Object> map = Maps.newHashMap();
		User user = new User();
		user.setUserName(loginName);
        user.setPassword(password);
		map.put("userName", loginName);
		List<User> userList = userService.getBaseUser(map);
		if (userList.size() < 1) {
			modelMap.put("errorMsg", "该用户不存在");
			//传回user对象，当登陆失败时页面显示的账户和密码和登陆之前显示一致
			modelMap.put("user", user);
			return "admin/login";
		}else if(!passWordSaltUtil.equalPassword(userList.get(0).getPassword(), password, userList.get(0).getCreatedDate())){ //密码错误
            modelMap.put("errorMsg", "密码不正确");
            //传回user对象，当登陆失败时页面显示的账户和密码和登陆之前显示一致
            modelMap.put("user", user);
            return "admin/login";
        }else{
			HttpSession session = request.getSession();
			Iterator<User> it = userList.iterator();
			user = it.next();
			if (false == user.getIsFreeze()) {
				adminUserUtil.setCurrentUser(String.valueOf(user.getId())); //成功登陆，将用户ID放入缓存
				List<Module> permissions = permissionService.findPermissionByUserId(user.getId());
                cacheUtil.setCacheValue(Constants.AUTHORIZE_PREFIX_PERMISSION + user.getId(), permissions);
				userService.updateLoginTime(user.getId(), IPUtil.getIp(request));
				session.setAttribute(Constants.ADMIN_USER, user);
				return "redirect:/admin/homePage";
			} else {
				modelMap.put("errorMsg", "该用户已被冻结，请联系管理员！");
				modelMap.put("user", user);
				return "admin/login";
			}
			
		}
	}
	
	/**
	 * 主菜单
	 * @return
	 */
	@RequestMapping("homePage")
	public String homePage(){
		return "admin/home";
	}
	
	@RequestMapping("login/logout")
	public String logout(HttpServletRequest request){
		userService.logout(request);
		return "redirect:/admin/";
	}
	
	@ResponseBody
	@RequestMapping("getCurrentUser")
	public User getCurrentUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return user;
	}
	
	@RequestMapping("changePassword")
	@ResponseBody
	public Map<String, Object> changePassword(String userId, String origPassword, String newPassword,HttpServletRequest request){
		Map<String, Object> model = Maps.newHashMap();
		if (null != userId) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", userId);
			User user = userService.findOne(map);
			if (!passWordSaltUtil.equalPassword(user.getPassword(), origPassword, user.getCreatedDate())) {
				model.put("errorMsg", "原始密码不正确！");
				model.put("result", false);
			} else {
				user.setPassword(passWordSaltUtil.passWordSalt(newPassword,user.getCreatedDate()));
				userService.update(user);
				model.put("result", true);
			}
			return model;
		} else {
			model.put("errorMsg","当前没有用户登录，请登录后尝试");
			return model;
		}
	}
	
}
