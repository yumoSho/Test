package com.glanway.jty.dao.product;



import java.util.List;
import java.util.Map;

import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.product.Consult;

public interface ConsultDao extends BaseDao<Consult, Long>{

	Consult getConsultById(Long id);

    List<Consult> findConsultByIdList(Map<String, Object> map);

    int findConsultByIdCount(Map<String, Object> map);
}
