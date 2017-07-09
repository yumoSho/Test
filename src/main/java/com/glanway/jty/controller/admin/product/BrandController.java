package com.glanway.jty.controller.admin.product;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.product.Brand;
import com.glanway.jty.service.product.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

//import com.glanway.sh.service.product.CategoryService;


/**
 * 品牌管理
 */
@Controller
@RequestMapping("/admin/brand")
public class BrandController extends BaseController {

    @Autowired
    private BrandService brandService;

    /**
     *
     * <p>名称：</p>
     * <p>描述：品牌首页</p>
     * @author：LiuJC
     * @return 页面
     */
    @RequestMapping("index")
    public String index(HttpSession session) {
        return "/admin/brand/index";
    }

    /**
     *
     * <p>名称：</p>
     * <p>描述：校验名称是否有效</p>
     * @author：LiuJC
     * @param id
     * @param name
     */
    @ResponseBody
    @RequestMapping("check")
    public Boolean check(String id, String name) {
        Filters filters = Filters.create();

        if (StringUtils.hasText(id)) {
            filters.ne("id", id);
        }
        if (StringUtils.hasText(name)) {
            filters.eq("name", name);
        }
        return brandService.count(filters) < 1;
    }

    /**
     * 分页过滤品牌 for easyui
     */
    @ResponseBody
    @RequestMapping("list")
    public Page<Brand> list(Filters filters, Pageable pageable) {
        return brandService.findPage(filters, pageable);
    }

    /**
     * <p>名称：add</p>
     * <p>描述：添加品牌</p>
     * @author：LiuJC
     * @param session
     */
    @Token(save = true)
    @RequestMapping("add")
    public String add(HttpSession session) {

        return "/admin/brand/add";
    }

    /**
     * 保存品牌
     */
    @Token(remove = true)
    @RequestMapping("save")
    public String save(Brand brand, RedirectAttributes redirectAttrs,HttpSession session) {
        brandService.save(brand);
        redirectAttrs.addFlashAttribute(SUCCESS_MSG_KEY, "保存成功");
        return "redirect:/admin/brand/index";
    }

    /**
     * <p>名称：edit</p>
     * <p>描述：编辑</p>
     * @author：LiuJC
     * @param id
     * @param model
     * @param redirectAttributes
     * @param session
     * @return
     */
    @Token(save = true)
    @RequestMapping("edit/{id}")
    public String edit(@PathVariable("id") String id, Map<String, Object> model, RedirectAttributes redirectAttributes,HttpSession session) {

        Brand brand = brandService.find(Long.parseLong(id));
        if (null == brand) {
            redirectAttributes.addFlashAttribute(ERROR_MSG_KEY, "不存在此品牌");
            return "redirect:/admin/brand/edit";
        }
        model.put("m", brand);
        return "/admin/brand/edit";
    }

    /**
     * <p>名称：</p>
     * <p>描述：更新品牌</p>
     * @author：LiuJC
     * @param brand
     * @param redirectAttrs
     * @return
     */
    @Token(remove = true)
    @RequestMapping("update")
    public String update(Brand brand, RedirectAttributes redirectAttrs) {
        brandService.update(brand);
        redirectAttrs.addFlashAttribute(SUCCESS_MSG_KEY, "更新成功");
        return "redirect:/admin/brand/index";
    }

    /**
     * <p>名称：</p>
     * <p>描述：删除品牌</p>
     * @author：LiuJC
     * @param ids 品牌ID数组
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Message delete(@RequestParam("id") Long[] ids) {
        if(null == ids && 0==ids.length){
            return Message.fail("没有有效的品牌");
        }
        brandService.delete(ids);
        return Message.success();
    }

}
