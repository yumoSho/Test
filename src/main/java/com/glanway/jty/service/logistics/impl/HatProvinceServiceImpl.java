package com.glanway.jty.service.logistics.impl;

import com.glanway.jty.common.Constants;
import com.glanway.jty.dao.logistics.HatProvinceDao;
import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.service.logistics.HatProvinceService;
import com.glanway.jty.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/** 
* @文件名: HatProvinceServiceImpl.java
* @功能描述: 省份信息业务层实现
* @author SunF
* @date 2016年4月7日 下午3:03:24 
*  
*/
@Service
public class HatProvinceServiceImpl implements HatProvinceService {

    @Autowired
    private HatProvinceDao hatProvinceDao;
    @Autowired
    private CacheUtil cacheUtil ;

    @Override
    public List<HatProvince> listAllProvince() {
        return hatProvinceDao.listAllProvince();
    }

    @Override
     public HatProvince listProvinceInfoByCode(String prvinceCode) {
        return hatProvinceDao.listProvinceInfoByCode(prvinceCode);
    }
    
    @Override
    public List<HatProvince> listALLProvinceAndCity() {
        return hatProvinceDao.listAllProvinceAndCity();
    }
    @Override
    @Deprecated
    public List<HatProvince> listMunicipalities() {
        return hatProvinceDao.listMunicipalities();
    }

	@Override
	public HatProvince queryLikeName(String provinceName) {
		return hatProvinceDao.queryLikeName(provinceName);
	}


}
