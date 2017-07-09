package com.glanway.jty.controller.admin.product;

import com.glanway.gone.spring.bind.PageableDefault;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.product.Brand;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.service.product.BrandService;
import com.glanway.jty.service.product.CategoryService;
import com.glanway.jty.service.product.ModelService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ModelService modelService;

    @RequestMapping("index")
    public String index() {
        return "admin/category/index";
    }

    /**
     * 校验分类名称合法性
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
        return categoryService.count(filters) < 1;
    }


    /**
     * 分类列表
     * @param filters 过滤条件
     * @param pageable 分页条件
     * @return 分页数据
     */
    @ResponseBody
    @RequestMapping("list")
    @PageableDefault(size = Integer.MAX_VALUE)
    public Page<Category> categories(Filters filters, Pageable pageable) {
        Page<Category> categories=categoryService.findPage(filters, pageable);
        return categories;
    }

    /**
     * 分类保存
     * @param category 分类
     * @param redirectAttr    redirec域参数
     * @return 保存成功调首页
     */
    @Token(remove = true)
    @RequestMapping("save")
    public String save(Category category,
            RedirectAttributes redirectAttr) {
        categoryService.save(category);
        categoryService.updateIndexCategoryAndBrand();
        redirectAttr.addFlashAttribute("message", "保存成功");
        return "redirect:/admin/category/index";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "brands", new CustomStringCollectionBeanCollectionPropertyEditor(
                List.class, true, Brand.class, "id", new String[]{}
        ));
    }


    /**
     *新增页面
     * @param model request域参数
     * @return 新增页面
     */
    @Token(save = true)
    @RequestMapping("add")
    public String add(Map<String, Object> model) {

        model.put("brands", brandService.getEnableBrand());
        model.put("models", modelService.findAll());
        return "/admin/category/add";
    }

    /**
     * 编辑页面
     * @param id 分类id
     * @param model request域参数
     * @param redirectAttributes redirect域参数
     * @return 编辑页面
     */
    @Token(save = true)
    @RequestMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Map<String, Object> model,RedirectAttributes redirectAttributes) {
        Category category = categoryService.find(id);
        if (null == category) {
            redirectAttributes.addFlashAttribute(ERROR_MSG_KEY,"未找到该分类");
            return "redirect:/admin/category/index";
        }
        model.put("flag",categoryService.categoryHaveBeenUsedForGoods(id));
        model.put("model", category);
        model.put("brands", brandService.getEnableBrand());
        model.put("models", modelService.findAll());
        return "admin/category/edit";
    }

    /**
     * 更新分类  品牌分类
     * @param category
     * @param redirectAttr
     * @return
     */
    @Token(remove = true)
    @RequestMapping("update")
    public String update(Category category,RedirectAttributes redirectAttr) {

        categoryService.update(category);
        categoryService.updateIndexCategoryAndBrand();
        redirectAttr.addFlashAttribute("message", "更新成功");
        return "redirect:/admin/category/index";
    }

    @ResponseBody
    @RequestMapping("delete")
    public Message delete(@RequestParam("id") Long[] ids) {
        categoryService.delete(ids);
        categoryService.updateIndexCategoryAndBrand();
        return Message.success();
    }

    @ResponseBody
    @RequestMapping("tree")
    public List<Map<String,Object>> cats() {
        List<Category> categories = categoryService.findCategoryTree();
        if(CollectionUtils.isEmpty(categories)){
            return null;
        }
        final Function<Category, Map<String, Object>> transFunc = new Function<Category, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(Category input) {
                Map<String, Object> ret = Maps.newHashMap();
                ret.put("id", input.getId());
                ret.put("pId", null==input.getParent()?null:input.getParent().getId());
                ret.put("name", input.getName());
                ret.put("modelId", null == input.getModel()?null:input.getModel().getId());
                ret.put("modelName", null == input.getModel()?null:input.getModel().getName());
                ret.put("children", Lists.transform(input.getChildren(), this));
                ret.put("brands", input.getBrands());
                ret.put("path", input.getPath());
                ret.put("pathNames", input.getPathNames());
                return ret;
            }
        };
        return Lists.transform(categories, transFunc);
    }

}
