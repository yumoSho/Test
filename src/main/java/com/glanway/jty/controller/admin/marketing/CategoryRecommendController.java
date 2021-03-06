package com.glanway.jty.controller.admin.marketing;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.marketing.CategoryRecommend;
import com.glanway.jty.entity.product.Label;
import com.glanway.jty.service.marketing.CategoryRecommendService;
import com.glanway.jty.service.product.LabelService;
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


/**
 * 分类推荐标签管理
 */
@Controller
@RequestMapping("/admin/category-label")
public class CategoryRecommendController extends BaseController {

    @Autowired
    private CategoryRecommendService categoryRecommendService;

    /**
     *
     * <p>名称：</p>
     * <p>描述：标签首页</p>
     * @author：LiuJC
     * @return 页面
     */
    @RequestMapping("index")
    public String index(HttpSession session) {
        return "/admin/categoryLabel/index";
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
            filters.eq("title", name);
        }
        return categoryRecommendService.count(filters) < 1;
    }

    /**
     * 分页过滤品牌 for easyui
     */
    @ResponseBody
    @RequestMapping("list")
    public Page<CategoryRecommend> list(Filters filters, Pageable pageable) {
        return categoryRecommendService.findPage(filters, pageable);
    }

    /**
     * <p>名称：add</p>
     * <p>描述：添加标签</p>
     * @author：LiuJC
     * @param session
     */
    @Token(save = true)
    @RequestMapping("add")
    public String add(HttpSession session) {
        return "/admin/categoryLabel/add";
    }

    /**
     * 保存标签
     */
    @Token(remove = true)
    @RequestMapping("save")
    public String save(CategoryRecommend  categoryRecommend, RedirectAttributes redirectAttrs,HttpSession session) {
        categoryRecommendService.save(categoryRecommend);
        redirectAttrs.addFlashAttribute(SUCCESS_MSG_KEY, "保存成功");
        return "redirect:/admin/category-label/index";
    }

    /**
     * <p>名称：edit</p>
     * <p>描述：编辑标签</p>
     * @author：LiuJC
     * @param id
     * @param model
     * @param redirectAttributes
     * @param session
     * @return
     */
    @Token(save = true)
    @RequestMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes redirectAttributes,HttpSession session) {

        CategoryRecommend categoryRecommend = categoryRecommendService.find(id);
        if (null == categoryRecommend) {
            redirectAttributes.addFlashAttribute(ERROR_MSG_KEY, "不存在此标签");
            return "redirect:/admin/category-label/index";
        }
        model.put("categoryRecommend", categoryRecommend);
        return "/admin/categoryLabel/edit";
    }

    /**
     * <p>名称：</p>
     * <p>描述：更新标签</p>
     * @author：LiuJC
     * @param categoryRecommend
     * @param redirectAttrs
     * @return
     */
    @Token(remove = true)
    @RequestMapping("update")
    public String update(CategoryRecommend categoryRecommend, RedirectAttributes redirectAttrs) {
        categoryRecommendService.update(categoryRecommend);
        redirectAttrs.addFlashAttribute(SUCCESS_MSG_KEY, "更新成功");
        return "redirect:/admin/category-label/index";
    }

    /**
     * <p>名称：</p>
     * <p>描述：删除标签</p>
     * @author：LiuJC
     * @param ids 标签ID数组
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Message delete(@RequestParam("id") Long[] ids) {
        if(null == ids && 0==ids.length){
            return Message.fail("没有有效的分类推荐标签");
        }
        categoryRecommendService.delete(ids);
        return Message.success();
    }

}
