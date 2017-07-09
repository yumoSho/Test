package com.glanway.jty.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.common.Constants;
import com.glanway.jty.entity.perm.User;
import com.glanway.jty.utils.DiffSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.glanway.gone.dao.CrudDao;
import com.glanway.gone.entity.Auditable;
import com.glanway.gone.entity.Idable;
import com.glanway.gone.service.impl.CrudServiceImpl;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.util.IteratorWrapper;
import com.glanway.gone.util.StringUtils;
import com.glanway.jty.dao.perm.UserDao;
import com.glanway.jty.utils.AdminUserUtil;

/**
 * @author vacoor
 */
public abstract class BaseServiceImpl<E, ID extends Serializable>
        extends CrudServiceImpl<E, ID> implements BaseService<E, ID> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseServiceImpl.class);
    public static final String RECORD_BEEN_USED_MSG = "Records have been used, Can't delete it";
    //private BaseDao<E, ID> crudDao;
    @Autowired
    private AdminUserUtil adminUserUtil;
    @Autowired
    private UserDao adminUserDao;
    
    /* (non-Javadoc)
     * 自动注入crudDao
     * @说明 dao必须实现BaseDao从而实现crudDao
     * @see com.glanway.gone.service.impl.CrudServiceImpl#setCrudDao(com.glanway.gone.dao.CrudDao)
     */

    @Override
    @Autowired
    public void setCrudDao(CrudDao<E, ID> crudDao) {
        super.setCrudDao(crudDao);
    }
    
    protected Filters wrap(Filters filters) {
        return IterateNamingTransformFilters.class.isAssignableFrom(filters.getClass()) ? filters : new IterateNamingTransformFilters(filters);
    }

    protected Sort wrap(Sort sort) {
        return IterateNamingTransformSort.class.isAssignableFrom(sort.getClass()) ? sort : new IterateNamingTransformSort(sort);
    }

    /** 
    * @文件名: IterateNamingTransformFilters.java
    * @功能描述: 覆盖了迭代方法的Filters
    */
    protected class IterateNamingTransformFilters extends Filters {
        public IterateNamingTransformFilters(Filters filters) {
            super(filters);
        }

        @Override
        public Iterator<Filter> iterator() {
            return new IteratorWrapper<Filter>(super.iterator()) {
                @Override
                public Filter next() {
                    Filter f = super.next();
                    //遍历filter 生成条件语句
                    return new Filter(transformDynamicProperty(f.getProperty()), f.getOperator(), f.getValues());
                }
            };
        }

    }

    /** 
    * @文件名: IterateNamingTransformSort.java
    * @功能描述: 覆盖了迭代方法的Sort
    */
    protected class IterateNamingTransformSort extends Sort {
        private static final long serialVersionUID = -8648623635266511773L;

        public IterateNamingTransformSort(Sort sort) {
            super(sort);
        }

        @Override
        public Iterator<Order> iterator() {
            return new IteratorWrapper<Order>(super.iterator()) {
                @Override
                public Order next() {
                    Order order = super.next();
                    return new Order(transformDynamicProperty(order.getProperty()), order.getDirection());
                }
            };
        }
    }


    protected String transformDynamicProperty(String property) {
        String prop = StringUtils.camelCaseToUnderscore(property, false);
        return prop;
    }

    @Override
    public void approval(E var1){
        LOG.debug("audit: {}", var1);
        super.update(var1);
    }

    @Override
    public void reject(E var1){
        LOG.debug("audit: {}", var1);
        super.update(var1);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void save(E var1){
        LOG.debug("audit: {}", var1);
        if (var1 instanceof Auditable<?, ?>) {
            Auditable<Long, ?> a = (Auditable<Long, ?>) var1;
            a.setCreatedDate(new Date());
            a.setLastModifiedDate(new Date());
        }
        super.save(var1);
    }
    @Override
    public void update(E e) {
        LOG.debug("update: {}", e);
        if (e instanceof Auditable<?, ?>) {
            Auditable<?, ?> a = (Auditable<?, ?>) e;
            a.setLastModifiedDate(new Date());
        }
        super.update(e);
    }
    
    /** 
    * @功能描述: 获取当前用户id
    * @return   当前用户id    
    */
    public Long getCurrentUserId(){
    	return adminUserUtil.getCurrentUserId();
    }
    
    /** 
    * @功能描述: 获取当前登录用户 
    * @param Id 用户id
    * @return  user实例   
    */
    public User getCurrentUserBean(Long Id){
    	if(null == Id){
    		return null;
    	}
    	User user = adminUserDao.find(Id);
    	user.setPassword(Constants.STRING_EMPTY);
    	return user;
    }
    
    /** 
    * @功能描述: 获取当前登录用户 
    * @return   user实例       
    */
    public User getCurrentUserBean(){
    	Long userId = adminUserUtil.getCurrentUserId();
    	User user = adminUserDao.find(userId);
    	user.setPassword(Constants.STRING_EMPTY);
    	return user;
    }

    /** 
    * @文件名: DiffHandler.java
    * @功能描述: TODO 
    * @date 2016年4月14日 下午6:01:32 
    * 
    * @param <T> 
    */
    protected static abstract class DiffHandler<T extends Idable<?>> extends DiffSwitch.Handler<T, T> {
        protected DiffHandler() {
            super(true);
        }

        @Override
        protected boolean doEquals(T left, T right) {
            if (null == right || null == right.getId()) {
                throw new IllegalStateException("managed entity id must be not null");
            }
            // return left == right || null != left && super.doDefaultEquals(BeanMap.create(left).get("id"), BeanMap.create(right).get("id"));
            return left == right || null != left && super.doDefaultEquals(left.getId(), right.getId());
        }
    }


	/** 
	* @功能描述: 列表查询处理
	* @说明：当页面传入filters做查询时，将任意数量个filters传入一起做处理
	* @param filters 任意数量的Filters
	* @return       
	*/
	public Map<String, Object> createParamsMapByFilters(Filters ...filters) {
		Map<String, Object> paramsMap = super.createParamsMap();
		if(null != filters && filters.length > 0){
			for(Filters filter : filters){
				filter = new IterateNamingTransformFilters(filter);
				paramsMap = super.resolveFiltersToParamsMap(filter, paramsMap);
			}
		}
		return paramsMap;
	}

    /**
     * <p>名称：转化map</p>
     * <p>描述：将filters 和 pageable 转化为map</p>
     * @author：tianxuan
     * @param filters
     * @param pageable
     * @return
     */
    protected  Map<String,Object> filtersAndPageToMap(Filters filters, Pageable pageable){
        filters = new IterateNamingTransformFilters(filters);
        Map<String,Object> params = createParamsMap();
        params.put(CrudDao.FILTERS_PROP, filters);
        if (null != pageable) {
            params.put(CrudDao.OFFSET_PROP, pageable.getOffset());
            params.put(CrudDao.MAX_RESULTS_PROP, pageable.getPageSize());
            Sort sort = pageable.getSort();
            if (null != sort) {
                params.put(CrudDao.SORT_PROP, new IterateNamingTransformSort(sort));
            }
        }
        return params;
    }

    /**
     * 不分页只排序
     * @param filters
     * @param sort
     * @return
     */
    protected  Map<String,Object> FiltersAndPageToMap(Filters filters, Sort sort){
        filters = new IterateNamingTransformFilters(filters);
        Map<String,Object> params = createParamsMap();
        params.put(CrudDao.FILTERS_PROP, filters);
        if (null != sort) {
            params.put(CrudDao.SORT_PROP, new IterateNamingTransformSort(sort));
        }
        return params;
    }
}
