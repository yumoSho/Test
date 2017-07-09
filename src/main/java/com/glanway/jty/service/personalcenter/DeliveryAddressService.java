/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: DeliveryAddressController.java
 * PackageName: com.glanway.sh.controller.cart
 * Date: 2016/5/313:36
 **/
package com.glanway.jty.service.personalcenter;




import java.util.List;

import com.glanway.jty.entity.personalcenter.DeliveryAddress;
import com.glanway.jty.service.BaseService;

/**
 * <p>名称: 收货地址service</p>
 * <p>说明: 收货地址service</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/5/313:36
 * @version: 1.0
 */
public interface DeliveryAddressService extends BaseService<DeliveryAddress,Long> {
    List<DeliveryAddress> findAllByMemberId(Long memberId);
}
