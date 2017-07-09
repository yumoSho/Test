/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 *
 * ProjectName: hg
 * FileName: ActivityServiceImpl
 * PackageName: com.glanway.hg.service.activity.impl
 * Date: 2016年4月29日下午5:48:34
 **/
package com.glanway.jty.service.marketing.impl;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.dao.marketing.ActivityMgrDao;
import com.glanway.jty.entity.marketing.ActivityGoods;
import com.glanway.jty.entity.marketing.ActivityMgr;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseService;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.marketing.ActivityGoodsService;
import com.glanway.jty.service.marketing.ActivityMgrService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>名称：活动管理</p>
 * <p>描述：活动管理</p>
 * @author：LiuJC
 * @date：2016/5/13 14:04
 * @version: 1.0
 */
@Service
@Transactional
public class ActivityServiceImpl extends BaseServiceImpl<ActivityMgr, Long> implements ActivityMgrService {
    @Autowired
    private ActivityGoodsService activityGoodsService;

    @Autowired
    private ActivityMgrDao activityMgrDao;

    /**
     * <p>名称：活动管理保存</p>
     * <p>描述：保存</p>
     * @author：LiuJC
     * @param activityMgr　活动管理
     */
    @Override
    public void save(ActivityMgr activityMgr) {
        super.save(activityMgr);
        List<ActivityGoods> activityGoodses = activityMgr.getActivityGoodses();
        if(!CollectionUtils.isEmpty(activityGoodses)){
            for(ActivityGoods ag:activityGoodses){
                ag.setActivity(activityMgr);
                activityGoodsService.save(ag);
            }
        }else{
            throw new CustomException("未找到活动商品");
        }
    }


    /**
     * <p>名称：活动管理更新</p>
     * <p>描述：更新</p>
     * @author：LiuJC
     * @param activityMgr　活动管理
     */
    @Override
    public void update(ActivityMgr activityMgr) {
        super.update(activityMgr);
        Filters filters = Filters.create().eq("tam.id", activityMgr.getId());
        Sort sort = Sort.create().add(Sort.Direction.ASC, "sort");
        List<ActivityMgr> activityMgrs = findMany(filters, sort);
        syncAttributes(activityMgrs.get(0),activityMgr);
    }

    /**
     * <p>名称：活动管理更新比较</p>
     * <p>描述：比较</p>
     * @author：LiuJC
     * @param origActivityMgr 旧值
     * @param newActivityMgr 旧值
     */
    /*新旧值比较*/
    private void syncAttributes(ActivityMgr origActivityMgr, ActivityMgr newActivityMgr) {
        //如果新对象中的list 为空则全部删除
        List<ActivityGoods> newActivityGoodses = newActivityMgr.getActivityGoodses();
        if ( CollectionUtils.isEmpty(newActivityGoodses)) {
            //全部删除
            activityGoodsService.deleteByActivityId(origActivityMgr.getId());
            return;
        }
        /*如果旧对象中的list 为空怎全部新增*/
        List<ActivityGoods> origActivityGoodses = origActivityMgr.getActivityGoodses();
        if (CollectionUtils.isEmpty(origActivityGoodses)) {
            //全部新增
            for(ActivityGoods newAg : newActivityGoodses){
                newAg.setActivity(origActivityMgr);
                activityGoodsService.save(newAg);
            }
            return;
        }

        Iterator<ActivityGoods> it = newActivityGoodses.iterator();
        // 遍历所有新属性参数
        while (it.hasNext() && !origActivityGoodses.isEmpty()) {
            ActivityGoods newActivityGoods = it.next();
            ActivityGoods origActivityGoods = null;
            //新集合中对象进行判空
            if (null == newActivityGoods) {
                continue;
            }
            // 在存在属性列表中查找 -- 判定是否是更新
            for (ActivityGoods orig : origActivityGoodses) {
                Long id;
                if (null == orig || null == (id = orig.getId())) {
                    continue;
                }
                if (id.equals(newActivityGoods.getId())) {
                    activityGoodsService.update(newActivityGoods);
                    it.remove();                // 更新后移除保存列表
                    origActivityGoods = orig;           // 记录旧列表更新对象
                    break;
                }
            }
            origActivityGoodses.remove(origActivityGoods);          // 从旧列表中移除已更新对象
        }

        // 此时旧列表中对象为被删除的对象
        for (ActivityGoods origAg : origActivityGoodses) {
            activityGoodsService.delete(origAg);
        }
        // 新列表中所有对象应该为新增对象
        for(ActivityGoods newAg : newActivityGoodses){
            newAg.setActivity(origActivityMgr);
            activityGoodsService.save(newAg);
        }
    }

    /**
     * <p>名称：删除活动</p>
     * <p>描述：删除活动</p>
     * @author：LiuJC
     * @param activityMgr 活动
     */
    @Override
    public void delete(ActivityMgr activityMgr) {
        super.delete(activityMgr);
        activityGoodsService.deleteByActivityId(activityMgr.getId());
    }

    /**
     * <p>名称：活动管理详情</p>
     * <p>描述：活动管理详情</p>
     * @author：LiuJC
     * @param id 活动id
     * @return
     */
    @Override
    public ActivityMgr findDetail(Long id,Long provinceId) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("id",id);
        map.put("provinceId",provinceId);
        map.put("nowDate",new Date());
        return activityMgrDao.findDetail(map);
    }

    public Page<ActivityMgr> findPage(Filters filters, Pageable pageable) {
        Iterator<Filters.Filter> iterator = filters.iterator();
        Boolean  flag = false;
        while(iterator.hasNext()){
            Filters.Filter next = iterator.next();
            if(BaseService.PROVINCE.equals(next.getProperty())){
                Object[] values = (Object[]) next.getValues();
                if(values.length ==1 && values[0] ==999){
                    continue;
                }else{
                    flag =true;
                    break;
                }
            }
        }
        int count = 0;
        if(flag){
            Map<String, Object> paramsMap = this.createParamsMap();
            count = activityMgrDao.countByProvinceId(this.resolveFiltersToParamsMap(filters, paramsMap));
        }else{
            count = this.count(filters);
        }
        List data = count > 0?this.findMany(filters, pageable):Collections.emptyList();
        return new SimplePage(pageable, data, count);
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


