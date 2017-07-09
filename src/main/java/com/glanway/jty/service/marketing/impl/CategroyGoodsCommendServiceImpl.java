package com.glanway.jty.service.marketing.impl;


import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.dao.marketing.CategoryGoodsCommendDao;
import com.glanway.jty.dao.marketing.CategoryRecommendDao;
import com.glanway.jty.entity.marketing.CategoryGoods;
import com.glanway.jty.entity.marketing.CategoryGoodsCommend;
import com.glanway.jty.entity.marketing.CategoryRecommend;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseService;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.marketing.CategoryGoodsCommendService;
import com.glanway.jty.service.marketing.CategoryGoodsService;
import com.glanway.jty.service.marketing.CategoryRecommendService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class CategroyGoodsCommendServiceImpl extends BaseServiceImpl<CategoryGoodsCommend, Long> implements CategoryGoodsCommendService {

	Logger logger = Logger.getLogger(CategroyGoodsCommendServiceImpl.class);

	@Autowired
	private CategoryGoodsCommendDao categoryGoodsCommendDao;
	@Autowired
	private CategoryGoodsService categoryGoodsService;

	@Override
	public void save(CategoryGoodsCommend categoryGoodsCommend) {
		if(null == categoryGoodsCommend.getPic() || CollectionUtils.isEmpty(categoryGoodsCommend.getCategoryGoodses())){
			throw new CustomException("数据不完整");
		}
		super.save(categoryGoodsCommend);
		for(CategoryGoods cg:categoryGoodsCommend.getCategoryGoodses()){
			cg.setCategoryGoodsCommend(categoryGoodsCommend);
			categoryGoodsService.save(cg);
		}
	}

	@Override
	public void update(CategoryGoodsCommend categoryGoodsCommend) {
		categoryGoodsService.deleteByCategoryGoodsCommendId(categoryGoodsCommend.getId());
		super.update(categoryGoodsCommend);
		for(CategoryGoods cg:categoryGoodsCommend.getCategoryGoodses()){
			cg.setCategoryGoodsCommend(categoryGoodsCommend);
			categoryGoodsService.save(cg);
		}
	}

	@Override
	public CategoryGoodsCommend findDetail(Long id) {
		return categoryGoodsCommendDao.findDetail(id);
	}

	@Override
	public void delete(CategoryGoodsCommend categoryGoodsCommend) {
		super.delete(categoryGoodsCommend);
		categoryGoodsService.deleteGoodsCommendId(categoryGoodsCommend.getId());
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

	@Override
	public Page<CategoryGoodsCommend> findPage2(Filters filters, Pageable pageable) {
		int count = this.count(filters);
		List data = count > 0?this.findMany2(filters, pageable): Collections.emptyList();
		return new SimplePage(pageable, data, count);
	}

	public List<CategoryGoodsCommend> findMany2(Filters filters, Pageable pageable) {
		Map paramsMap = this.createParamsMap();
		paramsMap = this.resolveFiltersToParamsMap(filters, paramsMap);
		paramsMap = this.resolvePageableToParamsMap(pageable, paramsMap);
		return this.findMany2(paramsMap);
	}
	public List<CategoryGoodsCommend> findMany2(Map<String, ?> params) {
		params = this.needSplitCascadeProperty()?this.resolveCascade(params):params;
		return categoryGoodsCommendDao.findMany2(params);
	}

}
