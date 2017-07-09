package com.glanway.jty.controller.admin.product;

import com.glanway.gone.spring.bind.FiltersConfig;
import com.glanway.gone.spring.bind.PageableDefault;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.util.StringUtils;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.product.*;
import com.glanway.jty.service.product.AttributeService;
import com.glanway.jty.service.product.AttributeValueService;
import com.glanway.jty.service.product.ModelService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *  商品模型
 */
@Controller
@RequestMapping("/admin/model")
public class ModelController extends BaseController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private AttributeService attributeService;
    @Autowired
    private AttributeValueService attributeValueService;

    @RequestMapping("index")
    public String index() {
        return "/admin/model/index";
    }

    /**
     * <p>名称：models</p>
     * <p>描述：获取所有模型</p>
     * @author：LiuJC
     * @return
     */
    @ResponseBody
    @RequestMapping("models")
    public List<Model> models() {
        //return modelService.findAll();
        return null;
    }

    /**
     * <p>名称：check</p>
     * <p>描述：校验名称是否有效</p>
     * @author：LiuJC
     * @param name
     * @param id
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
        return modelService.count(filters) < 1;
    }

    /**
     *
     * <p>名称：list</p>
     * <p>描述：列表查询</p>
     * @author：LiuJC
     * @param filters
     * @param pageable
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    @FiltersConfig(dateFormats = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"})
    @PageableDefault(page = 1, size = 30)
    public Page<Model> list(
            Filters filters,
            @PageableDefault(sort = {"createdDate-desc"})
            Pageable pageable) {
        Page<Model> models= modelService.findPage(filters, pageable);
        return models;
    }

    /**
     * 新增页面
     * <p>名称：add</p>
     * <p>描述：新增</p>
     * @author：LiuJC
     *
     */
    @Token(save = true)
    @RequestMapping("add")
    public String add() {
        return "admin/model/add";
    }

    /**
     *
     * <p>名称：save</p>
     * <p>描述：模型保存</p>
     * @author：LiuJC
     * @param model
     * @param redirectAttr
     * @return
     */
    @Token(remove = true)
    @RequestMapping("save")
    public String save(Model model, RedirectAttributes redirectAttr) {
        cleanModel(model);
        modelService.save(model);
        redirectAttr.addFlashAttribute(SUCCESS_MSG_KEY, "保存成功");;
        return "redirect:/admin/model/index";
    }

    /**
     * <p>名称：edit</p>
     * <p>描述：模型编辑</p>
     * @author：LiuJC
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     */
    @Token(save = true)
    @RequestMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Map<String, Object> model,RedirectAttributes redirectAttributes) {
        Model m = modelService.findDetail(id);
        if (m == null) {
            redirectAttributes.addFlashAttribute(ERROR_MSG_KEY, "不存在此模型");
            return "redirect:/admin/model/index";
        }
        model.put("flag",modelService.modelHaveBeenUsedForGoods(id));
        model.put("model", m);
        model.put("referenced", modelService.modelHaveBeenUsedForGoods(id));
        return "/admin/model/edit";
    }

    /**
     * <p>名称：update</p>
     * <p>描述：模型更新</p>
     * @author：LiuJC
     * @param model
     * @param redirectAttr
     * @return
     */
    @Token(remove = true)
    @RequestMapping("update")
    public String update(Model model, RedirectAttributes redirectAttr) {
        cleanModel(model);
        modelService.update(model);
        redirectAttr.addFlashAttribute(SUCCESS_MSG_KEY, "更新成功");
        return "redirect:/admin/model/index";
    }

    /**
     *
     * <p>名称：delAttr</p>
     * <p>描述：删除模型属性</p>
     * @author：LiuJC
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delAttr")
    @Deprecated
    public Map<String, Object> delAttr(@RequestParam("id") Long id) {
        attributeService.delete(id);
        return ImmutableMap.<String, Object>of("success", true);
    }

    /**
     *
     * <p>名称：delAttrVal</p>
     * <p>描述：删除模型属性值</p>
     * @author：LiuJC
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delAttrVal")
    @Deprecated
    public Map<String, Object> delAttrVal(@RequestParam("id") Long id) {
        attributeValueService.delete(id);
        return ImmutableMap.<String, Object>of("success", true);
    }

    /**
     * <p>名称：delModelSpec</p>
     * <p>描述：删除模型规格</p>
     * @author：LiuJC
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delModelSpec")
    @Deprecated
    public Map<String, Object> delModelSpec(@RequestParam("id") Long id) {
        modelService.deleteModelSpec(id);
        return ImmutableMap.<String, Object>of("success", true);
    }

    /**
     *
     * <p>名称：delete</p>
     * <p>描述：删除模型</p>
     * @author：LiuJC
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Map<String, Object> delete(@RequestParam("id") Long[] id) {
        modelService.delete(id);
        return ImmutableMap.<String, Object>of("success", true);
    }

    /**
     * <p>名称：cleanModel</p>
     * <p>描述：清除无用数据</p>
     * @author：LiuJC
     * @param model
     */
    private void cleanModel(Model model) {
        if (model == null) {
            return;
        }

        // clean empty attributes
        List<Attribute> attrs = Lists.newArrayList();
        if (model.getUseAttribute() && (attrs = model.getAttributes()) != null) {
            Iterator<Attribute> it = attrs.iterator();
            while (it.hasNext()) {
                Attribute attr = it.next();
                if (attr == null || !StringUtils.hasLength(attr.getName())) {
                    it.remove();
                }
                List<AttributeValue> values = attr.getAttributeValues();
                if (null == values) {
                    continue;
                }
                Iterator<AttributeValue> vit = values.iterator();
                if (vit == null) {
                    continue;
                }
                while (vit.hasNext()) {
                    AttributeValue value = vit.next();
                    if (value == null || !StringUtils.hasLength(value.getValue())) {
                        vit.remove();
                    }
                }
            }
        } else {
            model.setAttributes(attrs);
        }

        // clean empty parameters
        List<Parameter> params = Lists.newArrayList();
        if (model.getUseParameter() && (params = model.getParameters()) != null) {
            // parameter group
            Iterator<Parameter> it = params.iterator();
            while (it.hasNext()) {
                Parameter g = it.next();
                if (g == null || !StringUtils.hasLength(g.getName())) {
                    it.remove();
                }
                // parameter
                List<Parameter> children = g.getChildren();
                if (children != null) {
                    Iterator<Parameter> cit = children.iterator();
                    while (cit.hasNext()) {
                        Parameter c = cit.next();
                        if (c == null || !StringUtils.hasLength(c.getName())) {
                            cit.remove();
                        }
                    }
                }
            }
        } else {
            model.setParameters(params);
        }

        // clean empty specifications
        List<ModelSpec> modelSpecs = Lists.newArrayList();
        if (model.getUseSpec() && (modelSpecs = model.getModelSpecs()) != null) {
            Iterator<ModelSpec> it = modelSpecs.iterator();
            while (it.hasNext()) {
                ModelSpec modelSpec = it.next();
                if (modelSpec == null || modelSpec.getSpec() == null) {
                    it.remove();
                }
            }
        } else {
            model.setModelSpecs(modelSpecs);
        }
    }

    /**
     * <p>名称：basicModel</p>
     * <p>描述：基础模型页面</p>
     * @author：LiuJC
     * @param model 页面携带参数
     * @param redirectAttributes 参数有误携带消息
     * @return  基础属性页面
     */
    @Token(save = true)
    @RequestMapping("base")
    public String basicModel(Map<String, Object> model,RedirectAttributes redirectAttributes) {
        Model m = modelService.findBaseModel();
        if(null == m){
            redirectAttributes.addFlashAttribute(ERROR_MSG_KEY,"无基础模型");
            return "redirect:/admin/model/index";
        }
        model.put("m", m);
        return "admin/model/base";
    }

    /**
     *
     * <p>名称：saveBaseModel</p>
     * <p>描述：基础模型保存</p>
     * @author：LiuJC
     * @param model
     * @return
     */
    @Token(remove = true)
    @ResponseBody
    @RequestMapping("base/save")
    public Map<String, Object> saveBaseModel(Model model) {
        modelService.saveBaseModel(model);
        return ImmutableMap.<String, Object>of("success", "更新成功");
    }

    /**
     * <p>名称：detail</p>
     * <p>描述：模型详情</p>
     * @author：LiuJC
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("detail/{id}")
    public Map<String, Object> detail(@PathVariable("id") Long id) {

        Model data = modelService.findDetail(id);
        return ImmutableMap.<String, Object>of("success", true, "data", data);
    }
}