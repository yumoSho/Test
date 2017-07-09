package com.glanway.jty.interceptor;

import com.glanway.jty.entity.perm.Module;
import com.glanway.jty.entity.perm.Page;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.service.perm.InterceptorService;
import com.glanway.jty.service.perm.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private InterceptorService interceptorService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        String requestUri = request.getRequestURI();
        if (!requestUri.contains("/admin")) {
            return true;
        }
        /*-
         * 获取模块之后内容的第一个"/"的索引, eg: /ctx/admin/module/index --> module/index 之间"/" 的索引
         * /ctx/admin/module --> -1
         */
        int endIndex = requestUri.indexOf("/", requestUri.indexOf("admin") + 6);
        /*-a
    	 *  /ctx/admin/module/index --> admin/module/
    	 *  /ctx/admin/module --> admin/module
    	 */
        int pathIndex = requestUri.indexOf("admin");
        String moduleUrl = -1 < endIndex
                ? requestUri.substring(pathIndex, endIndex + 1)
                : requestUri.substring(pathIndex);

        request.setAttribute("moduleUrl", moduleUrl);
        request.setAttribute("requestUri", requestUri);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // 如果当前用户不存在
        if (null == user) {
			/*-
			 * update yangchanghe 2015.11.21
			 * 修改 forward 为 redirect
			 */
            String contextPath = request.getContextPath();
            String uri = contextPath + "/admin/";
            if (!uri.equals(requestUri)) {
                response.sendRedirect(uri);
            } else {
                 request.getRequestDispatcher("/WEB-INF/view/admin/login.jsp").forward(request, response);
            }
            return false;
        } else {
            List<Module> moduleList = permissionService.getPermissionFromCache();
            if(null == moduleList){
                session.removeAttribute("user");
                request.getRequestDispatcher("/WEB-INF/view/admin/login.jsp").forward(request, response);
            }
            //对模块根据sort来排序
            Module[] arr = (Module[]) moduleList.toArray(new Module[moduleList.size()]);
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[i].getSort() > arr[j].getSort()) {
                        Module module = arr[i];
                        arr[i] = arr[j];
                        arr[j] = module;
                    }
                }
            }
            List<Module> modules = new ArrayList<Module>(Arrays.asList(arr));
            for (Module module : modules) {
//				List<Page> pages = interceptorService.getPagesByModuleId(module.getId());
////				Collections.sort(pages, new Comparator<Page>(){
////					@Override
////					public int compare(Page p1, Page p2) {
////						if (null != p1 && null != p2) {
////							return -(p1.getSort() - p2.getSort());
////						}
////						return 0;
////					}
////				});
//				module.setPages(pages);
                for (Page page : module.getPages()) {
                    if (page.getUrl().indexOf(moduleUrl) != -1) {
                        request.setAttribute("mid", module.getId());
                        break;
                    }
                }
            }
            request.setAttribute("modules", modules);
//            request.setAttribute("shopIdZhao", shopId);
            //todo 如果访问路径是该用户没有的权限，那么直接跳到home页面    当request  里面没有包含mid ，就是没有权限

//            if(null==request.getAttribute("mid")&&! moduleUrl.equals("admin/homePage")&&! moduleUrl.equals("admin/login/")) {
//                request.getRequestDispatcher("/WEB-INF/view/admin/home.jsp").forward(request, response);
//                return false;
//            }
            if ("/admin".equals(requestUri) || "/admin/".equals(requestUri)) {
                request.getRequestDispatcher("/WEB-INF/view/admin/home.jsp").forward(request, response);
                return false;
            } else {
                return true;
            }
        }
    }

}
