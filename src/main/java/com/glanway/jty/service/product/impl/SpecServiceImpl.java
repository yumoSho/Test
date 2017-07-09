package com.glanway.jty.service.product.impl;

import com.glanway.jty.dao.product.SpecDao;
import com.glanway.jty.dao.product.SpecValueDao;
import com.glanway.jty.entity.product.Spec;
import com.glanway.jty.entity.product.SpecValue;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规格service实现类
 */
@Service
@Transactional
public class SpecServiceImpl extends BaseServiceImpl<Spec,Long> implements SpecService {
    @Autowired
    private SpecValueDao specValueDao;

    private SpecDao specDao;
    @Autowired
    public void setSpecDao(SpecDao specDao) {
        this.specDao = specDao;
        super.setCrudDao(specDao);
    }




	/**
	 * <p>名称：</p>
	 * <p>描述：保存规格值</p>
	 * @author：LiuJC
	 * @param spv
	 */
	@Override
	public void saveSpecValue(SpecValue spv) {
		specValueDao.save(spv);
	}

	/**
	 *
	 * <p>名称：updateSpecValue</p>
	 * <p>描述：更新规格值</p>
	 * @author：LiuJC
	 * @param spv
	 */
	@Override
	public void updateSpecValue(SpecValue spv) {
		specValueDao.update(spv);		
	}

	/**
	 * <p>名称：findSpecValById</p>
	 * <p>描述：通过规格id 查找规格值集合</p>
	 * @author：LiuJC
	 * @param specId
	 * @return
	 */
	@Override
	public List<SpecValue> findSpecValById(Long specId) {
		return specValueDao.findSpecValByspId(specId);
	}

	/**
	 * <p>名称：specHaveBeenUsedForGoods</p>
	 * <p>描述：是否有规格已被商品使用</p>
	 * @author：LiuJC
	 * @param specId
	 * @return
	 */
	@Override
	public Boolean specHaveBeenUsedForGoods(Long specId) {
		return specDao.specHaveBeenUsedForGoods(specId);
	}

	/**
	 * <p>名称：delete</p>
	 * <p>描述：删除规格</p>
	 * @author：LiuJC
	 * @param spec
	 */
	@Override
	public void delete(Spec spec) {
		Boolean flag = this.specHaveBeenUsedForGoods(spec.getId());
		if(null != flag && flag){
			throw new CustomException("有规格已被商品引用不能被删除");
		}else{
			super.delete(spec);
		}
	}

	/**
	 * <p>名称：specNextId</p>
	 * <p>描述：获取下个规格值的id</p>
	 * @author：LiuJC
	 * @return
	 */
	@Override
	public Long specNextId() {
		return specDao.specNextId();
	}

	/**
	 * <p>名称：getSpecCode</p>
	 * <p>描述：获取下个规格值的code</p>
	 * @author：LiuJC
	 * @return
	 */
	private String getSpecCode(){
		Long nextId = specNextId();
		nextId=null==nextId?0:nextId;
		return "S"+(nextId+1002);
	}

	/**
	 * <p>名称：save</p>
	 * <p>描述：保存规格</p>
	 * @author：LiuJC
	 * @param spec
	 */
	@Override
	public void save(Spec spec) {
		spec.setSpecCode(getSpecCode());
		super.save(spec);

		Map<String, Object> map = new HashMap<>();
		map.put("name", spec.getName());
		Spec spe = this.findOne(map);
		List<SpecValue> SpecVal =  spec.getSpecValues();
		if(null != SpecVal && SpecVal.size()>0) {
			for (SpecValue sv : SpecVal) {
				sv.setSpec(spe);
				saveSpecValue(sv);
			}
		}
	}

	/**
	 * <p>名称：update</p>
	 * <p>描述：更新规格</p>
	 * @author：LiuJC
	 * @param spec
	 */
	@Override
	public void update(Spec spec) {
		super.update(spec);
		Map<String, Object> map = new HashMap<String, Object>();
		List<SpecValue> spcSpecValues = spec.getSpecValues();
		if(null!=spcSpecValues&&spcSpecValues.size()>0){
			for (SpecValue spv : spcSpecValues) {
				if (spv.getId() == null) {
					map.put("id", spec.getId());
					Spec spe = findOne(map);
					spv.setSpec(spe);
					saveSpecValue(spv);
				} else {
					updateSpecValue(spv);
				}
			}
		}
	}

	/**
	 * <p>名称：</p>
	 * <p>描述：通过产品ID查询规格及规格值</p>
	 * @author：LiuJC
	 * @param pid
	 * @return
	 */
	public List<Spec> findSpecAndValuesByProductId(Long pid){
		return specDao.findSpecAndValuesByProductId(pid);
	}
}