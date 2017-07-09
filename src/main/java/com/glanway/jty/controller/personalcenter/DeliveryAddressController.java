/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: DeliveryAddressController.java
 * PackageName: com.glanway.sh.controller.cart
 * Date: 2016/5/313:36
 **/
package com.glanway.jty.controller.personalcenter;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.gone.spring.bind.domain.support.PageRequest;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.personalcenter.DeliveryAddress;
import com.glanway.jty.service.personalcenter.DeliveryAddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>名称: 收货地址Controller</p>
 * <p>说明: </p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/5/313:36
 * @version: 1.0
 */
@Controller
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController extends BaseController {
    @Autowired
    private DeliveryAddressService deliveryAddressService;

    /**
     * <p>名称：保存</p>
     * <p>描述：保存收货地址</p>
     *
     * @param da 收货地址实体
     * @return Message
     * @author：tianxuan
     */
    @RequestMapping("save")
    @ResponseBody
    public Message save(DeliveryAddress da, HttpSession session) {
        Member member = (Member) session.getAttribute(Constants.MEMBER);
        da.setMemberId(member.getId());
        if (null == da.getId()) {
            deliveryAddressService.save(da);
        } else {
            da.setDeleted(false);
            deliveryAddressService.update(da);
        }

        List<DeliveryAddress> addressList = deliveryAddressService.findAllByMemberId(member.getId());
        return Message.success(addressList);
    }


    /**
     * <p>名称：保存</p>
     * <p>描述：保存收货地址</p>
     *
     * @param da 收货地址实体
     * @return Message
     * @author：tianxuan
     */
    @RequestMapping("saveByMobile")
    @ResponseBody
    public Message saveByMobile(DeliveryAddress da, HttpSession session){
        Member member = (Member) session.getAttribute(Constants.MEMBER);
        da.setMemberId(member.getId());
        if (null == da.getId()) {
            deliveryAddressService.save(da);
        } else {
            da.setDeleted(false);
            deliveryAddressService.update(da);
        }
        return Message.success(da.getId());
    }

    /**
     * 手机端更多地址
     * @param session
     * @param modelMap
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("moreAddress")
    public String moreAddress(HttpSession session ,ModelMap modelMap,
                          @RequestParam(required=false,	defaultValue="1")Integer page,
                          @RequestParam(required=false, defaultValue="5")Integer pageSize,Long addressId){
        Member member = (Member) session.getAttribute(Constants.MEMBER);
        Filters filters = Filters.create().eq("memberId", member.getId());
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        if(null != addressId && addressId >0){
            pageRequest.setSort(new Sort().desc("id="+addressId));
        }else{
            pageRequest.setSort(new Sort().desc("id"));
        }
        Page<DeliveryAddress> pagable = deliveryAddressService.findPage(filters, pageRequest);

        modelMap.put("addressList",pagable.getData());
        modelMap.put("addressId",addressId);
        return "mobile/order/moreAddress";
    }

    @RequestMapping("moreAddressList")
    @ResponseBody
    public Message moreAddressList(HttpSession session , ModelMap modelMap,
                                   @RequestParam(required=false,	defaultValue="1")Integer page,
                                   @RequestParam(required=false, defaultValue="10")Integer pageSize, Long addressId){
        Member member = (Member) session.getAttribute(Constants.MEMBER);
        Filters filters = Filters.create().eq("memberId", member.getId());
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setPageSize(pageSize);
        if(null != addressId && addressId >0){
            pageRequest.setSort(new Sort().desc("id="+addressId));
        }else{
            pageRequest.setSort(new Sort().desc("id"));
        }
        Page<DeliveryAddress> pagable = deliveryAddressService.findPage(filters, pageRequest);

        return Message.success(pagable);
    }

}
