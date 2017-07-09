/**
 * Copyright (c) 2016, www.glanway.com Inc. All rights reserved. 
 *
 * ProjectName: aimon-web-eshop 
 * FileName: PaymentController.java 
 * PackageName: com.glanway.aimon.controller.orderCenter 
 * Date: 2016年5月25日下午7:11:22
 **/
package com.glanway.jty.controller.cart;

import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.BalancePaymentDetail;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.platform.RechargeRule;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.customer.BalancePaymentDetailService;
import com.glanway.jty.service.customer.MemberDividendService;
import com.glanway.jty.service.order.OrderService;
import com.glanway.jty.utils.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ponly.plugin.payment.Payment;
import org.ponly.plugin.payment.Payments;
import org.ponly.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**      
 * <p>名称: PaymentController</p>
 * <p>说明:  支付控制器</p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>  
 * 
 * @author：zhaochuang
 * @date：2016年5月25日下午7:11:31   
 * @version: 1.0 
 */
@Controller
@RequestMapping("payment")
public class PaymentController extends BaseController{

    private static  final Log log = LogFactory.getLog(PaymentController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private BalancePaymentDetailService balancePaymentDetailService;
    @Autowired
    MemberDividendService memberDividendService;
    @Autowired
    private CacheUtil cacheUtil;

    @RequestMapping
    public String mp(){
        return "redirect:/mp/MP_verify_GnLjyXnOvwOXb8xU.txt";
    }


    /**
     * <p>名称：pay</p> 
     * <p>描述： 支付参数确认，支付响应</p>
     * @author：zhaochuang
     * @param channel 支付方式
     * @param orderNo 订单唯一值
     * @param payType  交易支付 Or 充值支付   2：充值支付
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("pay")
    public String pay(String channel, String orderNo, Integer payType, HttpServletRequest request,
                      HttpServletResponse response, HttpSession session) throws IOException {
        String rtUrl = null;
        //余额支付
        if(channel.equals("balance")){
            return payByMyAccount(orderNo,member.getId());
        }
        // 获取支付网关
        Payment payment = getPayment(channel);
        if (null == payment) {
            // 如果网关不支持, 跳转到非法请求, 也可以返回 null, 跳转到 404
           throw new CustomException("不支持支付网关");
        }
        log.debug("网关：" + payment);
        Long memberId = ((Member)session.getAttribute(Constants.MEMBER)).getId();

    /* *******  开始提交支付  ******** */
        int fee;
        payType = (null == payType ? 1 : payType);
        if(payType == 2){
            fee = getRechargeMoney(memberId);
        }else {
            fee = getOrderActualAmounts(orderNo,memberId);
        }
        // 订单有效时长（默认为24h）
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, Constants.ORDER_EXPIRATION_ACCESSORY);
        Date expirationTime = calendar.getTime();// 过期时间, 一般是创建时间 + 24h
        String subject = "有机伯伯";
        String body = "";
        // String url = IPUtil.getDNS(request);
        // String returnUrl = url + "/payment/"+channel+"/return_url";
        // String notifyUrl =url + "/payment/"+channel+"/notify_url";
        String returnUrl = WebUtils.getUrlWithoutApplication(request, "payment/" + channel + "/return_url");
        String notifyUrl = WebUtils.getUrlWithoutApplication(request, "payment/" + channel + "/notify_url");

        payment.setNotifyUrl(notifyUrl);
        payment.setReturnUrl(returnUrl);
        log.debug("start pay transaction. returnUrl: " + returnUrl + "+" +
                ".");
        UserAgent ua = UserAgent.parse(request);
        if("wxpay".equals(channel) && !ua.isMobile() ){
            //微信支付pc端就返回二维码url
           String codeUrl =  payment.getQRCodeUrl(orderNo + "_" + memberId + "_" + payType, fee, expirationTime, subject, body,0);
            int fee2 = getOrderActualAmounts(orderNo,memberId);
            request.setAttribute("qrCode",codeUrl);
            request.setAttribute("fee",fee2/100.00);
            request.setAttribute("mergeCode",orderNo);
            request.setAttribute("memberId",memberId);
            rtUrl =  "pc/order/wxQRCode";
        }else{
            payment.postTransaction(request, response, orderNo + "_" + memberId + "_" + payType, fee, expirationTime, subject, body);
        }
        return rtUrl;
    }

    /**
     * 支付结果同步回调
     *
     * @param channel 支付通道
     */
    @RequestMapping("{channel}/return_url")
    public String return_url(@PathVariable("channel") String channel,
                            HttpServletRequest request,
                            HttpServletResponse response,HttpSession session) {
        Payment payment = getPayment(channel);
        if (null == payment) {
            // 支付方式不存在跳转到错误页面, 如果是同步回调应该不会出现
            throw new CustomException("支付方式不存在");
        }
        // 验证是否支付成功, 因为是同步， 因此这里可以不做业务处理
        cacheUtil.setCache("common.cache");
        String result = (String)cacheUtil.getCacheValue("MEMBER_PAY_" + member.getId());
        Double fee = (Double)cacheUtil.getCacheValue("MEMBER_PAY_FEE_" + member.getId());
        String uniqueCode = (String)cacheUtil.getCacheValue("MEMBER_PAY_ORDERNO_" + member.getId());
        Integer payType = (Integer)cacheUtil.getCacheValue("MEMBER_PAY_TYPE_" + member.getId());
        cacheUtil.removeCacheValue("MEMBER_PAY_" + member.getId());
        cacheUtil.removeCacheValue("MEMBER_PAY_FEE_" + member.getId());
        cacheUtil.removeCacheValue("MEMBER_PAY_ORDERNO_" + member.getId());
        cacheUtil.removeCacheValue("MEMBER_PAY_TYPE_" + member.getId());
        if("success".equals(result)){
            return "redirect:/order/paySuccess?payPrice=" + fee + "&orderGroupCode=" +uniqueCode;
       } else {
            String path =  "redirect:/order/payFail?payTotalPrice=" + fee + "&orderGroupCode=" +uniqueCode;
            if(null != payType && payType.equals("2")){
                path += "&paType=2&rechargePay=true";
            }
            return path;
        }
    }

    /**
     * 支付结果异步回调
     *
     * @param channel 支付通道
     */
    @RequestMapping("{channel}/notify_url")
    public void notify_url(@PathVariable("channel") final String channel,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           final HttpSession session)throws Exception{
        Payment payment = getPayment(channel);
       final String payMethod=channel;
        if (null == payment) {
            // 支付方式不存在跳转到错误页面, 如果是同步回调应该不会出现
            throw new CustomException("支付方式不存在");
        }else {
            payment.verifyTransaction(request, response, new Payment.TransactionCallback() {
                /**
                 * 支付成功
                 *
                 * @param tradeNo       订单编号
                 * @param fee           支付金额
                 * @param paidTime      支付时间
                 * @param transactionId 交易id
                 * @param params        原始参数
                 */
                @Override
                public void onFinished(String tradeNo, int fee, Date paidTime,
                                       String transactionId, Map<String, String> params) {
                /*-
                 *  执行相关支付成功逻辑  缺少银联支付后的 卡号
                 * 例如 没有支付成功的话，更新支付状态, 保存支付方式，支付时间，交易ID，库存等
                 * 注意： 异步回调可能会被调用多次，因此应该先判断支付状态
                 */
                    log.info("已经进入回调 " + TimeUtil.format(new Date()));
                    log.info("订单编号： " + tradeNo);
                    String[] codes = tradeNo.split("_");
                    Long memberId = Long.valueOf(codes[1]);
                    tradeNo = codes[0];
                    Integer payType = Integer.valueOf(codes[2]);
                   log.info("支付方法异步回调:memberId = "+memberId);
                   log.info("支付方法异步回调:tradeNo="+tradeNo + ",fee="+ fee );
                   log.info("原始参数");
                    for(String key : params.keySet()){
                        log.info(key + " = " + params.get(key));
                    }
                    String channelName = getPaymentName(channel);
                    if(payType ==2){
                        cacheUtil.setCache("common.cache");
                        RechargeRule rechargeRule = (RechargeRule)cacheUtil.getCacheValue("RECHARGE_" + memberId);
                        cacheUtil.removeCacheValue("RECHARGE_" + memberId);
                        balancePaymentDetailService.recharge(memberId,null, BalancePaymentDetail.TYPE_RECHARGE,new BigDecimal(rechargeRule.getMoney()),new BigDecimal(rechargeRule.getRtMoney()),channelName);
                        cacheUtil.setCache("common.cache");
                        cacheUtil.setCacheValue("MEMBER_PAY_" + memberId, "success");
                        cacheUtil.setCacheValue("MEMBER_PAY_FEE_" + memberId, fee/100.00);
                        cacheUtil.setCacheValue("MEMBER_PAY_ORDERNO_" + memberId, tradeNo);
                        cacheUtil.setCacheValue("MEMBER_PAY_TYPE_" + memberId, payType);
                    }else if(getOrderActualAmounts(tradeNo,memberId) > 0){
                        if(isGroup(tradeNo)){
                            orderService.payOrderByGroupCode(tradeNo,channelName);
                        }else{
                            orderService.payOrderById(Long.valueOf(tradeNo),channelName,fee/100.00);
                            cacheUtil.setCache("common.cache");
                            cacheUtil.setCacheValue("MEMBER_PAY_" + memberId, "success");
                            cacheUtil.setCacheValue("MEMBER_PAY_FEE_" + memberId, fee/100.00);
                            cacheUtil.setCacheValue("MEMBER_PAY_ORDERNO_" + memberId, tradeNo);
                        }
                        //  返利
                        memberDividendService.recharge(memberId,Long.valueOf(tradeNo),channelName);
                    }

                }

                @Override
                public void onError(String errCode, String errMsg, Exception ex,
                                    Map<String, String> callbackParams) {
                /*-
                 *  验证失败, 执行相关逻辑, 如果需要
                 */
                    log.info("异步回调--》支付失败");
                    log.info("errCode: " + errCode + "errMsg: " + errMsg );
                }
            });
        }
    }

    /**
     * 获取给定支付通道对应的支付实例
     *
     * @param channel 支付通道
     */
    protected Payment getPayment(String channel) {
        return Payments.getPayment(channel, PropertiesUtil.getConfig());
    }

    /**
     * <p>名称：getOrderActualAmounts</p> 
     * <p>描述： 获取订单价格总和</p>
     * @author：zhaochuang
     * @param uniqueCode
     * @return
     */
    public int getOrderActualAmounts(String uniqueCode,Long memberId){
        int payPrice = 0;
        BigDecimal price = getOrderActualAmounts2(uniqueCode,memberId);
        if(null != price){
           payPrice =  price.multiply(new BigDecimal(100)).intValue();
        }
        return payPrice;
    }

    public BigDecimal getOrderActualAmounts2(String uniqueCode,Long memberId){
        BigDecimal price;
        if(isGroup(uniqueCode)){
            price = orderService.countAmountPriceByPayment(memberId,uniqueCode,null);
        }else{
            price = orderService.countAmountPriceByPayment(memberId,null,Long.valueOf(uniqueCode));
        }
        return price;
    }


    /**
     * 根据订单号判断是否是一组订单
     * @param code
     * @return
     */
    public boolean isGroup(String code){
        boolean flag = false;
        if(null != code){
            flag = code.startsWith("GP");
        }
        return flag;
    }

    /**
     * 解析支付方式为汉字
     * @param payment
     * @return
     */
    public String getPaymentName(String payment){
        String pay = "";
        if(null != payment){
            if("alipay".equals(payment)){
                pay = "支付宝";
            } else if("wxpay".equals(payment)){
                pay = "微信";
            }  else if("union_pay".equals(payment)){
                pay = "银联";
            }
        }
        return  pay;
    }

    @RequestMapping("createImage")
    public void createImage(HttpServletRequest request, HttpServletResponse response,String qrcode)throws Exception {
        int width = 200;
        int height = 200;
        Hashtable hints= new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrcode, BarcodeFormat.QR_CODE, width, height,hints);
        BufferedImage bi =  MatrixToImageWriter.toBufferedImage(bitMatrix);
        if (null != bi) {
            ImageIO.write(bi, "JPG", response.getOutputStream());
        }
    }

    //是否扫码支付过了
    @ResponseBody
    @RequestMapping("OrderPaySuccess")
    public String OrderPaySuccess(String mergeCode,Long memberId){
        String rtStr = "success";
        if(getOrderActualAmounts(mergeCode,memberId) > 0){
            rtStr =  "error";
        }
        return rtStr;
    }

    /**
     * 账户余额支付
     * @param uniqueCode
     * @param memberId
     * @return
     */
    @Transactional
    private String payByMyAccount(String uniqueCode, Long memberId){
        BigDecimal fee = getOrderActualAmounts2(uniqueCode,memberId);
        boolean flag = balancePaymentDetailService.consume(memberId,fee);
        if(flag){
            if(getOrderActualAmounts(uniqueCode,memberId) > 0){
                if(isGroup(uniqueCode)){
                    orderService.payOrderByGroupCode(uniqueCode,"余额");
                }else{
                    orderService.payOrderById(Long.valueOf(uniqueCode),"余额",fee.doubleValue());
                }
            }
            try{
                //  返利
                memberDividendService.recharge(memberId,Long.valueOf(uniqueCode),"余额");
            }catch (Exception e){
                System.out.print("返利失败");
            }
            return "redirect:/order/paySuccess?payPrice=" + fee+ "&orderGroupCode=" +uniqueCode;
        }else {
            return "redirect:/order/payFail?payTotalPrice=" + fee + "&orderGroupCode=" +uniqueCode;
        }
    }

    /**
     * 获取充值的金额
     * @param memberId
     * @return
     */
    private int getRechargeMoney(Long memberId){
        cacheUtil.setCache("common.cache");
        Object obj = cacheUtil.getCacheValue("RECHARGE_" + memberId);
        if(null != obj && obj instanceof RechargeRule){
            RechargeRule rechargeRule = (RechargeRule)obj;
//            int fee = rechargeRule.getMoney() + rechargeRule.getRtMoney();
            int fee = rechargeRule.getMoney();
//            return fee/100;
            return fee*100;
        } else {
            throw new CustomException("无法获取充值对象");
        }
    }
}
