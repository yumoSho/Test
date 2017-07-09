package com.glanway.jty.controller.admin.product;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Message;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.gone.spring.bind.domain.support.SimplePage;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.common.Constants;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.logistics.HatProvince;
import com.glanway.jty.entity.product.*;
import com.glanway.jty.service.logistics.HatProvinceService;
import com.glanway.jty.service.product.*;
import com.glanway.jty.utils.StringUtil;
import com.glanway.jty.utils.TimeUtil;
import com.google.common.collect.ImmutableMap;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.saep.excel.Record;
import org.saep.excel.SAEP;
import org.saep.excel.annotation.AnnotatedBeanHandler;
import org.saep.excel.annotation.ExcelColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.vacoor.mux.common.util.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 产品管理
 */
@Controller
@RequestMapping("/admin/product")
public class ProductController extends BaseController {
    @Autowired
    private ModelService modelService;
    @Autowired
    private ProductService productService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ProductAttributeValueService pavService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileImportService fileImportService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private HatProvinceService hatProvinceService;
    @Autowired
    private IndexingService indexingService;
    /**
     * <p>名称：index</p>
     * <p>描述：商品首页</p>
     * @author：LiuJC
     * @return 页面
     */
    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        return "/admin/product/index";
    }

    /**
     * <p>名称：initBinder</p>
     * <p>描述：数据绑定</p>
     * @author：LiuJC
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("H:m"), true));
        binder.registerCustomEditor(List.class, "areas", new CustomStringCollectionBeanCollectionPropertyEditor(
                List.class, true, HatProvince.class, "id", new String[]{}
        ));
    }

    /**
     *
     * <p>名称：add</p>
     * <p>描述：添加产品</p>
     * @author：LiuJC
     */
    @Token(save = true)
    @RequestMapping("add")
    public String add(Map<String, Object> model) {
        model.put("baseModel", modelService.findBaseModel());
        model.put("provinces",hatProvinceService.listAllProvince());
        model.put("labels",labelService.findAll());
        return "/admin/product/add";
    }

    /**
     * <p>名称：save</p>
     * <p>描述：save</p>
     * @author：LiuJC
     * @param product
     * @param redirectAttr
     * @return
     */
    @Token(remove = true)
    @RequestMapping("save")
    public String save(Product product,
                       RedirectAttributes redirectAttr) {
        clean(product);
        productService.saveOrUpdate(product);
        redirectAttr.addFlashAttribute("message", "保存成功");
        return "redirect:/admin/product/index";
    }

    /**
     * <p>名称：clean</p>
     * <p>描述：清除无用数据</p>
     * @author：LiuJC
     * @param product
     */
    private void clean(Product product) {
        List<ParameterValue> pvals = product.getParameterValues();
        if (null != pvals) {
            Iterator<ParameterValue> it = pvals.iterator();
            while (it.hasNext()) {
                ParameterValue value = it.next();
                Parameter param = value.getParameter();
                if (null == param || null == param.getId()) {
                    it.remove();
                }
            }
        }
        List<ProductImg> images = product.getProductImgs();
        if (!CollectionUtils.isEmpty(images)) {
            Iterator<ProductImg> it = images.iterator();
            while (it.hasNext()) {
                ProductImg image = it.next();
                if (null == image || (null == image.getId() && !StringUtils.hasText(image.getPath()))) {
                    it.remove();
                }
            }
        }

    }

    /**
     * <p>名称：list</p>
     * <p>描述：商品列表</p>
     * @author：LiuJC
     * @param brandFilters    品牌条件
     * @param modelFilters    模型条件
     * @param categoryFilters 分类条件
     * @param pageable        分页条件
     * @param productFilters  产品条件
     * @param labelFilters    标签条件
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Page<Product> list(@Qualifier("B.") Filters brandFilters, @Qualifier("M.") Filters modelFilters,
                              @Qualifier("C.") Filters categoryFilters, Pageable pageable,
                              @Qualifier("P.") Filters productFilters, @Qualifier("LL.") Filters labelFilters) {
        return productService.findPage(productFilters, brandFilters, modelFilters, categoryFilters, labelFilters, pageable);
    }

    /**
     * <p>名称：edit</p>
     * <p>描述：编辑商品</p>
     * @author：LiuJC
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     */
    @Token(save = true)
    @RequestMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes redirectAttributes) {

        Product product = productService.find(id);
        if (null == product) {
            redirectAttributes.addFlashAttribute("message", "未找到此商品");
            return "redirect:/admin/product/index";
        }
        Category category = product.getCategory();
        if (category != null) {
            model.put("category", category);
            Model m = category.getModel();
            if (m != null) {
                m = modelService.findDetail(m.getId());
                List<Attribute> attributes = m.getAttributes();
                attributes = null != attributes ? attributes : Collections.<Attribute>emptyList();
                for (int j = 0; j < attributes.size(); j++) {
                    Attribute attribute = attributes.get(j);
                    if (1 == attribute.getDisplayType()) {
                        ProductAttributeValue pav = new ProductAttributeValue();
                        pav.setAttribute(attribute);
                        pav.setProduct(product);
                        if (null != pavService.getAttributeValueByProductIdAndAttributeId(pav)) {
                            attribute.setAttributeValues(
                                    null == pavService.getAttributeValueByProductIdAndAttributeId(pav) ? null
                                            : pavService.getAttributeValueByProductIdAndAttributeId(pav)
                                            .getAttributeValueList());
                        } else {
                            attribute.setAttributeValues(null);
                        }

                    }
                }
                model.put("model", m);
            }
        }
        Model base = modelService.findBaseModel();
        List<Attribute> attributes = base.getAttributes();
        for (int i = 0; i < attributes.size(); i++) {
            Attribute attribute = attributes.get(i);
            if (attribute.getDisplayType() == 1) {
                ProductAttributeValue pav = new ProductAttributeValue();
                pav.setAttribute(attribute);
                pav.setProduct(product);
                pav = pavService.getAttributeValueByProductIdAndAttributeId(pav);
                attribute.setAttributeValues(null == pav ? null : pav.getAttributeValueList());
            }
        }

        model.put("product", product);
        model.put("baseModel", base);
        Filters filter = Filters.create().eq("product_id", product.getId());
        List<Goods> goods = goodsService.findMany(filter, (Pageable) null);

        final int size = null != goods ? goods.size() : 0;
        boolean specEnabled = true;
        if (1 > size) {
            specEnabled = false;
        } else if (1 == size) {
            Boolean isDefault = goods.get(0).getIsDefault();
            if (null != isDefault && isDefault) {
                specEnabled = false;
            }
        }
        product.setEnableSpecs(specEnabled);

        model.put("goods", goods);

        StringBuilder buff = new StringBuilder();
        if (null != goods) {
            for (int i = 0; i < goods.size(); i++) {
                Goods g = goods.get(i);
                List<SpecValue> values = g.getSpecValues();
                if (null != values) {
                    for (SpecValue value : values) {
                        buff.append(value.getId()).append(",");
                    }
                }
            }
            if (buff.length() > 0) {
                buff.deleteCharAt(buff.length() - 1);
            }
        }
        model.put("provinces",hatProvinceService.listAllProvince());
        model.put("selectedSpecValueIds", buff.toString());
        model.put("labels",labelService.findAll());
        return getRelativeViewPath("edit");

    }

    /**
     * <p>名称：delete</p>
     * <p>描述：删除商品</p>
     * @author：LiuJC
     * @param ids 商品集合
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public Map<String, Object> delete(@RequestParam("id") Long[] ids) {

        productService.delete(ids);
        indexingService.rebuildIndex();
        return ImmutableMap.<String, Object>of("success", true);
    }

    /**
     * <p>名称：goodsOfProduct</p>
     * <p>描述：查看product子集合Goodses</p>
     * @author：LiuJC
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("goodsOfProduct")
    public Page<Goods> goodsOfProduct(Long id) {
        Filters filter = Filters.create().eq("product_id", id);
        List<Goods> goods = goodsService.findMany(filter, (Pageable) null);
        Page<Goods> pageGoods = new SimplePage<Goods>((Pageable) null, goods, goods.size());
        return pageGoods;
    }


    /*------------------------导出---------------------------------------*/
    /**
     * <p>名称：export</p>
     * <p>描述：导出商品信息</p>
     * @author：LiuJC
     * @param brandFilters
     * @param modelFilters
     * @param categoryFilters
     * @param pageable
     * @param productFilters
     * @return
     */
    @RequestMapping("export")
    public View export(@Qualifier("B.") Filters brandFilters, @Qualifier("M.") Filters modelFilters,
                       @Qualifier("C.") Filters categoryFilters, Pageable pageable,
                       @Qualifier("P.") Filters productFilters, @Qualifier("LL.") Filters labelFilters) {
        Page<Product> page = productService.findPage(productFilters, brandFilters, modelFilters, categoryFilters, labelFilters, (Pageable) null);
        if (CollectionUtils.isEmpty(page.getData())) {
            return null;
        }
        final List<Product> products = new ArrayList<>();
        for (Product p : page.getData()) {
            Product product = productService.find(p.getId());
            if (null == product) {
                continue;
            }
            Filters filter = Filters.create().eq("product_id", product.getId());
            List<Goods> goods = goodsService.findMany(filter, (Pageable) null);
            product.setGoods(goods);
            products.add(product);
        }

        return new AbstractExcelView() {
            @Override
            protected void buildExcelDocument(
                    Map<String, Object> model,
                    HSSFWorkbook workbook,
                    HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
                HSSFSheet sheet = workbook.createSheet("SHEET1");
                sheet.setDefaultColumnWidth(20);
                sheet.setDefaultRowHeightInPoints(20);
                HSSFCellStyle style = workbook.createCellStyle();
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

                HSSFCell cell = getCell(sheet, 0, 0);
                cell.setCellValue("产品导出");
                cell.setCellStyle(style);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));

                cell = getCell(sheet, 1, 0);
                cell.setCellValue("总行数：" + products.size() + "    导出时间：" + StringUtil.nullToEmpty(TimeUtil.format(new Date())));
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 14));

                cell = getCell(sheet, 2, 0);
                cell.setCellValue("商品名称");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 1);
                cell.setCellValue("产品简介/卖点");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 2);
                cell.setCellValue("产品编号");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 3);
                cell.setCellValue("产品分类");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 4);
                cell.setCellValue("产品模型");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 5);
                cell.setCellValue("产品品牌");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 6);
                cell.setCellValue("标签");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 7);
                cell.setCellValue("虚拟销量");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 8);
                cell.setCellValue("重量");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 9);
                cell.setCellValue("真实销量");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 10);
                cell.setCellValue("是否上架");
                cell.setCellStyle(style);


                cell = getCell(sheet, 2, 11);
                cell.setCellValue("单品名称");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 12);
                cell.setCellValue("货号");
                cell.setCellStyle(style);

                cell = getCell(sheet, 2, 13);
                cell.setCellValue("库存");
                cell.setCellStyle(style);


                cell = getCell(sheet, 2, 14);
                cell.setCellValue("售价");
                cell.setCellStyle(style);

                int prevSheetRowNum = 0;
                int sheetNum = 1;
                //标记下一行的行号
                int nextRowFlag = 3;
                int endRow = 0;
                for (int i = 0; i < products.size(); i++) {
                    Product current = products.get(i);
                    //产品标题
                    getCell(sheet, nextRowFlag, 0).setCellValue(current.getTitle());
                    //产品描述信息
                    getCell(sheet, nextRowFlag, 1).setCellValue(current.getIntro());
                    //产品编号
                    getCell(sheet, nextRowFlag, 2).setCellValue(current.getSn());
                    //分类品牌
                    Category category = current.getCategory();
                    getCell(sheet, nextRowFlag, 3).setCellValue(null == category ? "" : category.getName());
                    //模型名称
                    Model productModel = (null == current.getCategory() ?null :current.getCategory().getModel());
                    getCell(sheet, nextRowFlag, 4).setCellValue(null == productModel ? "" : productModel.getName());
                    //品牌名称
                    Brand brand = current.getBrand();
                    getCell(sheet, nextRowFlag, 5).setCellValue(null == brand ? "" : brand.getName());
                    //标签
                    Label label = current.getLabel();
                    getCell(sheet, nextRowFlag, 6).setCellValue(null == label ? "" : label.getLabelName());
                    //虚拟销量
                    getCell(sheet, nextRowFlag, 7).setCellValue(null == current.getSales()?0:current.getSales());
                    //重量
                    getCell(sheet, nextRowFlag, 8).setCellValue(current.getWeight().doubleValue());
                    //真实销量
                    getCell(sheet, nextRowFlag, 9).setCellValue(current.getWeight().doubleValue());
                    //审核状态
                    Boolean audit = current.getIsPutaway();
                    String stringAudit = null;
                    if (audit) {
                        stringAudit = "已上架";
                    } else {
                        stringAudit = "未上架";
                    }
                    getCell(sheet, nextRowFlag, 10).setCellValue(stringAudit);
                    if (!CollectionUtils.isEmpty(current.getGoods())) {
                        for (int k = 0; k < current.getGoods().size(); k++) {
                            Goods g = current.getGoods().get(k);
                            //单品标题
                            getCell(sheet, nextRowFlag + k, 11).setCellValue(g.getTitle());
                            //单品编号
                            getCell(sheet, nextRowFlag + k, 12).setCellValue(g.getSn());
                            //单品库存
                            getCell(sheet, nextRowFlag + k, 13).setCellValue(g.getStock());
                            //单品售价
                            getCell(sheet, nextRowFlag + k, 14).setCellValue(g.getPrice().doubleValue());
                        }
                    }
                    endRow = current.getGoods().size() > 1 ? nextRowFlag + current.getGoods().size() - 1 : nextRowFlag;   //mark2
                    for (int y = 0; y <= 10; y++) {
                        sheet.addMergedRegion(new CellRangeAddress(nextRowFlag, endRow, y, y));//首行、最后一行、首列、最后一列。
                    }
                    if (0 == current.getGoods().size()) {
                        nextRowFlag += current.getGoods().size() + 1;
                    } else {
                        nextRowFlag += current.getGoods().size();
                    }
                    endRow = 0;
                    if (nextRowFlag >= (prevSheetRowNum + Constants.SHEET_MAX_COUNT)) {
                        prevSheetRowNum = i + 1;
                        sheet = workbook.createSheet("SHEET" + (++sheetNum));
                        sheet.setDefaultColumnWidth(20);
                        sheet.setDefaultRowHeightInPoints(20);
                        nextRowFlag = 2;

                        cell = getCell(sheet, 0, 0);
                        cell.setCellValue("产品导出");
                        cell.setCellStyle(style);
                        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));

                        cell = getCell(sheet, 1, 0);
                        cell.setCellValue("商品名称");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 1);
                        cell.setCellValue("产品简介/卖点");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 2);
                        cell.setCellValue("产品编号");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 3);
                        cell.setCellValue("产品分类");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 4);
                        cell.setCellValue("产品模型");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 5);
                        cell.setCellValue("产品品牌");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 6);
                        cell.setCellValue("标签");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 7);
                        cell.setCellValue("虚拟销量");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 8);
                        cell.setCellValue("重量");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 9);
                        cell.setCellValue("真实销量");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 10);
                        cell.setCellValue("商品状态");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 11);
                        cell.setCellValue("单品名称");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 12);
                        cell.setCellValue("货号");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 13);
                        cell.setCellValue("库存");
                        cell.setCellStyle(style);

                        cell = getCell(sheet, 1, 14);
                        cell.setCellValue("售价");
                        cell.setCellStyle(style);
                    }
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String filename = "Product-" + df.format(new Date()) + ".xls";//设置下载时客户端excel的名称
                filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");//处理中文文件名
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream outputStream = response.getOutputStream();
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();

            }
        };
    }

    /*---------------------------导入----------------------------------*/
    /**
     * <p>名称：导入页面</p>
     * <p>描述：进入Excel导入页面</p>
     * @return
     * @date：2016/5/24 14:01
     * @author：LiuJC
     */

    @RequestMapping("toImport/index")
    public String toImport(ModelMap modelMap) {
        return "admin/product/import";
    }

    /**
     * <p>名称：helpBook</p>
     * <p>描述：商品上传帮助说明页</p>
     * @return
     * @date：2016/5/24 14:01
     * @author：LiuJC
     */
    @RequestMapping("toImport/helpBook")
    public String helpBook(ModelMap modelMap) {
        return "admin/product/helpBook";
    }

    /**
     * <p>名称：importList</p>
     * <p>描述：商品信息导入列表</p>
     * @author：LiuJC
     * @param filters
     * @param pageable
     * @return
     */
    @ResponseBody
    @RequestMapping("toImport/list")
    public Page<FileImport> importList(Filters filters, Pageable pageable) {
        return fileImportService.findPage(filters, pageable);
    }

    /**
     * <p>名称：setFileImportError</p>
     * <p>描述：错误信息记录</p>
     * @author：LiuJC
     * @param fileImport
     * @param title
     * @param name
     * @param error
     * @param status
     * @param total
     */
    private void setFileImportError(FileImport fileImport, String title, String name, String error, Boolean status, Long total) {
        fileImport.setTitle(title);
        fileImport.setName(name);
        fileImport.setError(error);
        fileImport.setStatus(status);
        fileImport.setTotal(total);
    }


    /**
     * 商品信息导入DTO
     */
    public static class ProductExcel {

        /**
         * 产品标题
         */

        @ExcelColumn("A")
        private String title;

        /**
         * 产品简介
         */

        @ExcelColumn("B")
        private String intro;

        /**
         * 产品编码
         */
        @ExcelColumn("C")
        private String sn;

        /**
         * 产品类别
         */
        @ExcelColumn("D")
        private String cate;
        /**
         * 店铺
         */
        @ExcelColumn("E")
        private String label;
        /**
         * 成本价
         */
        @ExcelColumn("F")
        private Double price;
        /**
         * 库存
         */
        @ExcelColumn("G")
        private String stock;
        /**
         * 重量
         */
        @ExcelColumn("H")
        private Double weight;
        /**
         * 警戒库存
         */
        @ExcelColumn("I")
        private String alertStock;
    }

    /**
     * <p>名称：getData</p>
     * <p>描述：商品信息导入操作</p>
     * @author：LiuJC
     * @param file
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("toImport/import")
    public Map<String, Object> getData(@RequestParam(value = "filename", required = false) MultipartFile file, HttpServletRequest request) {
        /**文件导入记录有关系*/
        FileImport fileImport = new FileImport();
        fileImport.setError("");
        if (file == null || file.isEmpty()) {
            setFileImportError(fileImport,
                    "未知文件",
                    "未知文件",
                    "未知文件错误,请检查文件",
                    Boolean.FALSE,
                    0l);
            fileImportService.save(fileImport);
            return ImmutableMap.<String, Object>of("error", Integer.valueOf(1), "message", "为空文件，请查看您的文件");
        }
        //文件上传名称
        String originalFilename = file.getOriginalFilename();
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/import");
        //创建文件对象
        File dir = new File(savePath);
        if (!dir.isDirectory()) {
            /*真是存在一个文件（其实是文件夹）*/
            dir.mkdirs();
        }
        /*获取文件后缀*/
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'), file.getOriginalFilename().length());
        //这样一个时间不会重复，而造成文件覆盖的问题了吧
        String fileName = System.currentTimeMillis() + (int) (Math.random() * 1000000) + suffix;
        //根据 parent 抽象路径名和 child 路径名字符串创建一个新 File 实例。
        File targetFile = new File(savePath, fileName);
        try {
            //保存文件spring mvc MultipartFile提供
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            setFileImportError(fileImport,
                    originalFilename,
                    fileName,
                    "文件保存至服务器失败",
                    Boolean.FALSE,
                    0l);
            fileImportService.save(fileImport);
            return ImmutableMap.<String, Object>of("error", Integer.valueOf(1), "message", "文件保存至服务器失败");
        }
        //读取文件到内存中
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(new File(savePath, fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            setFileImportError(fileImport,
                    originalFilename,
                    fileName,
                    "服务器繁忙，请稍后再试",
                    Boolean.FALSE,
                    0l);
            fileImportService.save(fileImport);
            return ImmutableMap.<String, Object>of("error", Integer.valueOf(1), "message", "服务器繁忙，请稍后再试");
        }
        final List<ProductExcel> temps = new ArrayList<ProductExcel>();
        //读取Excel文件内容
        try {
            SAEP.read(in, new AnnotatedBeanHandler<ProductExcel>(false, 0) {    // 跳过第一行
                protected void onBeanRecord(ProductExcel rec, int row, Record raw) {
                    temps.add(rec);
                }
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
            setFileImportError(fileImport,
                    originalFilename,
                    fileName,
                    "请核对文件内容,Excel可能少一列数据<br/>" +
                            "或者多一列或格式不正确<br/>",
                    Boolean.FALSE,
                    0l);
            fileImportService.save(fileImport);
            return ImmutableMap.<String, Object>of("error", Integer.valueOf(1), "message", "核对文件内容，或格式");
        } finally {
            //一定要记得关闭流
            IOUtils.close(in);
        }
        if (CollectionUtils.isEmpty(temps)) {
            setFileImportError(fileImport,
                    originalFilename,
                    fileName,
                    "文件内容为空",
                    Boolean.FALSE,
                    0l);
            fileImportService.save(fileImport);
            return ImmutableMap.<String, Object>of("error", Integer.valueOf(1), "message", "Invalid file, must not be empty");
        }
        List<Product> products = new ArrayList<>();

        //开始读取数据， 判断数据是否正确
        for (int i = 0; i < temps.size(); i++) {
            ProductExcel productOne = temps.get(i);
            if (null == productOne) {
                setFileImportError(fileImport,
                        originalFilename,
                        fileName,
                        fileImport.getError() + "A" + i + 2 + "行木有响应数据<br>",
                        Boolean.FALSE,
                        (long) temps.size());
                continue;
            }
            if (!StringUtils.hasText(productOne.title)) {
                //标题 必须填写
                setFileImportError(fileImport,
                        originalFilename,
                        fileName,
                        fileImport.getError() + "A" + (2 + i) + "单元格（产品标题）不能为空<br>",
                        Boolean.FALSE,
                        (long) temps.size());
                continue;
            }
            if (!StringUtils.hasText(productOne.intro)) {
                //code 必须填写;
                setFileImportError(fileImport,
                        originalFilename,
                        fileName,
                        fileImport.getError() + "B" + (2 + i) + "单元格（产品简介）不能为空<br>",
                        Boolean.FALSE,
                        (long) temps.size());

                continue;
            }
            if (!StringUtils.hasText(productOne.sn)) {
                //code 必须填写;
                setFileImportError(fileImport,
                        originalFilename,
                        fileName,
                        fileImport.getError() + "C" + (2 + i) + "单元格（产品编码）不能为空<br>",
                        Boolean.FALSE,
                        (long) temps.size());

                continue;
            }

            Category category = null;
            if (!StringUtils.hasText(productOne.cate)) {
                setFileImportError(fileImport,
                        originalFilename,
                        fileName,
                        fileImport.getError() + "D" + (2 + i) + "单元格（分类）不能为空<br>",
                        Boolean.FALSE,
                        (long) temps.size());
                continue;
            } else {
                //分类必须填写而且可以找到该分类
                Filters filters = Filters.create();
                filters.eq("tc.categoryCode", productOne.cate);
                List<Category> categoryList = categoryService.findMany(filters, (Pageable) null);
                if (CollectionUtils.isEmpty(categoryList)) {
                    //没有找到此分类
                    setFileImportError(fileImport,
                            originalFilename,
                            fileName,
                            fileImport.getError() + "D" + (2 + i) + "单元格（分类）没有找到该分类<br>",
                            Boolean.FALSE,
                            (long) temps.size());
                    continue;
                } else if (1 != categoryList.size()) {
                    //发现了多个相同名称分类
                    setFileImportError(fileImport,
                            originalFilename,
                            fileName,
                            fileImport.getError() + "D" + (2 + i) + "单元格（分类）找到了多个相同分类<br>",
                            Boolean.FALSE,
                            (long) temps.size());
                    continue;
                } else {
                    //发现1个这个是正确的
                    category = categoryList.get(0);
                }
            }

            Label label = null;
            if (!StringUtils.hasText(productOne.label)) {


            } else {
                //分类必须填写而且可以找到该分类
                Filters filters  = Filters.create().eq("labelCode",productOne.label).eq("deleted",Boolean.FALSE);
                List<Label> labels = labelService.findMany(filters,(Pageable)null);
                if (CollectionUtils.isEmpty(labels)) {
                    //没有找到此标签
                    setFileImportError(fileImport,
                            originalFilename,
                            fileName,
                            fileImport.getError() + "E" + (2 + i) + "单元格（标签）没有找到该标签<br>",
                            Boolean.FALSE,
                            (long) temps.size());
                    continue;
                } else if (1 != labels.size()) {
                    //发现了多个相同名称分类
                    setFileImportError(fileImport,
                            originalFilename,
                            fileName,
                            fileImport.getError() + "E" + (2 + i) + "单元格（标签）找到了多个相同标签<br>",
                            Boolean.FALSE,
                            (long) temps.size());
                    continue;
                } else {
                    //发现1个这个是正确的
                    label = labels.get(0);
                }
            }


            //成本价
            BigDecimal price = null;
            if (null == productOne.price) {
                setFileImportError(fileImport,
                        originalFilename,
                        fileName,
                        fileImport.getError() + "F" + (2 + i) + "单元格（售价）不能为空<br>",
                        Boolean.FALSE,
                        (long) temps.size());
                continue;
            } else {
                try {
                    price = new BigDecimal(productOne.price);
                } catch (Exception e) {
                    e.printStackTrace();
                    setFileImportError(fileImport,
                            originalFilename,
                            fileName,
                            fileImport.getError() + "F" + (2 + i) + "单元格（售价)数字格式不正确<br>",
                            Boolean.FALSE,
                            (long) temps.size());
                    continue;
                }
            }

            //库存
            Long stock = null;
            if (null == productOne.stock) {
                setFileImportError(fileImport,
                        originalFilename,
                        fileName,
                        fileImport.getError() + "G" + (2 + i) + "单元格（库存）不能为空<br>",
                        Boolean.FALSE,
                        (long) temps.size());
                continue;
            } else {
                try {
                    stock = Long.parseLong(productOne.stock);
                } catch (Exception e) {
                    setFileImportError(fileImport,
                            originalFilename,
                            fileName,
                            fileImport.getError() + "G" + (2 + i) + "单元格（库存）格式不正确<br>",
                            Boolean.FALSE,
                            (long) temps.size());
                    continue;
                }
            }


            //重量
            BigDecimal weight = null;
            if (null == productOne.weight) {
                setFileImportError(fileImport,
                        originalFilename,
                        fileName,
                        fileImport.getError() + "H" + (2 + i) + "单元格（重量）不能为空<br>",
                        Boolean.FALSE,
                        (long) temps.size());
                continue;
            } else {
                weight = new BigDecimal(productOne.weight);
            }


            //库存
            Long alertStock = null;
            if (null == productOne.alertStock) {
                setFileImportError(fileImport,
                        originalFilename,
                        fileName,
                        fileImport.getError() + "I" + (2 + i) + "单元格（警戒库存）不能为空<br>",
                        Boolean.FALSE,
                        (long) temps.size());
                continue;
            } else {
                try {
                    alertStock = Long.parseLong(productOne.alertStock);
                } catch (Exception e) {
                    setFileImportError(fileImport,
                            originalFilename,
                            fileName,
                            fileImport.getError() + "I" + (2 + i) + "单元格（警戒库存）格式不正确<br>",
                            Boolean.FALSE,
                            (long) temps.size());
                    continue;
                }
            }

            Product product = new Product();
            product.setTitle(productOne.title);
            product.setIntro(productOne.intro);
            product.setSn(productOne.sn);
            product.setCategory(category);
            product.setPrice(price);
            product.setStock(stock);
            product.setAlertStock(0l);
            product.setWeight(weight);
            product.setLabel(label);
            product.setAlertStock(alertStock);
            product.setEnableSpecs(false);
            product.setIsPutaway(false);
            products.add(product);
        }
        if (products.size() == temps.size()) {
            try {
                productService.save(products);
            } catch (Exception e) {
                e.printStackTrace();
                setFileImportError(fileImport,
                        originalFilename,
                        fileName,
                        fileImport.getError() + "保存失败请检查您的文件格式<br>",
                        Boolean.FALSE,
                        (long) temps.size());
                fileImportService.save(fileImport);
                return ImmutableMap.<String, Object>of("error", Integer.valueOf(1), "message", "保存失败请检查您的文件格式");
            }
            setFileImportError(fileImport,
                    originalFilename,
                    fileName,
                    "",
                    Boolean.TRUE,
                    (long) temps.size());
            fileImportService.save(fileImport);
            return ImmutableMap.<String, Object>of("error", Integer.valueOf(0), "path", 1, "url", 1);
        } else {
            setFileImportError(fileImport,
                    originalFilename,
                    fileName,
                    fileImport.getError() + "请检查您的文件格式<br>",
                    Boolean.FALSE,
                    (long) temps.size());
            fileImportService.save(fileImport);
            return ImmutableMap.<String, Object>of("error", Integer.valueOf(1), "message", "请检查您的数据格式");
        }
    }


    /**
     * <p>名称：export</p>
     * <p>描述：获取商品导入模板</p>
     * @author：LiuJC
     * @return
     */
    @RequestMapping("toImport/getTemplate")
    public View export() {
        return new AbstractExcelView() {
            @Override
            protected void buildExcelDocument(
                    Map<String, Object> model,
                    HSSFWorkbook workbook,
                    HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
                // TODO Auto-generated method stub

                // 第一步，创建一个webbook，对应一个Excel文件
                // HSSFWorkbook wb = new HSSFWorkbook();
                // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
                // HSSFSheet sheet = wb.createSheet("采购单");
                HSSFSheet sheet = workbook.createSheet("sheet1");
                // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
                // HSSFRow row = sheet.createRow((int) 0);
                // 第四步，创建单元格，并设置值表头 设置表头居中
                HSSFCellStyle style = workbook.createCellStyle();

                HSSFCellStyle cellStyle = workbook.createCellStyle();
                HSSFDataFormat format = workbook.createDataFormat();
                cellStyle.setDataFormat(format.getFormat("yyyy/MM/dd"));
                //保留小数后两位
                HSSFCellStyle floatStyle = workbook.createCellStyle();
                floatStyle.setDataFormat((short) 2);
                //保留整数
                HSSFCellStyle intStyle = workbook.createCellStyle();
                intStyle.setDataFormat((short) 1);

                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
                // getCell(sheet, 0, col)
                HSSFCell cell = getCell(sheet, 0, 0);
                cell.setCellValue("产品标题");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, 1);
                cell.setCellValue("产品简介");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, 2);
                cell.setCellValue("产品编号");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, 3);
                cell.setCellValue("类别");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, 4);
                cell.setCellValue("标签");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, 5);
                cell.setCellValue("售价");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, 6);
                cell.setCellValue("库存");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, 7);
                cell.setCellValue("重量");
                cell.setCellStyle(style);

                cell = getCell(sheet, 0, 8);
                cell.setCellValue("警戒库存");
                cell.setCellStyle(style);


                // 第五步，写入实体数据 实际应用中这些数据从数据库得到
                // 第四步，创建单元格，并设置值
                //产品名称
                getCell(sheet, 1, 0).setCellValue("这是个例子产品");
                //简介
                getCell(sheet, 1, 1).setCellValue("这是个例子简介");
                //编号
                getCell(sheet, 1, 2).setCellValue("ZB131233232");
                //分类
                getCell(sheet, 1, 3).setCellValue("C1002");
                //店铺
                getCell(sheet, 1, 4).setCellValue("L1002");
                //售价
                getCell(sheet, 1, 5).setCellValue(20.50);
                //库存
                getCell(sheet, 1, 6).setCellValue("12");
                //重量
                getCell(sheet, 1, 7).setCellValue(23.2);
                getCell(sheet, 1, 8).setCellValue("10");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String filename = "productImport-" + df.format(new Date()) + ".xls";//设置下载时客户端Excel的名称
                filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");//处理中文文件名
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();
                workbook.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
            }
        };
    }


    /**
     * <p>名称：importDelete</p>
     * <p>描述：删除导入信息和文件删除</p>
     * @author：LiuJC
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("toImport/delete")
    public Message importDelete(@RequestParam("id") Long id, HttpServletRequest request) {
        FileImport anImport = fileImportService.find(id);
        String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/import");
        Boolean falg = deleteFile(savePath + "/" + anImport.getName());
        if (falg) {
            fileImportService.delete(id);
            return Message.success();
        } else {
            return Message.fail("文件删除失败");
        }
    }

    /**
     * <p>名称：deleteFile</p>
     * <p>描述：删除文件</p>
     * @author：LiuJC
     * @param sPath
     * @return
     */
    public boolean deleteFile(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
