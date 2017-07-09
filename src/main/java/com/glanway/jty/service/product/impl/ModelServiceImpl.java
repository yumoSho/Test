package com.glanway.jty.service.product.impl;

import com.glanway.jty.dao.product.*;
import com.glanway.jty.entity.product.*;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ModelServiceImpl extends BaseServiceImpl<Model,Long> implements ModelService{
    @Autowired
    private AttributeDao attributeDao;
    @Autowired
    private AttributeValueDao attributeValueDao;
    @Autowired
    private ParameterDao parameterDao;
    @Autowired
    private ModelSpecDao modelSpecDao;

    private ModelDao modelDao;

    @Autowired
    public void setModelDao(ModelDao modelDao) {
        this.modelDao = modelDao;
        super.setCrudDao(modelDao);
    }

    /**
     * <p>名称：save</p>
     * <p>描述：保存模型</p>
     * @author：LiuJC
     * @param m
     */
    @Override
    public void save(Model m) {
        m.setModelCode(getModelCode());
        super.save(m);
        saveAttributes(m, m.getAttributes(), false);
        saveModelSpecs(m, m.getModelSpecs());
        saveParameterGroups(m, m.getParameters());
    }

    /**
     * <p>名称：getModelCode</p>
     * <p>描述：获取下一个模型coded</p>
     * @author：LiuJC
     * @return
     */
    private String getModelCode(){
        Long nextId = modelNextId();
        nextId=null==nextId?0:nextId;
        return "M"+(nextId+1002);
    }

    /**
     * <p>名称：saveAttributes</p>
     * <p>描述：保存模型的属性 和属性值</p>
     * @author：LiuJC
     * @param m 模型
     * @param attrs 属性
     * @param isBase 是否基础属性
     */
    private void saveAttributes(Model m, List<Attribute> attrs, boolean isBase) {
        if (m == null || attrs == null) {
            return;
        }

        for (Attribute attr : attrs) {
            if (!isBase) {
                attr.setModel(m);
            } else {
                attr.setIsBase(true);
                attr.setModel(null);
            }
            attributeDao.save(attr);
            if(1!=attr.getDisplayType()){
                List<AttributeValue> values = attr.getAttributeValues();
                if (null != values) {
                    for (AttributeValue value : values) {
                        value.setAttribute(attr);
                        attributeValueDao.save(value);
                    }
                }
            }
        }
    }



    /**
     *
     * <p>名称：saveModelSpecs</p>
     * <p>描述：保存模型 规格</p>
     * @author：LiuJC
     * @param m 模型
     * @param specs 模型的规格
     */
    private void saveModelSpecs(Model m, List<ModelSpec> specs) {
        if (m == null || specs == null) {
            return;
        }

        for (ModelSpec spec : specs) {
            spec.setModel(m);
            modelSpecDao.save(spec);
        }
    }

    /**
     * <p>名称：saveParameterGroups</p>
     * <p>描述：保存模型参数</p>
     * @author：LiuJC
     * @param m 模型
     * @param params 模型的参数
     */
    private void saveParameterGroups(Model m, List<Parameter> params) {
        if (m == null || params == null) {
            return;
        }
        // parameter group
        for (Parameter param : params) {
            param.setModel(m);
            parameterDao.save(param);

            // parameter
            List<Parameter> children = param.getChildren();
            if (children != null) {
                for (Parameter c : children) {
                    c.setModel(m);
                    c.setParent(param);
                    parameterDao.save(c);
                }
            }
        }
    }

    /**
     * <p>名称：findDetail</p>
     * <p>描述：获取模型属性详细参数</p>
     * @author：LiuJC
     * @param id 模型id
     * @return model 模型详细信息
     */
    @Override
    public Model findDetail(Long id) {
        Model m = find(id);
        if (null == m) {
            return m;
        }
        Boolean useAttribute = m.getUseAttribute();
        Boolean useParameter = m.getUseParameter();
        Boolean useSpec = m.getUseSpec();

        if (useAttribute) {
            m.setAttributes(attributeDao.findModelAttributes(id));
        }

        if (useParameter) {
            m.setParameters(parameterDao.findModelParameterDetail(id));
        }

        if (useSpec) {
            m.setModelSpecs(modelSpecDao.findModelSpecs(id));
        }

        return m;
    }

    /**
     *
     * <p>名称：isReferenced</p>
     * <p>描述：分类是否关联了模型</p>
     * @author：LiuJC
     * @param mid 模型ID
     * @return boolean 是否已和分类关联
     */
    @Override
    public boolean isReferenced(Long mid) {
        Boolean referenced = modelDao.isReferenced(mid);
        return null != referenced && referenced;
    }

    /**
     *
     * <p>名称：update</p>
     * <p>描述：更新模型 相关参数（属性，规格，参数）</p>
     * @author：LiuJC
     * @param newModel
     */
    @Override
    public void update(Model newModel) {
        Long mid = newModel.getId();
        super.update(newModel);
        //查询出原来旧值
        Model origModel = findDetail(mid);
        syncAttributes(origModel, newModel, false);
        syncParameterGroups(origModel, newModel);
        syncModelSpec(origModel, newModel);
    }

    /**
     *
     * <p>名称：syncAttributes</p>
     * <p>描述：比对后 更新，保存，删除属性</p>
     * @author：LiuJC
     * @param isBase 是否是处理基本模型属性
     */
    private void syncAttributes(Model origModel, Model newModel, boolean isBase) {
        Boolean useAttr = newModel.getUseAttribute();
        List<Attribute> newAttrs = newModel.getAttributes();
        // 非基本模型且禁用属性或者新属性为空，直接清空
        if ((null != useAttr && !useAttr && !isBase) || CollectionUtils.isEmpty(newAttrs)) {
            // attributeDao.deleteBaseAttributes();
            attributeDao.deleteModelAttributes(newModel.getId());
            return;
        }

        List<Attribute> origAttrs = origModel.getAttributes();
        if (CollectionUtils.isEmpty(origAttrs)) {
            saveAttributes(newModel, newAttrs, isBase);
            return;
        }

        Iterator<Attribute> it = newAttrs.iterator();

        // 遍历所有新属性参数
        while (it.hasNext() && !origAttrs.isEmpty()) {
            Attribute newAttr = it.next();
            Attribute origAttr = null;

            if (null == newAttr) {
                continue;
            }

            // 在存在属性列表中查找 -- 判定是否是更新
            for (Attribute orig : origAttrs) {
                Long id;
                if (null == orig || null == (id = orig.getId())) {
                    continue;
                }

                if (id.equals(newAttr.getId())) {
                    if (isBase) {
                        newAttr.setIsBase(true);
                        newAttr.setModel(null);
                    } else {
                        newAttr.setIsBase(false);
                        newAttr.setModel(newModel);
                    }

                    attributeDao.update(newAttr);
                    syncAttributeValues(orig, newAttr);// 如果是更新, 则比对属性值

                    it.remove();                // 更新后移除保存列表
                    origAttr = orig;           // 记录旧列表更新对象

                    break;
                }
            }
            origAttrs.remove(origAttr);          // 从旧列表中移除已更新对象
        }

        // 此时旧列表中对象为被删除的对象
        for (Attribute origAttr : origAttrs) {
            attributeValueDao.deleteByAttributeId(origAttr.getId());
            attributeDao.delete(origAttr);
        }

        // 新列表中所有对象应该为新增对象
        saveAttributes(newModel, newAttrs, isBase);
    }

    /**
     *
     * <p>名称：syncParameterGroups</p>
     * <p>描述：比对后 更新，保存，删除参数</p>
     * @author：LiuJC
     * @origModel 原来模型
     * @param newModel  新模型
     */
    private void syncParameterGroups(Model origModel, Model newModel) {
        Boolean useParam = newModel.getUseParameter();
        List<Parameter> newGroups = newModel.getParameters();
        if ((null != useParam && !useParam) || CollectionUtils.isEmpty(newGroups)) {
            parameterDao.deleteModelParameters(origModel.getId());
            return;
        }

        List<Parameter> origGroups = origModel.getParameters();
        if (CollectionUtils.isEmpty(origGroups)) {
            saveParameterGroups(newModel, newGroups);
            return;
        }

        Iterator<Parameter> it = newGroups.iterator();
        while(it.hasNext() && !origGroups.isEmpty()) {
            Parameter newGroup = it.next();
            Parameter origGroup = null;

            if (null == newGroup) {
                continue;
            }

            for (Parameter oldGroup : origGroups) {
                Long id;
                if (null == oldGroup || null == (id = oldGroup.getId())) {
                    continue;
                }
                if (id.equals(newGroup.getId())) {
                    newGroup.setModel(newModel);
                    parameterDao.update(newGroup);
                    syncParameters(oldGroup, newGroup);

                    it.remove();
                    origGroup = oldGroup;
                    break;
                }
            }
            origGroups.remove(origGroup);
        }

        deleteParameterGroups(origGroups);
        saveParameterGroups(newModel, newGroups);

    }


    /**
     * <p>名称：syncModelSpec</p>
     * <p>描述：比对后 更新，保存，删除规格</p>
     * @author：LiuJC
     * @origModel 原来模型
     */
    private void syncModelSpec(Model origModel, Model newModel) {
        Boolean useSpec = newModel.getUseSpec();
        List<ModelSpec> newModelSpecs = newModel.getModelSpecs();
        if ((null != useSpec && !useSpec) || CollectionUtils.isEmpty(newModelSpecs)) {
            modelSpecDao.deleteModelSpecs(origModel.getId());
            return;
        }

        List<ModelSpec> origModelSpecs = origModel.getModelSpecs();
        if (CollectionUtils.isEmpty(origModelSpecs)) {
            saveModelSpecs(newModel, newModelSpecs);
            return;
        }

        Iterator<ModelSpec> it = newModelSpecs.iterator();
        while(it.hasNext() && !origModelSpecs.isEmpty()) {
            ModelSpec newMs = it.next();
            ModelSpec origMs = null;
            if (null == newMs) {
                continue;
            }

            for (ModelSpec oldMs : origModelSpecs) {
                Long id;
                if (null == oldMs || null == (id = oldMs.getId())) {
                    continue;
                }

                if (id.equals(newMs.getId())) {
                    newMs.setModel(newModel);
                    modelSpecDao.update(newMs);

                    it.remove();
                    origMs = oldMs;
                    break;
                }
            }
            origModelSpecs.remove(origMs);
        }
        for (ModelSpec origModelSpec : origModelSpecs) {
            modelSpecDao.delete(origModelSpec);
        }
        saveModelSpecs(newModel, newModelSpecs);
    }


    /**
     * <p>名称：syncAttributeValues</p>
     * <p>描述：比对更新前后属性并进行相关的 保存、更新、删除操作</p>
     * @author：LiuJC
     * @param origAttr
     * @param newAttr
     */
    private void syncAttributeValues(Attribute origAttr, Attribute newAttr) {
        if (origAttr == newAttr) {
            return;
        }
        List<AttributeValue> origValues = null != origAttr ? origAttr.getAttributeValues() : Collections.<AttributeValue>emptyList();
        List<AttributeValue> newValues = null != newAttr ? newAttr.getAttributeValues() : Collections.<AttributeValue>emptyList();

        // 比对属性值
        // 全部被删除
        if (CollectionUtils.isEmpty(newValues)) {
            for (AttributeValue origValue : origValues) {
                attributeValueDao.delete(origValue);
            }
            return;
        }

        // 全部为新增
        if (CollectionUtils.isEmpty(origValues)) {
            for (AttributeValue newValue : newValues) {
                newValue.setAttribute(newAttr);
                attributeValueDao.save(newValue);
            }
            return;
        }

        Iterator<AttributeValue> valueIt = newValues.iterator();
        while (valueIt.hasNext() && !origValues.isEmpty()) {
            AttributeValue value = valueIt.next();
            AttributeValue origValue = null;

            for (AttributeValue origVal : origValues) {
                Long id;
                if (null == origVal || null == (id = origVal.getId())) {
                    continue;
                }

                if (id.equals(value.getId())) {
                    // DO UPDATE
                    value.setAttribute(newAttr);
                    attributeValueDao.update(value);

                    valueIt.remove();
                    origValue = origVal;
                    break;
                }
            }
            origValues.remove(origValue);

        }

        // 剩余为已删除的 DO DELETE
        for (AttributeValue attrVal : origValues) {
            attributeValueDao.delete(attrVal);
        }

        // 剩余为新增的 DO SAVE
        for (AttributeValue attrVal : newValues) {
            attrVal.setAttribute(newAttr);
            attributeValueDao.save(attrVal);
        }
    }

    /**
     *
     * <p>名称：syncParameters</p>
     * <p>描述：比对更新前后参数并进行相关的 保存、更新、参数</p>
     * @author：LiuJC
     * @param origGroup 原来参数组
     * @param newGroup 新参数组
     */
    private void syncParameters(Parameter origGroup, Parameter newGroup) {
        List<Parameter> newParams = newGroup.getChildren();
        if (CollectionUtils.isEmpty(newParams)) {
            // DELETE CHILDREN
            parameterDao.deleteByParentId(origGroup.getId());
            return;
        }

        List<Parameter> origParams = origGroup.getChildren();
        if (CollectionUtils.isEmpty(origParams)) {
            for (Parameter newParam : newParams) {
                newParam.setParent(newGroup);
                parameterDao.save(newParam);
            }
            return;
        }

        Iterator<Parameter> it = newParams.iterator();
        while(it.hasNext() && !origParams.isEmpty()) {
            Parameter newParam = it.next();
            Parameter origParam = null;

            if (null == newParam) {
                continue;
            }

            for (Parameter oldParam : origParams) {
                Long id;
                if (null == oldParam || null == (id = oldParam.getId())) {
                    continue;
                }
                if (id.equals(newParam.getId())) {
                    newParam.setParent(newGroup);
                    parameterDao.update(newParam);
                    syncParameters(oldParam, newParam);

                    it.remove();
                    origParam = oldParam;
                    break;
                }
            }
            origParams.remove(origParam);
        }

        deleteParameterGroups(origParams);  // 也可删除参数
        for (Parameter newParam : newParams) {
            newParam.setParent(newGroup);
            parameterDao.save(newParam);
        }
    }

    /**
     * <p>名称：deleteParameterGroups</p>
     * <p>描述：删除模型子参数</p>
     * @author：LiuJC
     * @param groups
     */
    private void deleteParameterGroups(List<Parameter> groups) {
        if (CollectionUtils.isEmpty(groups)) {
            return;
        }
        for (Parameter group : groups) {
            List<Parameter> parameters = group.getChildren();
            if (!CollectionUtils.isEmpty(parameters)) {
                for (Parameter parameter : parameters) {
                    parameterDao.delete(parameter);
                }
            }

            parameterDao.delete(group);
        }
    }

    /**
     *
     * <p>名称：deleteModelSpec</p>
     * <p>描述：通过模型ID 删除模型规格</p>
     * @author：LiuJC
     * @param msId
     */
    @Override
    public void deleteModelSpec(Long msId) {

        Boolean flag = modelSpecHaveBeenUsedForGoods(msId);
        if (null != flag && flag) {
            throw new CustomException("有模型规格已被商品关联,不能删除");
        }
        ModelSpec modelSpec = new ModelSpec();
        modelSpec.setId(msId);
        modelSpecDao.delete(modelSpec);
    }

    /**
     *
     * <p>名称：findBaseModel</p>
     * <p>描述：查找基础模型</p>
     * @author：LiuJC
     * @return model 基础模型
     */
    @Override
    public Model findBaseModel(){
        Model m = new Model(); // 后面如果不仅仅是属性时进行修改
        Map<String, Object> paramsMap = createParamsMap();
        paramsMap.put("isBase", true);

        List<Attribute> attrs = attributeDao.findMany(paramsMap);

        m.setAttributes(attrs);
        return m;
    }

    /**
     *
     * <p>名称：saveBaseModel</p>
     * <p>描述：基础属性保存</p>
     * @author：LiuJC
     * @param m 模型
     */
    @Override
    public void saveBaseModel(Model m) {
        Model baseModel = findBaseModel();
        syncAttributes(baseModel, m, true);
    }

    /**
     *
     * <p>名称：modelHaveBeenUsedForGoods</p>
     * <p>描述：模型是否已被使用</p>
     * @author：LiuJC
     * @param modelId 模型
     * @return
     */
    @Override
    public Boolean modelHaveBeenUsedForGoods(Long modelId) {
        return modelDao.modelHaveBeenUsedForGoods(modelId);
    }

    /**
     * <p>名称：modelSpecHaveBeenUsedForGoods</p>
     * <p>描述：模型规格是否已被使用</p>
     * @author：LiuJC
     * @param modelSpecId
     * @return
     */
    @Override
    public Boolean modelSpecHaveBeenUsedForGoods(Long modelSpecId) {
        return modelDao.modelSpecHaveBeenUsedForGoods(modelSpecId);
    }


    /**
     * <p>名称：delete</p>
     * <p>描述：删除模型</p>
     * @param model
     */
    @Override
    public void delete(Model model) {
        Boolean flag = modelHaveBeenUsedForGoods(model.getId());
        if(null != flag && flag){
            throw new CustomException("有模型已被商品引用不能被删除");
        }
        super.delete(model);
    }


    /**
     * <p>名称：modelNextId</p>
     * <p>描述：获取下个id</p>
     * @author：LiuJC
     * @return
     */
    @Override
    public Long modelNextId() {
        return modelDao.modelNextId();
    }
}