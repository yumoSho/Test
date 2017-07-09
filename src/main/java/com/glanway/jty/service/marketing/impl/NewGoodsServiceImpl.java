package com.glanway.jty.service.marketing.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.dao.marketing.NewGoodsDao;
import com.glanway.jty.entity.marketing.NewGoods;
import com.glanway.jty.service.BaseService;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.marketing.NewGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>名称：NewGoodsServiceImpl</p>
 * <p>描述：爆品推荐</p>
 * @author：LiuJC
 */
@Service
@Transactional
public class NewGoodsServiceImpl extends BaseServiceImpl<NewGoods,Long> implements NewGoodsService{

	private NewGoodsDao newGoodsDao;
	
	@Autowired
	public void setSaleDao(NewGoodsDao newGoodsDao){
		this.newGoodsDao = newGoodsDao;
		super.setCrudDao(newGoodsDao);
	}

	@Override
	public void saveOrUpdate(List<NewGoods> newGoods) {
		List<NewGoods> origNewGoods = findMany((Filters) null, Sort.create().add(Sort.Direction.ASC, "sort"));
		syncAttributes(origNewGoods,newGoods);
	}

	@Override
	public void save(NewGoods newGoods) {
		newGoods.setCreatedDate(new Date());
		newGoods.setLastModifiedDate(new Date());
		super.save(newGoods);
	}

	@Override
	public void update(NewGoods newGoods) {
		newGoods.setLastModifiedDate(new Date());
		super.update(newGoods);
	}


	/**
	 * <p>名称：新品上市管理更新比较</p>
	 * <p>描述：比较</p>
	 * @author：LiuJC
	 * @param origNewGoods 旧值
	 * @param newNewGoods 旧值
	 */
    /*新旧值比较*/
	private void syncAttributes(List<NewGoods> origNewGoods, List<NewGoods> newNewGoods) {
		//如果新对象中的list 为空则全部删除
		if ( CollectionUtils.isEmpty(newNewGoods)) {
			//全部删除
			newGoodsDao.deleteAll();
			return;
		}
        /*如果旧对象中的list 为空怎全部新增*/
		if (CollectionUtils.isEmpty(origNewGoods)) {
			//全部新增
			for(NewGoods newAg : newNewGoods){
				this.save(newAg);
			}
			return;
		}

		Iterator<NewGoods> it = newNewGoods.iterator();
		// 遍历所有新属性参数
		while (it.hasNext() && !origNewGoods.isEmpty()) {
			NewGoods newCurr = it.next();
			NewGoods origCurr = null;
			//新集合中对象进行判空
			if (null == newCurr) {
				continue;
			}
			// 在存在属性列表中查找 -- 判定是否是更新
			for (NewGoods orig : origNewGoods) {
				Long id;
				if (null == orig || null == (id = orig.getId())) {
					continue;
				}
				if (id.equals(newCurr.getId())) {
					this.update(newCurr);
					it.remove();                // 更新后移除保存列表
					origCurr = orig;           // 记录旧列表更新对象
					break;
				}
			}
			origNewGoods.remove(origCurr);          // 从旧列表中移除已更新对象
		}

		// 此时旧列表中对象为被删除的对象
		for (NewGoods origAg : origNewGoods) {
			delete(origAg);
		}
		// 新列表中所有对象应该为新增对象
		for(NewGoods newAg : newNewGoods){
			save(newAg);
		}
	}

	protected Map<String, Object> resolveFiltersToParamsMap(Filters filters, Map<String, Object> paramsMap) {
		if(null == filters) {
			return paramsMap;
		} else {
			if(null == paramsMap) {
				paramsMap = this.createParamsMap();
			}

			boolean needSplit = this.needSplitCascadeProperty();
			Iterator i$ = filters.iterator();

			while(i$.hasNext()) {
				Filters.Filter filter = (Filters.Filter)i$.next();
				Object ref = paramsMap;
				String prop = filter.getProperty();
				Filters.Operator op = filter.getOperator();
				if(prop.equals(BaseService.PROVINCE)){
					paramsMap.put(BaseDao.PROVINCE,((Object[])filter.getValues())[0]);
					continue;
				}
				Object values;
				int index;
				Object orig;
				for(values = filter.getValues(); needSplit && -1 < (index = prop.indexOf(".")); ref = orig) {
					String o = prop.substring(0, index);
					prop = prop.substring(index + 1);
					orig = (Map)((Map)ref).get(o);
					if(null == orig) {
						orig = new HashMap();
						((Map)ref).put(o, orig);
					}
				}

				Object o1 = ((Map)ref).get("_filters");
				if(null == o1) {
					((Map)ref).put("_filters", o1 = Filters.create());
				}

				if(o1 instanceof Filters) {
					Filters orig1 = (Filters)o1;
					orig1.add(this.transformDynamicPropertyIfNecessary(prop), op, (Object[])((Object[])values));
				}
			}

			return paramsMap;
		}
	}
}
