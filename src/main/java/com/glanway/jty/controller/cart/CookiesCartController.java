/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved.
 * <p/>
 * ProjectName: hg
 * FileName: CookiesCartController.java
 * PackageName: com.glanway.jty.controller.cart
 * Date: 2016/5/912:53
 **/
package com.glanway.jty.controller.cart;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.Sort;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.cart.Cart;
import com.glanway.jty.entity.product.Goods;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.product.GoodsService;
import com.glanway.jty.utils.AESUtil;
import com.glanway.jty.utils.CookieUtil;
import com.glanway.jty.utils.JSONArrayUtil;
import com.glanway.jty.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>名称: cookie购物车</p>
 * <p>说明: cookie购物车</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/5/912:53
 * @version: 1.0
 */
@Controller
@RequestMapping("/cookieCart")
public class CookiesCartController extends BaseController {
    @Autowired
    private GoodsService goodsService;

    /**
     * <p>名称：进入我的购物车</p>
     * <p>描述：进入我的购物车</p>
     *
     * @param ids 选中的商品Id
     * @param
     * @return
     * @author：tianxuan
     */
    @RequestMapping
    public ModelAndView cart(@RequestParam(value = "id", required = false) List<Long> ids, HttpServletRequest request) {
        Map<String,Object> rtMap = getDataMap(request,null);
        rtMap.put("ids", ids);
        String path = "order/cart";
        if(this.isMobile){
            path = "mobile/" + path;
        }else {
            path = "pc/" + path;
        }
        return new ModelAndView(path, "rtMap", rtMap);
    }

    /**
     *右侧边栏 购物车
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping("/cartAsyn")
    @ResponseBody
    public Map cartAsyn (@RequestParam(value = "id", required = false) List<Long> ids, HttpServletRequest request) {
        Map<String,Object> rtMap = getDataMap(request,null);
        rtMap.put("ids", ids);
        return rtMap;
    }

    /**
     * <p>名称：添加到购物车(cookies)</p>
     * <p>描述：添加到购物车（cookies）</p>
     *
     * @param goodsIds goodsId
     * @param buyCount 购买数量
     * @param goodsFrom 购买来源
     * @param otherId 活动或者限时
     * @param buyCount 购买数量
     * @param session
     * @param ra
     * @return
     * @author：tianxuan
     */
    @RequestMapping("save")
    public String saveToCookies(@RequestParam(value = "goodsId", required = false) Long[] goodsIds, int buyCount,Integer[] goodsFrom,Long[] otherId, HttpServletResponse response, HttpServletRequest request, HttpSession session, RedirectAttributes ra) {
        if (null == goodsIds || buyCount < 1) {
            throw new CustomException("参数不合法");
        }
        Cookie cartCookie = CookieUtil.getCookie(request, Constants.CART_COOKIE_NAME);
        List<Cart> cartList = new ArrayList<>();
        if (null != cartCookie) {
            String cartListStr = cartCookie.getValue();
            cartListStr = AESUtil.decrypt(cartListStr, Constants.CART_AES_KEY);
            cartList = JSONArrayUtil.parse(cartListStr, Cart.class);
        }

        /*以商品Id为可以，将cart 装到map中*/
        Map<Long, List<Cart>> cartMap = new HashMap<>();
        if (null != cartList) {
            for (Cart cart : cartList) {
                Long goodsId = cart.getGoodsId();
                cart.setSelected(Boolean.FALSE);
                List<Cart> carts;
                if(cartMap.containsKey(goodsId)){
                    carts = cartMap.get(goodsId);
                }else {
                    carts = new ArrayList<>();
                }
                carts.add(cart);
                cartMap.put(goodsId, carts);
            }
        }

        /*将商品加入购物车*/
        for (int i=0; i < goodsIds.length; i++) {
            Long goodsId = goodsIds[i];
            List<Cart> carts = cartMap.get(goodsId);

            List<Goods> goodsList = goodsService.findMany(Filters.create().eq("id", goodsId),(Pageable) null);
            Goods goods = goodsList.get(0);
            Cart cart = new Cart();
            cart.setId(goodsId);  //购物车中的Id为 goodsID
            cart.setGoodsId(goodsId);
            cart.setGoodsName(goods.getTitle());
            cart.setGoodsImg(goods.getImage());
            cart.setSelected(Boolean.TRUE);
            if(null != goodsFrom && goodsFrom.length > i ){
                cart.setGoodsFrom(goodsFrom[i]);
                cart.setOtherId(otherId[i]);
            }
            Cart oldCart = null;
            if(null != carts){
                for(Cart ct :carts){
                    Integer gf = ct.getGoodsFrom();
                    Integer gf2 = cart.getGoodsFrom();
                    gf =  null == gf ? 0  : gf;
                    gf2 = null == gf2 ? 0 : gf2;
                    if(gf == gf2){
                        oldCart = ct;
                    }
                }
            }
            if(null != oldCart){
                oldCart.setId(oldCart.getId());
                oldCart.setBuyCount(buyCount + oldCart.getBuyCount());
            } else {
                cart.setBuyCount(buyCount);
                cartList.add(cart);
            }
        }
        /*将新的购物车 存进 cookies*/
        addCartToCookie(cartList, response);
        return "redirect:/cookieCart";
    }


    /**
     * <p>名称：添加到购物车(cookies)</p>
     * <p>描述：添加到购物车（cookies）</p>
     *
     * @param goodsIds goodsId
     * @param buyCount 购买数量
     * @param session
     * @param ra
     * @return
     * @author：tianxuan
     */
    @RequestMapping("saveAsync")
    @ResponseBody
    public Message saveAsync(@RequestParam(value = "goodsId", required = false) Long[] goodsIds,Integer[] goodsFrom,Long[] otherId, int buyCount, HttpServletResponse response, HttpServletRequest request, HttpSession session, RedirectAttributes ra) {
        if (null == goodsIds || buyCount < 1) {
            throw new CustomException("参数不合法");
        }
        Cookie cartCookie = CookieUtil.getCookie(request, Constants.CART_COOKIE_NAME);
        List<Cart> cartList = new ArrayList<>();
        if (null != cartCookie) {
            String cartListStr = cartCookie.getValue();
            cartListStr = AESUtil.decrypt(cartListStr, Constants.CART_AES_KEY);
            cartList = JSONArrayUtil.parse(cartListStr, Cart.class);
        }

        /*以商品Id为可以，将cart 装到map中*/
        Map<Long, List<Cart>> cartMap = new HashMap<>();
        if (null != cartList) {
            for (Cart cart : cartList) {
                Long goodsId = cart.getGoodsId();
                cart.setSelected(Boolean.FALSE);
                List<Cart> carts;
                if(cartMap.containsKey(goodsId)){
                    carts = cartMap.get(goodsId);
                }else {
                    carts = new ArrayList<>();
                }
                carts.add(cart);
                cartMap.put(goodsId, carts);
            }
        }

        /*将商品加入购物车*/
        for (int i=0;i<goodsIds.length;i++) {
            Long goodsId = goodsIds[i];
            List<Cart> carts = cartMap.get(goodsId);
            List<Goods> goodsList = goodsService.findMany(Filters.create().eq("id", goodsId),(Pageable) null);
            Goods goods = goodsList.get(0);
            Cart cart = new Cart();
            cart.setId(goodsId);  //购物车中的Id为 goodsID
            cart.setGoodsId(goodsId);
            cart.setGoodsName(goods.getTitle());
            cart.setGoodsImg(goods.getImage());
            cart.setSelected(Boolean.TRUE);
            /*设置商品的折扣来源*/
            if(null != goodsFrom && goodsFrom.length > i ){
                cart.setGoodsFrom(goodsFrom[i]);
                cart.setOtherId(otherId[i]);
            }
            Cart oldCart = null;
            if(null != carts){
                for(Cart ct :carts){
                    Integer gf = ct.getGoodsFrom();
                    Integer gf2 = cart.getGoodsFrom();
                    gf =  null == gf ? 0  : gf;
                    gf2 = null == gf2 ? 0 : gf2;
                    if(gf == gf2){
                        oldCart = ct;
                    }
                }
            }
            if(null != oldCart){
                oldCart.setId(oldCart.getId());
                oldCart.setBuyCount(buyCount + oldCart.getBuyCount());
            } else {
                cart.setBuyCount(buyCount);
                cartList.add(cart);
            }
        }
        /*将新的购物车 存进 cookies*/
        addCartToCookie(cartList, response);
        return Message.success();
    }


    /**
     * <p>名称：删除</p>
     * <p>描述：删除cookies 中购物车商品</p>
     *
     * @param ids
     * @param
     * @return
     * @author：tianxuan
     */
    @RequestMapping("delete")
    @ResponseBody
    public Message deleteByCookie(@RequestParam(value = "id", required = false) Long[] ids, HttpServletRequest request, HttpServletResponse response) {
        String cartListStr = CookieUtil.getCookieValue(request, Constants.CART_COOKIE_NAME);
        cartListStr = AESUtil.decrypt(cartListStr, Constants.CART_AES_KEY);
        List<Cart> cartList = JSONArrayUtil.parse(cartListStr, Cart.class);

        /*从cookie中 删除*/
        Arrays.sort(ids);
        Iterator<Cart> it = cartList.iterator();
        while (it.hasNext()) {
            Cart cart = it.next();
            if (Arrays.binarySearch(ids, cart.getId()) > -1) {
                it.remove();
            }
        }
        /*将新的购物车 存进 cookies*/
        addCartToCookie(cartList, response);
        final Map<String, Object> dataMap = getDataMap(request, cartList);
        return Message.success(dataMap);
    }


    /**
     * <p>名称：选中商品操作</p>
     * <p>描述：选中商品后的操作</p>
     *
     * @param ids
     * @param
     * @return
     * @author：tianxuan
     */
    @RequestMapping("selectGoods")
    @ResponseBody
    public Message selected(@RequestParam(value = "id", required = false) Long[] ids, HttpServletRequest request, HttpServletResponse response) {
        List<Cart> cartList = findCartList(request);
        for (Cart cart : cartList) {
            if (null != ids && ids.length > 0) {
                Arrays.sort(ids);
                if ((Arrays.binarySearch(ids, cart.getId()) > -1)) {
                    cart.setSelected(true);
                }else{
                    cart.setSelected(false);
                }
            } else {
                cart.setSelected(false);
            }
        }
        addCartToCookie(cartList, response);
        final Map<String, Object> dataMap = getDataMap(request, cartList);
        return Message.success(dataMap);
    }


    /**
     * <p>名称：修改商品数量</p>
     * <p>描述：修改商品数量</p>
     *
     * @param id
     * @param type     1->加 2->减 3->直接修改数值
     * @param buyCount
     * @param
     * @return
     * @author：tianxuan
     */
    @RequestMapping("changeBuyCount")
    @ResponseBody
    public Message changBuyCount(Long id, Integer type, Integer buyCount, HttpServletRequest request, HttpServletResponse response) {
        List<Cart> cartList = findCartList(request);
        Cart cart = null;
        for (Cart cart1 : cartList) {
            if (id.equals(cart1.getId())) {
                cart = cart1;
                break;
            }
        }
        // 查询商品的的库存
        Integer inventoryNums = goodsService.getStock(cart.getGoodsId());
        Integer _buyCount = cart.getBuyCount();
        if (1 == type) {
            _buyCount += 1;
            if (_buyCount > inventoryNums) {
//                throw new CustomException("商品数量不能大于" + inventoryNums);
                throw new CustomException("商品数量超限");
            }
        } else if (2 == type) {
            if (_buyCount <= 1) {
//                throw new CustomException("商品数量不能小于1");
                throw new CustomException("商品数量超限");
            }
            _buyCount -= 1;
        } else if (3 == type) {
            if (buyCount > inventoryNums || buyCount < 1) {
//                throw new CustomException("商品数量必须大于 0 小于 " + inventoryNums);
                throw new CustomException("商品数量超限");
            }
            _buyCount = buyCount;
        } else {
            throw new CustomException("参数错误");
        }

        cart.setBuyCount(_buyCount);
        addCartToCookie(cartList, response);
        final Map<String, Object> dataMap = getDataMap(request,cartList);
        return Message.success(dataMap);
    }

    /**
     * <p>名称：购物车商品数量</p>
     * <p>描述：查询购物车中的商品数量</p>
     * @author：tianxuan
     * @param request
     * @return
     */
    @RequestMapping("findCount")
    @ResponseBody
    public Message findCount(HttpServletRequest request){
        List<Cart> cartList = findCartList(request);
        Integer totalCount = 0;
        if(null != totalCount){
            for(Cart cart : cartList){
                totalCount += cart.getBuyCount();
            }
        }
        return Message.success(totalCount);
    }

    /**
     * <p>名称：将购物车存进cookie</p>
     * <p>描述：将购物车存进cookie</p>
     *
     * @param response
     * @author：tianxuan
     */
    private static void addCartToCookie(List<Cart> cartList, HttpServletResponse response) {
        String cartJson = JSONArrayUtil.stringify(cartList);
        cartJson = AESUtil.encrypt(cartJson, Constants.CART_AES_KEY);
        CookieUtil.setCookie(response, Constants.CART_COOKIE_NAME, cartJson);
    }

    /**
     * <p>名称：查询购物车商品</p>
     * <p>描述：从cookie 查询购物车商品</p>
     *
     * @param request
     * @return
     * @author：tianxuan
     */
    public static List<Cart> findCartList(HttpServletRequest request) {
        List<Cart> cartList = new ArrayList<>();
        String cartListStr = CookieUtil.getCookieValue(request, Constants.CART_COOKIE_NAME);
        if (StringUtil.notEmpty(cartListStr)) {
            cartListStr = AESUtil.decrypt(cartListStr, Constants.CART_AES_KEY);
            cartList = JSONArrayUtil.parse(cartListStr, Cart.class);
        }
        return cartList;
    }

    /**
     * <p>名称：清空购物车</p>
     * <p>描述：从cookies中删除购物车信息</p>
     *
     * @param request
     * @param response
     * @author：tianxuan
     */
    public static void deleteCart(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, Constants.CART_COOKIE_NAME);
    }

    /**
     * 清空购物车
     * @param session
     * @return
     */
    @RequestMapping("clear")
    @ResponseBody
    public Message clear(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        this.deleteCart(request,response);
        return Message.success(getDataMap(request,null));
    }

    /**
     * <p>名称：查询页面需要的数据</p>
     * <p>描述：从购物车中查询出页面需要展示的数据</p>
     * @author：tianxuan
     * @param request
     * @return
     */
    private Map<String,Object> getDataMap(HttpServletRequest request,List<Cart> cartList){
        Map<String, Object> rtMap = new HashMap<>();
        //购物车list
        if(null == cartList){
            cartList = findCartList(request);
        }
        //购物车中的 商品Id 集合
        List<Long> goodsIdList = new ArrayList<>();
        //转为 以 goodsId 为 kyemap
        Map<Long, List<Cart>> cartMap = new HashMap<>();
        //转为以Id 为key 的 map
        Map<Long, Cart> cartMapById = new HashMap<>();
        /*为map赋值*/
        for (Cart cart : cartList) {
            Long goodsId = cart.getGoodsId();
            goodsIdList.add(goodsId);
            List<Cart> carts;
            if(cartMap.containsKey(goodsId)){
                carts = cartMap.get(goodsId);
            }else {
                carts = new ArrayList<>();
            }
            carts.add(cart);
            cartMap.put(goodsId, carts);
            cartMapById.put(cart.getId(), cart);
        }

        //购物车中的所有商品
        List<Goods> goodsList = goodsService.findMany(Filters.create().eq("id", goodsIdList.toArray()), Sort.create());

        /*获取商品的最新价格 及 选中商品的总数量 和 总价格*/
        Integer totalBuyCount = 0;  //选中的商品总数量
        BigDecimal totalPrice = new BigDecimal(0); //选中商品总价格
        for (Goods goods : goodsList) {
            if(!goods.getProduct().getIsPutaway()){
                continue;
            }
            Long goodsId = goods.getId();
            if (cartMap.containsKey(goodsId)) {
                // 查询商品的的库存

                List<Cart> carts = cartMap.get(goodsId);
                if(carts.size() < 2 ){
                    Cart cart = carts.get(0);
                Integer inventoryNums = goodsService.getStock(cart.getGoodsId());
                    cart.setInventory(inventoryNums);
                    // 计算价格
                    goodsService.calcMemberPriceAtEveryOne(goods,cart.getGoodsFrom(),cart.getOtherId(),request.getSession());
                    cart.setGoodsName(goods.getTitle());
                    cart.setGoodsImg(goods.getImage());
                    // 获取价格
                    BigDecimal price = goods.getPromotePrice();
                    BigDecimal singleTotalPrice = price.multiply(new BigDecimal(cart.getBuyCount())).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                    cart.setPrice(price); //单价
                    cart.setTotalPrice(singleTotalPrice);//总价
                    cart.setIsLive(true);

                /*计算选中商品总价 和 总数量*/
                    if (cart.getSelected()) {
                        totalBuyCount += cart.getBuyCount();
                        totalPrice = totalPrice.add(cart.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                    }
                }else if(carts.size() > 1){
                    for (int i = 0; i <carts.size() ; i++) {
                        Cart cart = carts.get(i);
                        Integer inventoryNums = goodsService.getStock(cart.getGoodsId());
                        cart.setInventory(inventoryNums);
                        // 计算价格
                        goodsService.calcMemberPriceAtEveryOne(goods,cart.getGoodsFrom(),cart.getOtherId(),request.getSession());
                        cart.setGoodsName(goods.getTitle());
                        cart.setGoodsImg(goods.getImage());
                        // 获取价格
                        BigDecimal price = goods.getPromotePrice();
                        BigDecimal singleTotalPrice = price.multiply(new BigDecimal(cart.getBuyCount())).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                        cart.setPrice(price); //单价
                        cart.setTotalPrice(singleTotalPrice);//总价
                        cart.setIsLive(true);

                /*计算选中商品总价 和 总数量*/
                        if (cart.getSelected()) {
                            totalBuyCount += cart.getBuyCount();
                            totalPrice = totalPrice.add(cart.getTotalPrice()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                        }
                    }
                }

            }
        }
        Collections.reverse(cartList);
        rtMap.put("cartList", cartList);
        rtMap.put("totalBuyCount", totalBuyCount);
        rtMap.put("totalPrice", totalPrice);
        return rtMap;
    }
}
