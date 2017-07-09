package com.glanway.jty.controller.admin.product;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.product.Spec;
import com.glanway.jty.entity.product.SpecValue;
import com.glanway.jty.service.product.SpecService;
import com.glanway.jty.service.product.SpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 后台规格，增删查改 类；
 */

@Controller
@RequestMapping("/admin/spec")
public class SpecController extends BaseController{
    @Autowired
    private SpecService specService;
    @Autowired
    private SpecValueService specValueService;

    /**
     * <p>名称：index</p>
     * <p>描述：规格首页</p>
     * @author：LiuJC
     * @return
     */
    @RequestMapping("index")
    public String index() {

        return "admin/spec/index";
    }

    /**
     * <p>名称：check</p>
     * <p>描述：检查规格名称是否重复</p>
     * @author：LiuJC
     * @param id 规格id
     * @param name 规格名称
     * @return  返回true 或者false
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
        return specService.count(filters) < 1;
    }

    /**
     *
     * <p>名称：selectOne</p>
     * <p>描述：查询某一规格</p>
     * @author：LiuJC
     * @param id 规格id
     * @return Spec
     */
    @ResponseBody
    @RequestMapping("selectOne")
    public Spec selectOne(Long id) {
        Spec spec = specService.find(id);
        return spec;
    }

    /**
     * <p>名称：list</p>
     * <p>描述：规格列表</p>
     * @author：LiuJC
     * @param filters
     * @param pageable
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Page<Spec> list(Filters filters,Pageable pageable) {
        Page<Spec> specs = specService.findPage(filters, pageable);
        return specs;
    }

    /**
     * <p>名称：add</p>
     * <p>描述：规格新增页面</p>
     * @author：LiuJC
     */
    @Token(save = true)
    @RequestMapping("add")
    public String add() {
        return "admin/spec/add";
    }

    /**
     *
     * <p>名称：save</p>
     * <p>描述：规格保存页面</p>
     * @author：LiuJC
     * @param spec 规格对象
     * @param redirectAttrs
     * @return  重定向页面 只首页
     */
    @Token(remove = true)
    @RequestMapping("save")
    public String save(Spec spec, RedirectAttributes redirectAttrs) {
        clean(spec);
        specService.save(spec);
        redirectAttrs.addFlashAttribute(SUCCESS_MSG_KEY, "保存成功");
        return "redirect:/admin/spec/index";
    }

    /**
     * <p>名称：edit</p>
     * <p>描述：编辑页面</p>
     * @author：LiuJC
     * @param id 要编辑的规格id
     * @param model request域的参数
     * @return  返回的页面
     */
    @Token(save = true)
    @RequestMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Map<String, Object> model,RedirectAttributes redirectAttributes) {
        Spec spec = specService.find(id); 
        
        List<SpecValue> specValue=  specService.findSpecValById(id);
        spec.setSpecValues(specValue);
        if (null == spec) {
            redirectAttributes.addFlashAttribute(ERROR_MSG_KEY,"未查找到当前规格");
        }
        model.put("m", spec);
        return "/admin/spec/edit";
    }

    /**
     *
     * <p>名称：update</p>
     * <p>描述：更新规格</p>
     * @author：LiuJC
     * @param spec 要更新的规格对象
     * @param redirectAttrs 重定向域重的参数
     * @return 返回规格首页
     */
    @Token(remove = true)
    @RequestMapping("update")
    public String update(Spec spec, RedirectAttributes redirectAttrs) {
        clean(spec);
        specService.update(spec);
        redirectAttrs.addFlashAttribute(SUCCESS_MSG_KEY, "更新成功");
        return "redirect:/admin/spec/index";
    }

    /**
     *
     * <p>名称：delete</p>
     * <p>描述：删除规格</p>
     * @author：LiuJC
     * @param ids 规格id集合
     * @return Message 是否删除成功
     */
    @ResponseBody
    @RequestMapping("delete")
    public Message delete(@RequestParam("id") Long[] ids) {
        specService.delete(ids);
        return Message.success();
    }

    /**
     * <p>名称：deleteSpecValue</p>
     * <p>描述：删除某一规格值</p>
     * @author：LiuJC
     * @param id 规格值
     * @return Message
     */
    @ResponseBody
    @RequestMapping("deleteSpecValue/{id}")
    public Message deleteSpecValue(@PathVariable("id") Long id) {
         specValueService.delete(id);
         Message m = Message.success();
         return m;
    }

    /**
     * <p>名称：clean</p>
     * <p>描述：删除无用规格，或者无用的规格值</p>
     * @author：LiuJC
     * @param spec
     */
    private void clean(Spec spec) {
        List<SpecValue> values;
        if (spec == null || (values = spec.getSpecValues()) == null) {
            return;
        }
        Iterator<SpecValue> it = values.iterator();
        while (it.hasNext()) {
            SpecValue value = it.next();
            if (value == null || StringUtils.isEmpty(value.getName())) {
                it.remove();
            }
        }
    }
}