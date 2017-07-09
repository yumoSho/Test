package com.glanway.jty.service.product.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glanway.jty.dao.product.ConsultDao;
import com.glanway.jty.entity.product.Consult;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.ConsultService;
import com.glanway.jty.utils.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 咨询serviceImpl
 */
@Service
@Transactional
public class ConsultServiceImpl extends BaseServiceImpl<Consult, Long> implements ConsultService{
    @Autowired
	private ConsultDao consultDao;

    /**
     * <p>名称：getConsultById</p>
     * <p>描述：获取咨询信息</p>
     * @author：LiuJC
     * @param id
     * @return
     */
	@Override
	public Consult getConsultById(Long id) {
		return consultDao.getConsultById(id);
	}

    /**
     * <p>名称：getConsultByPage</p>
     * <p>描述：获取咨询分页</p>
     * @author：LiuJC
     * @param map
     * @return
     */
	@Override
	public List<Consult> getConsultByPage(Map<String, Object> map) {
		Page pager = (Page) map.get("pager");
		Integer _offset = (pager.getCurPage() - 1) * pager.getPageSize();
		Integer _maxResults = pager.getPageSize();
		map.put("_offset", _offset);
		map.put("_maxResults", _maxResults);
		List<Consult> consults = consultDao.findMany(map);
		return consults;
	}

    /**
     * <p>名称：findConsultByIdList</p>
     * <p>描述：咨询分页查询</p>
     * @author：LiuJC
     * @param map
     * @return
     */
    //todo 有待验证
    @Override
    public List<Consult> findConsultByIdList(Map<String, Object> map) {
        Page pager = (Page) map.get("pager");
        Integer _offset = (pager.getCurPage() - 1) * pager.getPageSize();
        Integer _maxResults = pager.getPageSize();
        map.put("_offset", _offset);
        map.put("_maxResults", _maxResults);
        List<Consult> consults = consultDao.findConsultByIdList(map);
        return consults;
    }

    /**
     * <p>名称：findConsultByIdCount</p>
     * <p>描述：咨询信息总记录数查询</p>
     * @author：LiuJC
     * @param map
     * @return
     */
    //todo 有待验证
    @Override
    public int findConsultByIdCount(Map<String, Object> map) {
        return consultDao.findConsultByIdCount(map);
    }

    /**
     * <p>名称：getConsults</p>
     * <p>描述：分页查询分页</p>
     * @author：LiuJC
     * @param page
     * @param goodsId
     * @return
     */
    //todo 有待验证
    @Override
    public List<Consult> getConsults(Page page,Long goodsId) {
        Map<String,Object> map=new HashMap<String,Object>();
        if(page.getCurPage()<1)
            page.setCurPage(1);
        if(page.getPageSize()<1)
            page.setPageSize(5);
        map.put("goodsId", goodsId);
        int count2 = this.findConsultByIdCount(map);
        page.setTotalCount(count2);
        if(count2<1)
            return null;
        page.setTotalPage((count2 + page.getPageSize() - 1)
                / page.getPageSize());
        if(page.getCurPage()>page.getTotalPage())
            page.setCurPage(page.getTotalPage());
        page.setBigenPage();
        page.setEndPage();
        map.put("pager", page);
        List<Consult> consultList = this.findConsultByIdList(map);
        return consultList;
    }
}
