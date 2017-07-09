/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: CartController.java
 * PackageName: com.glanway.jty.controller.cart
 * Date: 2016/5/714:59
 **/
package com.glanway.jty.controller.cart;

import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.service.cart.CartService;
import com.glanway.jty.service.product.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>名称: 购物车Controller</p>
 * <p>说明: 购物车Controller</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/5/714:59
 * @version: 1.0
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController{

    @Autowired
    private CartService cartService;
    @Autowired
    private GoodsService goodsService;

    /**
     * <p>名称：进入我的购物车</p>
     * <p>描述：进入我的购物车</p>
     * @param ids  选中的商品Id
     * @param session
     * @return
     * @author：tianxuan
     */
    @RequestMapping
    public ModelAndView cart(@RequestParam(value = "id",required = false) List<Long> ids, HttpSession session) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        Map<String, Object> rtMap = cartService.findAllByMemberId(memberId,session);
        rtMap.put("ids",ids);
        String path = "order/cart";
        if(this.isMobile){
            path = "mobile/" + path;
        } else {
            path = "pc/" + path;
        }
        return new ModelAndView(path,"rtMap",rtMap);
    }

    /**
     *右侧边栏 购物车
     * @param ids
     * @param
     * @return
     */
    @RequestMapping("/cartAsyn")
    @ResponseBody
    public Map cartAsyn(@RequestParam(value = "id",required = false) List<Long> ids, HttpSession session) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        Map<String, Object> rtMap = cartService.findAllByMemberId(memberId,session);
        rtMap.put("ids",ids);
        return rtMap;
    }

    /**
     * <p>名称：向购物车添加商品</p>
     * <p>描述：向购物车添加商品</p>
     *
     * @param goodsIds  商品id
     * @param buyCount 数量
     * @param session  session
     * @return
     * @author：tianxuan
     */
    @RequestMapping("save")
    public String save(@RequestParam(value = "goodsId",required = false) Long[] goodsIds,Integer[] goodsFrom,Long[] otherId, int buyCount, HttpSession session, RedirectAttributes ra) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        cartService.saveCartBatch(goodsIds, buyCount,goodsFrom,otherId, memberId);
        return "redirect:/cart";
    }

    /**
     * <p>名称：异步向购物车添加商品</p>
     * <p>描述：异步向购物车添加商品</p>
     *
     * @param goodsIds  商品id
     * @param buyCount 数量
     * @param session  session
     * @return
     * @author：tianxuan
     */
    @RequestMapping("saveAsync")
    @ResponseBody
    public Message saveAsync(@RequestParam(value = "goodsId",required = false) Long[] goodsIds,Integer[] goodsFrom,Long[] otherId, int buyCount, HttpSession session, RedirectAttributes ra) {
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        cartService.saveCartBatch(goodsIds, buyCount, goodsFrom, otherId,memberId);
        return Message.success();
    }

    /**
     * <p>名称：选中商品操作</p>
     * <p>描述：选中商品后的操作</p>
     * @author：tianxuan
     * @param ids
     * @param session
     * @return
     */
    @RequestMapping("selectGoods")
    @ResponseBody
    public Message selected(@RequestParam(value = "id",required = false) Long[] ids, HttpSession session){
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        cartService.selectGoods(ids,memberId);
        Map<String,Object> dataMap = cartService.findAllByMemberId(memberId,session);
        return Message.success(dataMap);
    }

    /**
     * <p>名称：修改商品数量</p>
     * <p>描述：修改商品数量</p>
     * @author：tianxuan
     * @param id
     * @param type  1->加 2->减 3->直接修改数值
     * @param buyCount
     * @param session
     * @return
     */
    @RequestMapping("changeBuyCount")
    @ResponseBody
    public Message changBuyCount(Long id, Integer type, Integer buyCount, HttpSession session){
        Long memberId = ((Member)session.getAttribute(Constants.MEMBER)).getId();
        cartService.changeBuyCount(id,type,buyCount);
        Map<String, Object> dataMap = cartService.findAllByMemberId(memberId,session);
        return Message.success(dataMap);
    }

    /**
     * <p>名称：删除商品</p>
     * <p>描述：删除商品</p>
     * @author：tianxuan
     * @param ids
     * @param session
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Message delete(@RequestParam(value = "id",required = false) Long[] ids, HttpSession session){
        Long memberId = ((Member)session.getAttribute(Constants.MEMBER)).getId();
        cartService.delete(ids);
        Map<String,Object> dataMap = cartService.findAllByMemberId(memberId,session);
        return Message.success(dataMap);
    }

    /**
     * 清空购物车
     * @param session
     * @return
     */
    @RequestMapping("clear")
    @ResponseBody
    public Message clear(HttpSession session){
        Long memberId = ((Member)session.getAttribute(Constants.MEMBER)).getId();
        cartService.clear(memberId);
        Map<String,Object> dataMap = cartService.findAllByMemberId(memberId,session);
        return Message.success(dataMap);
    }


    /**
     * <p>名称：移入收藏夹</p>
     * <p>描述：移入收藏夹</p>
     * @author：tianxuan
     * @param ids  id集合
     * @param session
     * @return
     */
    @RequestMapping("moveToFavorite")
    @ResponseBody
    public Message moveToFavorite(@RequestParam(value = "id",required = false) Long[] ids,Integer goodsFrom,Long otherId, HttpSession session){
        Long memberId = ((Member)session.getAttribute(Constants.MEMBER)).getId();
        cartService.moveToFavorite(ids,memberId);
        Map<String,Object> dataMap = cartService.findAllByMemberId(memberId,session);
        return Message.success(dataMap);
    }


    /**
     * <p>名称：购物车商品数量</p>
     * <p>描述：查询购物车中的商品数量</p>
     * @author：tianxuan
     * @param session
     * @return
     */
    @RequestMapping("findCount")
    @ResponseBody
    public Message findCount(HttpSession session){
        Long memberId = ((Member) session.getAttribute(Constants.MEMBER)).getId();
        Map<String,Object> param = new HashMap<>();
        param.put("memberId",memberId);
        Integer totalCount = cartService.totalBuyCount(param);
        return Message.success(totalCount);
    }

}
