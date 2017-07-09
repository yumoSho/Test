/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: OrderTempController.java
 * PackageName: com.glanway.jty.controller.cart
 * Date: 2016/5/514:47
 **/
package com.glanway.jty.controller.cart;

import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.entity.personalcenter.DeliveryAddress;
import com.glanway.jty.service.cart.OrderTempService;
import com.glanway.jty.service.logistics.HatProvinceService;
import com.glanway.jty.service.logistics.SupplierAreaService;
import com.glanway.jty.service.personalcenter.DeliveryAddressService;
import com.glanway.jty.vo.OrderTempVo;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>名称: 提交订单临时Controller</p>
 * <p>说明: 提交订单临时Controller</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 *
 * @author：tianxuan
 * @date：2016/5/514:47
 * @version: 1.0
 */
@Controller
@RequestMapping("/orderTemp")
public class OrderTempController extends BaseController {
    @Autowired
    private OrderTempService orderTempService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private SupplierAreaService supplierAreaService;
    @Autowired
    private HatProvinceService hatProvinceService;

    /**
     * <p>名称：</p>
     * <p>描述：更新购买数量</p>
     * @author：tianxuan
     * @param id
     * @param addressId 收货地址Id
     * @param type
     * @param buyCount
     * @return
     */
   /* @RequestMapping("changeBuyCount")
    @ResponseBody
    public Message changBuyCount(Long id, Long addressId, Integer type, Integer buyCount, HttpSession session){
        Long memberId = ((Member)session.getAttribute(Constants.MEMBER)).getId();
        orderTempService.changeBuyCount(id,type,buyCount);
        OrderTempVo otv = orderTempService.findAllByMemberId(memberId,addressId);
        return Message.success(otv);
    }*/

    /**
     * <p>名称：修改收货地址</p>
     * <p>描述：地址改变事件</p>
     * @author：tianxuan
     * @param addressId
     * @param session
     * @return
     */
    @RequestMapping("changAddress")
    @ResponseBody
    public Message changAddress(Long addressId, HttpSession session){
        Long memberId = ((Member)session.getAttribute(Constants.MEMBER)).getId();
        DeliveryAddress deliveryAddress;
        if(null != addressId){
            deliveryAddress = deliveryAddressService.find(addressId);
        }else{
            deliveryAddress = deliveryAddressService.findOne(ImmutableMap.<String, Object>of("memberId",memberId));
        }

        /*运费*/
        BigDecimal totalFreight = new BigDecimal(-1);
        if(null != deliveryAddress){
            BigDecimal freight = supplierAreaService.getPriceBySupplierIdAndCity(deliveryAddress.getCityCode()) ;
            totalFreight = (null != freight ? freight : totalFreight);
        }
        return Message.success(totalFreight);
    }

    /**
     * <p>名称：删除购买商品</p>
     * <p>描述：删除购买商品</p>
     * @author：tianxuan
     * @param id  id
     * @param addressId  收货地址id
     * @param session
     * @return  Message
     */
   /* @RequestMapping("delete")
    @ResponseBody
    public Message delete(Long id, Long addressId, HttpSession session){
        Long memberId = ((Member)session.getAttribute(Constants.MEMBER)).getId();
        orderTempService.delete(id);
        OrderTempVo otv = orderTempService.findAllByMemberId(memberId,addressId);
        return Message.success(otv);
    }*/


    /**
     * 手机端选择收货地址
     * @param map
     * @return
     */
    @RequestMapping("selectAddr")
    public String selectAddr(Long addressId,ModelMap map) {
        Long memberId = member.getId();
        List<DeliveryAddress> addrList = deliveryAddressService.findAllByMemberId(memberId);
        map.put("addrList",addrList);
        map.put("addressId",addressId);
        return "mobile/order/selectAddr";
    }

    /**
     * 手机端添加新地址
     * @param map
     * @return
     */
    @RequestMapping("addrAdd")
    public String addrAdd(ModelMap map) {
        //所有省
        List<HatProvince> hatProvinceList = hatProvinceService.listAllProvince();
        map.put("provinces",hatProvinceList);
        return "mobile/order/addrAdd";
    }
}
