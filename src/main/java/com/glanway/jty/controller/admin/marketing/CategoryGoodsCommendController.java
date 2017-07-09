/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: jintianyuan
 * FileName: CategoryGoodsCommendController.java
 * PackageName: com.glanway.jty.controller.admin.marketing
 * Date: 2016/8/114:35
 **/
package com.glanway.jty.controller.admin.marketing;/**
 * Created by chao on 2016/8/1.
 */

import com.glanway.gone.spring.bind.PageableDefault;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.entity.marketing.CategoryGoodsCommend;
import com.glanway.jty.entity.product.Category;
import com.glanway.jty.service.marketing.CategoryGoodsCommendService;
import com.glanway.jty.service.product.CategoryService;
import com.glanway.jty.service.product.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * <p>名称: </p>
 * <p>说明: TODO</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/8/114:35
 * @version: 1.0
 */
@Controller
@RequestMapping("/admin/goods-commend")
public class CategoryGoodsCommendController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CategoryGoodsCommendService categoryGoodsCommendService;

    /**
     * <p>名称：index</p>
     * <p>描述：首页</p>
     * @author：LiuJC
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "admin/goodsCommend/index";
    }
    /**
     *
     * <p>名称：</p>
     * <p>描述：校验名称是否有效</p>
     * @author：LiuJC
     * @param id
     * @param cid
     */
    @ResponseBody
    @RequestMapping("check")
    public Boolean check(String id,@RequestParam("category.id") String cid) {
        Filters filters = Filters.create();

        if (StringUtils.hasText(id)) {
            filters.ne("tam.id", id);
        }
        if (StringUtils.hasText(cid)) {
            filters.eq("tc.id", cid);
        }
        return categoryGoodsCommendService.count(filters) < 1;
    }

    /**
     * @功能描述: 进入添加页
     * @return
     */
    @RequestMapping("add")
    @Token(save = true)
    public String add(ModelMap model) {
        List<Category> categories = categoryService.topCategory((Integer) null, (Integer) null);
        model.put("categories",categories);
        return "admin/goodsCommend/add";
    }


    @RequestMapping("save")
    @Token(remove = true)
    public String save(CategoryGoodsCommend categoryGoodsCommend, RedirectAttributes redirectAttrs) {
        categoryGoodsCommendService.save(categoryGoodsCommend);
        return "redirect:/admin/goods-commend/index";
    }

    /**
     * 分页过滤活动管理 for easyui
     */
    @ResponseBody
    @RequestMapping("list")
    @PageableDefault(sort = {"createdDate-desc"})
    public Page<CategoryGoodsCommend> list(Filters filters, Pageable pageable) {
        Page<CategoryGoodsCommend> page = categoryGoodsCommendService.findPage2(filters, pageable);
        return page;
    }

    @RequestMapping("edit/{id}")
    @Token(save = true)
    public String edit(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes redirectAttributes) {
        CategoryGoodsCommend detail = categoryGoodsCommendService.findDetail(id);
        model.put("detail",detail);
        List<Category> categories = categoryService.topCategory((Integer) null, (Integer) null);
        model.put("categories",categories);
        return "admin/goodsCommend/edit";
    }

    @RequestMapping("update")
    @Token(remove = true)
    public String update(CategoryGoodsCommend categoryGoodsCommend, RedirectAttributes redirectAttrs) {
        categoryGoodsCommendService.update(categoryGoodsCommend);
        return "redirect:/admin/goods-commend/index";
    }

    /**
     * 删除活动
     * @param ids 数组
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Message delete(@RequestParam("id") Long[] ids) {
        if(null == ids && 0==ids.length){
            return Message.fail("没有有效的推荐");
        }
       categoryGoodsCommendService.delete(ids);
        return Message.success();
    }
}
