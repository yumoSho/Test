package com.glanway.jty.service.personalcenter.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.personalcenter.CollectService;
import com.glanway.jty.dao.personalcenter.CollectDao;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.personalcenter.Collect;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.exception.CustomException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author  liujichao
 * @date 创建时间：2015年11月07日 上午10:26:02
 * 用户收藏
 * @version 1.0  
 * @since  
 */
@Service
@Transactional
public class CollectServiceImpl extends BaseServiceImpl<Collect, Long> implements CollectService {

	private CollectDao collectDao;

	@Autowired
	public void setCollectionDao(CollectDao collectDao){
        setCrudDao(collectDao);
		this.collectDao = collectDao;
	}

    /**
     * 加入收藏
     * */
	public void addCollect(Long memberId,Long goodsId ,Integer goodsFrom,Long otherId){
		Filters filters = Filters.create();
		filters.add("tgId", Filters.Operator.EQ,goodsId);
		filters.add("tmId", Filters.Operator.EQ,memberId);
		if(null != goodsFrom && 0 != goodsFrom){
			filters.eq("goodsFrom",goodsFrom);
			if(null != otherId && 0 != otherId){
				filters.eq("otherId",otherId);
			}else{
				filters.isNull("otherId");
			}
		}else{
			filters.isNull("goodsFrom");
			filters.isNull("otherId");
		}

		List<Collect> collectList = findMany(filters, (PageRequest) null);
		if(CollectionUtils.isEmpty(collectList)){
			Collect collect = new Collect();
			Goods goods = new Goods();
			goods.setId(goodsId);
			collect.setGoods(goods);
			Member member = new Member();
			member.setId(memberId);
			collect.setMember(member);
			collect.setGoodsFrom(goodsFrom);
			collect.setOtherId(otherId);
			save(collect);
		}else if(collectList.size()==1){
		}else{
			throw new CustomException("数据异常");
		}
	}

}
