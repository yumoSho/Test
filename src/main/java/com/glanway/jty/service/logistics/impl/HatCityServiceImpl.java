package com.glanway.jty.service.logistics.impl;

import com.glanway.jty.dao.logistics.HatCityDao;
import com.glanway.jty.entity.logistics.HatCity;
import com.glanway.jty.service.logistics.HatCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2014/10/27.
 */
@Service
public class HatCityServiceImpl implements HatCityService {

    @Autowired
    private HatCityDao hatCityDao;

    @Override
    public List<HatCity> listCityBySuperCode(String superCode) {
        return hatCityDao.listCityBySuperCode(superCode);
    }

	@Override
	public HatCity queryLikeNameAndSuperCode(String cityName, String superCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(CITY_NAME, cityName);
		map.put(SUPER_CODE, superCode);
		return hatCityDao.queryLikeNameAndSuperCode(map);
	}

}
