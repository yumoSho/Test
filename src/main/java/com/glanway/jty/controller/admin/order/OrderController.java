package com.glanway.jty.controller.admin.order;

import com.glanway.gone.spring.bind.PageableDefault;
import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.entity.order.Order;
import com.glanway.jty.entity.order.OrderDetail;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.service.order.OrderService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller("adminOrderController")
@RequestMapping("/admin/adminOrder")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    /*@Autowired
    private LogisticsInfoService logisticsInfoService;*/

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 日期转换器
     */
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), false));
    }

    /**
     * 葒果后台
     * 进入订单列表
     *
     * @return
     */
    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        //查询订单中，待发货订单（状态：3）个数，待收货（状态：4）订单个数
        Map<String, Object> map = null;
        map = orderService.countStatus(Constants.ORDER_STATUS_DFH, Constants.ORDER_STATUS_DSH,null, null);
        modelMap.put("DFH", map.get("COUNT1"));
        modelMap.put("DSH", map.get("COUNT2"));
        return "admin/adminOrder/index";
    }

    /**
     * 列表数据查询
     *
     * @param beginDate 开始时间（基于创建时间）
     * @param endDate   结束时间（基于创建时间）
     * @param filters
     * @param pageable
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Page<Order> list(
            @RequestParam(value = "beginDate", required = false) String beginDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @Qualifier("M.") Filters memberFilters,
            @Qualifier("O.") Filters filters,
             Filters commFilters,
         /*   @PageableDefault(sort = {"createdDate-desc"})*/ Pageable pageable) {
        Page<Order> orderlist = orderService.findPage(beginDate, endDate, memberFilters, filters, pageable,commFilters);
        return orderlist;
    }

    /**
     * 根据订单ID查询订单相应信息(订单信息,订单详情信息,会员信息,发票信息,物流信息)
     *
     * @param id    订单id
     * @param model
     * @returno
     * @change Log:
     */
    @RequestMapping("edit/{id}")
    public String normal(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes,
            Map<String, Object> model) {
        //根据订单ID查询订单信息
        Order order = orderService.getOrderById(id);
        if (null == order) {
            redirectAttributes.addFlashAttribute("message", "该订单不存在");
            return "redirect:/admin/adminOrder/index";
        }
      /*  //快递100获取物流信息
        String channle = DomainConstants.LOGISTICS_CHANNEL_CODE_KUAIDI100;
        String shunfeng = KuaiDi100Constants.COMPANY_CODE_SHUNFENG;
        List<RouteVO> routeVOList = null;
        if (null != order && null != order.getDeliverCode() && !"".equals(order.getDeliverCode())) {
            String trackingNumber = order.getDeliverCode();
            routeVOList = logisticsInfoService.getRouteVoList(channle, trackingNumber, shunfeng);
        }
        model.put("routeVOList", routeVOList);*/
        List<Dictionary> deliverCompanyList = dictionaryService.findBySuperDicCode(Constants.DT_WLGS);
        model.put("order", order);
        model.put("deliverCompanyList",deliverCompanyList);
        return "admin/adminOrder/search";
    }

    /**
     * 根据订单ID更新订单状态
     *
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping("updateOrderStatusById")
    public Message updateOrderStatusById(
            @RequestParam("id") Long id,
            @RequestParam("status") Integer status,
            RedirectAttributes redirectAttributes) {
        if (!StringUtils.isEmpty(id) && !StringUtils.isEmpty(status)) {
            orderService.updateOrderStatusById(id, status);
            return Message.success();
        }

        return Message.fail("操作失败！");
    }

    public Message deliverOrderById(
            Long id, RedirectAttributes redirectAttributes) {
        if (!StringUtils.isEmpty(id)) {
            orderService.deliverOrderById(id);
            return Message.success();
        }

        return Message.fail("操作失败！");
    }

    /**
     * 根据订单ID更新订单状态为待收货
     * <p>名称：订单发货</p>
     * <p>描述：根据订单ID更新订单状态为待收货并更新订单发货时间</p>
     *
     * @param id
     * @return
     * @author：kiah
     */
    @ResponseBody
    @RequestMapping("deliverOrder")
    public Message deliverOrder(
            @RequestParam("id") Long id,
            RedirectAttributes redirectAttributes) {
        return deliverOrderById(id, redirectAttributes);
    }

    /**
     * 根据订单ID更新订单状态为交易取消
     *
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping("updateOrderStatusCancleById")
    public Message updateOrderStatusCancleById(
            @RequestParam("id") Long id,
            @RequestParam("status") Integer status,
            RedirectAttributes redirectAttributes) {
        return updateOrderStatusById(id, status, redirectAttributes);
    }

    /**
     * 根据订单ID更新快递单号
     *
     * @param deliverCode
     * @param orderId
     * @return Message
     */
    @ResponseBody
    @RequestMapping("addDeliverCode")
    public Message addDeliverCode(String deliverCode, Long orderId, String deliverCompanyCode) {
        if (!StringUtils.isEmpty(orderId) && !StringUtils.isEmpty(deliverCode) && !StringUtils.isEmpty(deliverCompanyCode)) {
            orderService.updateDeliverCodeByOrderId(orderId, deliverCode, deliverCompanyCode);
            return Message.success();
        }

        return Message.fail("操作失败！");
    }

    /**
     * 导出订单列表至Excel
     *
     * @param beginDate          开始时间
     * @param endDate            结束时间
     * @param filters            过滤条件
     * @param pageable           页面参数
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequestMapping("export")
    public View export(
            @RequestParam(value = "beginDate", required = false) String beginDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @Qualifier("M.") Filters memberFilters,
            @Qualifier("O.") Filters filters,
            @PageableDefault(sort = {"createdDate-desc"},page = 1,size = 2147483647) Pageable pageable,
            RedirectAttributes redirectAttributes) {
        final List<Order> orderList = orderService.ListForExport(beginDate, endDate, memberFilters, filters, pageable);
        return new AbstractExcelView() {
            @Override
            protected void buildExcelDocument(Map<String, Object> model,
                                              HSSFWorkbook workbook, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
                HSSFSheet sheet = workbook.createSheet("SHEET1" +
                        "");
                HSSFCellStyle style = workbook.createCellStyle();
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                sheet.setDefaultRowHeightInPoints(20);
                sheet.setDefaultColumnWidth(15);
                int headIndex = 0;
                HSSFCell cell = getCell(sheet, 0, headIndex);
                cell.setCellValue("序号");
                cell.setCellStyle(style);

                 cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("订单编号");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("订单状态");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("订单金额");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("订单来源");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("会员名");
                cell.setCellStyle(style);


                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("手机");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("下单时间");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("收件人");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("收货地址");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("联系电话");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("配送时间");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("收货时间");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue(" 商品名称");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("购买数量");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, ++headIndex);
                cell.setCellValue("单价");
                cell.setCellStyle(style);

                int nextRowFlag = 1;
                int sheetNum = 1;
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
                Order current;
                for (int i = 0; i < orderList.size(); i++) {
                    int colIndex = 0;
                    current = orderList.get(i);
                    int endRow = nextRowFlag + current.getOrderDetails().size() -1;

                    //序号
                    getCell(sheet, nextRowFlag, colIndex).setCellValue(i+1);
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }
                    // 订单号
                    getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getCode());
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }

                    //订单状态
                    String status = "";
                    switch (current.getStatus()) {
                        case 1:
                            status = "待支付";
                            break;
                        case 2:
                            status = "已支付";
                            break;
                        case 3:
                            status = "待发货";
                            break;
                        case 4:
                            status = "待收货";
                            break;
                        case 5:
                            status = "已确认收货";
                            break;
                        case 6:
                            status = "交易完成";
                            break;
                        case 7:
                            status = "交易取消";
                            break;
                        case 8:
                            status = "退换货处理中";
                            break;
                        case 9:
                            status = "问题/缺货";
                            break;
                        default:
                            status = "";

                    }
                    getCell(sheet, nextRowFlag, ++colIndex).setCellValue(status);
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }
                    //订单金额
                    getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getPrice());
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }
                    //订单来源
                    getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getSource());
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }
                    //会员名
                    Member member = current.getMember();
                    if(null ==member){
                        getCell(sheet, nextRowFlag, ++colIndex).setCellValue("");
                    }else{
                        getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getMember().getMemberName());
                    }

                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }
                    //手机
                    if(null ==member){
                        getCell(sheet, nextRowFlag, ++colIndex).setCellValue("");
                    }else{
                        getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getMember().getPhone());
                    }

                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }
                    //下单时间
                    getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getCreatedDate() == null ? "" : df.format(current.getCreatedDate()));
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }

                    //收件人
                    getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getReceiver());
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }

                    //收货地址
                    getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getAddress());
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }

                    //联系电话
                    getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getContact());
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }

                    //配送时间
                    getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getPsDate());
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }
                    //收货时间
                    getCell(sheet, nextRowFlag, ++colIndex).setCellValue(current.getReceiveDate() == null ? "" : df.format(current.getCreatedDate()));
                    getCell(sheet, nextRowFlag, colIndex).setCellStyle(style);
                    if(endRow>nextRowFlag){
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow,colIndex, colIndex));
                    }

                    List<OrderDetail> orderDetails = current.getOrderDetails();
                    int innerNextRowFlag = nextRowFlag;
                    for( int j=0;j<orderDetails.size();j ++ ){
                    int innerColIndex = colIndex;
                        OrderDetail orderDetail = orderDetails.get(j);
                        //商品名称
                        getCell(sheet, innerNextRowFlag, ++innerColIndex).setCellValue(orderDetail.getGoodsName() == null ? "" : orderDetail.getGoodsName());
                        getCell(sheet, innerNextRowFlag, innerColIndex).setCellStyle(style);
                        //购买数量
                        getCell(sheet, innerNextRowFlag, ++innerColIndex).setCellValue(orderDetail.getGoodsNum());
                        getCell(sheet, innerNextRowFlag, innerColIndex).setCellStyle(style);
                        //商品价格
                        Double goodsPrice =orderDetail.getGoodsPrice();
                        getCell(sheet, innerNextRowFlag, ++innerColIndex).setCellValue(goodsPrice == null ? "" : goodsPrice.toString());
                        getCell(sheet, innerNextRowFlag, innerColIndex).setCellStyle(style);
                        innerNextRowFlag++;
                    }
                    nextRowFlag = innerNextRowFlag;

                    if ((i + 1) < orderList.size() && (i + 1) >= Constants.SHEET_MAX_COUNT && (i + 1) % Constants.SHEET_MAX_COUNT == 0) {
                        sheet = workbook.createSheet("SHEET" + (++sheetNum));
                        sheet.setDefaultRowHeightInPoints(20);
                        sheet.setDefaultColumnWidth(15);
                        nextRowFlag = 1;
                        headIndex = 0;

                        cell = getCell(sheet, 0, headIndex);
                        cell.setCellValue("序号");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("订单编号");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("订单状态");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("订单金额");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("订单来源");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("会员名");
                        cell.setCellStyle(style);


                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("手机");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("下单时间");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("收件人");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("收货地址");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("联系电话");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("配送时间");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("收货时间");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue(" 商品名称");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("购买数量");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 0, ++headIndex);
                        cell.setCellValue("单价");
                        cell.setCellStyle(style);
                    }
                }
                String filename = "Order" + df.format(new Date()) + ".xls";// 设置下载时客户端excel的名称
                filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");// 处理中文文件名
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition",
                        "attachment;filename=" + filename);
                OutputStream outputStream = response.getOutputStream();
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            }
        };
    }
    @RequestMapping("delete")
    @ResponseBody
    public Message delete(@RequestParam("id") Long[] ids){
        orderService.delete(ids);
        return Message.success();
    }

}
