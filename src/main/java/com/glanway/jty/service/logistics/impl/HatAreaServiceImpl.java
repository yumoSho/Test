package com.glanway.jty.service.logistics.impl;

import com.glanway.jty.dao.logistics.HatAreaDao;
import com.glanway.jty.entity.logistics.HatArea;
import com.glanway.jty.service.logistics.HatAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @文件名: HatAreaServiceImpl.java
* @功能描述: 区县信息业务层接口实现
* @author SunF
* @date 2016年4月7日 下午3:45:09 
*  
*/
@Service
public class HatAreaServiceImpl implements HatAreaService {

    @Autowired
    private HatAreaDao hatAreaDao;

    @Override
    public List<HatArea> listAreaBySuperCode(String superCode) {
        return hatAreaDao.listAreaBySuperCode(superCode);
    }

	@Override
	public HatArea queryLikeNameAndSuperCode(String areaName, String superCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("areaName", areaName);
		map.put("superCode", superCode);
		return hatAreaDao.queryLikeNameAndSuperCode(map);
	}
}
