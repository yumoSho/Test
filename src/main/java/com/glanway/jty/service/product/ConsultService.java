package com.glanway.jty.service.product;



import java.util.List;
import java.util.Map;

import com.glanway.jty.entity.product.Consult;
import com.glanway.jty.service.BaseService;
import com.glanway.jty.utils.Page;

public interface ConsultService extends BaseService<Consult, Long> {
	Consult getConsultById(Long id);

	List<Consult> getConsultByPage(Map<String, Object> map);

    List<Consult> findConsultByIdList(Map<String, Object> map);

    int findConsultByIdCount(Map<String, Object> map);

    List<Consult> getConsults(Page page, Long goodsId);
}
