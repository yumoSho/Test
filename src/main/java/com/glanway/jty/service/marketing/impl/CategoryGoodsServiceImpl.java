package com.glanway.jty.service.marketing.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.jty.dao.marketing.CategoryGoodsDao;
import com.glanway.jty.entity.marketing.CategoryGoods;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.marketing.CategoryGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>名称：CategoryGoodsServiceImpl</p>
 * <p>描述：楼层推荐推荐</p>
 * @author：LiuJC
 */
@Service
@Transactional
public class CategoryGoodsServiceImpl extends BaseServiceImpl<CategoryGoods,Long> implements CategoryGoodsService{

	private CategoryGoodsDao categoryGoodsDao;
	
	@Autowired
	public void setSaleDao(CategoryGoodsDao categoryGoodsDao){
		this.categoryGoodsDao = categoryGoodsDao;
		super.setCrudDao(categoryGoodsDao);
	}

	@Override
	public void saveOrUpdate(List<CategoryGoods> newGoods) {
		List<CategoryGoods> origNewGoods = findMany((Filters) null, Sort.create().add(Sort.Direction.ASC, "sort"));
		syncAttributes(origNewGoods,newGoods);
	}

	@Override
	public void save(CategoryGoods categoryGoods) {
		categoryGoods.setCreatedDate(new Date());
		categoryGoods.setLastModifiedDate(new Date());
		super.save(categoryGoods);
	}

	@Override
	public void update(CategoryGoods categoryGoods) {
		categoryGoods.setLastModifiedDate(new Date());
		super.update(categoryGoods);
	}

	public void deleteByCategoryGoodsCommendId(Long id){
		categoryGoodsDao.deleteByCategoryGoodsCommendId(id);
	}

	@Override
	public void deleteGoodsCommendId(Long id) {
		categoryGoodsDao.deleteGoodsCommendId(id);
	}

	/**
	 * <p>名称：新品上市管理更新比较</p>
	 * <p>描述：比较</p>
	 * @author：LiuJC
	 * @param origNewGoods 旧值
	 * @param newNewGoods 旧值
	 */
    /*新旧值比较*/
	private void syncAttributes(List<CategoryGoods> origNewGoods, List<CategoryGoods> newNewGoods) {
		//如果新对象中的list 为空则全部删除
		if ( CollectionUtils.isEmpty(newNewGoods)) {
			//全部删除
			categoryGoodsDao.deleteAll();
			return;
		}
        /*如果旧对象中的list 为空怎全部新增*/
		if (CollectionUtils.isEmpty(origNewGoods)) {
			//全部新增
			for(CategoryGoods newAg : newNewGoods){
				this.save(newAg);
			}
			return;
		}

		Iterator<CategoryGoods> it = newNewGoods.iterator();
		// 遍历所有新属性参数
		while (it.hasNext() && !origNewGoods.isEmpty()) {
			CategoryGoods newCurr = it.next();
			CategoryGoods origCurr = null;
			//新集合中对象进行判空
			if (null == newCurr) {
				continue;
			}
			// 在存在属性列表中查找 -- 判定是否是更新
			for (CategoryGoods orig : origNewGoods) {
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
		for (CategoryGoods origAg : origNewGoods) {
			delete(origAg);
		}
		// 新列表中所有对象应该为新增对象
		for(CategoryGoods newAg : newNewGoods){
			save(newAg);
		}
	}
}
