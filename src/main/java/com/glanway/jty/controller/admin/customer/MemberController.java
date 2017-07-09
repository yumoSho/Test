/*
* Copyright (c) 2005, 2016  glanway.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.glanway.jty.controller.admin.customer;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.annotation.Token;
import com.glanway.jty.common.Constants;
import com.glanway.jty.common.json.JSONUtil;
import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.customer.Grade;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.service.customer.GradeService;
import com.glanway.jty.service.customer.MemberService;
import com.glanway.jty.service.dictionary.DictionaryService;
import com.glanway.jty.utils.StringUtil;
import com.glanway.jty.utils.TimeUtil;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* </b>Member Controller
*  @author  generator
*  @Time     2016-04-12
*  @version 1.0
*/

@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController extends BaseController {
	private final static String PATH = "admin/member/";
	private  static Log logger= LogFactory.getLog(MemberController.class);
	@Autowired(required=false) //自动注入
	private MemberService memberService;

	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private GradeService gradeService;

	/**
	 * <p>名称：</p>
	 * <p>描述：会员列表页</p>
	 * @author：tianxuan
	 * @param map
	 * @return
     */
	@RequestMapping("index")
	public String index(ModelMap map){
		List<Dictionary> froms = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		List<Dictionary> memberStatus = dictionaryService.findBySuperDicCode(Constants.DT_HYZT);
		map.put("froms", JSONUtil.getJSONString(froms));
		map.put("memberStatus",JSONUtil.getJSONString(memberStatus));
		return PATH + "index";
	}

	/**
	 * <p>名称：</p>
	 * <p>描述：添加页面</p>
	 * @author：tianxuan
	 * @return
     */
	@Token(save = true)
    @RequestMapping("add")
    public String add(){
        return PATH + "add";
    }

	/**
	 * <p>名称：</p>
	 * <p>描述：保存会员信息</p>
	 * @author：tianxuan
	 * @param member
	 * @return
     */
	@Token(remove = true)
	@RequestMapping("save")
	public String save(Member member){
		memberService.save(member);
		return "redirect:/"+PATH + "/index";
	}

	/**
	 * <p>名称：</p>
	 * <p>描述：列表页查询方法</p>
	 * @author：tianxuan
	 * @param filters
	 * @param pageable
     * @return
     */
	@RequestMapping("list")
	@ResponseBody
	public Page<Member> list(Filters filters, Pageable pageable){
    	return memberService.findPage(filters,  pageable);
	}


	/**
	 * 删除方法
	 * @param id
	 * @return
     */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") Long[] id){
		memberService.delete(id);
		return ImmutableMap.<String, Object>of("success", true);
	}

	/**
	 * <p>名称：</p>
	 * <p>描述：编辑页面</p>
	 * @author：tianxuan
	 * @param id
	 * @param modelMap
     * @return
     */
	@Token(save = true)
	@RequestMapping("edit/{id}")
	public String edit(@PathVariable(value="id") Long id,ModelMap modelMap){
		Member member = memberService.find(id);
		if(null == member || member.getDeleted()) {
			return "redirect:/admin/member/index";
		}
		List<Dictionary> froms = dictionaryService.findBySuperDicCode(Constants.DT_ZCPT);
		List<Dictionary> memberStatus = dictionaryService.findBySuperDicCode(Constants.DT_HYZT);
		List<Grade> gradeList = gradeService.findAll();
		modelMap.put("member",member);
		modelMap.put("froms", froms);
		modelMap.put("gradeList", gradeList);
		modelMap.put("memberStatus",memberStatus);
        return PATH + "edit";
	}

	/**
	 * <p>名称：</p>
	 * <p>描述：更新</p>
	 * @author：tianxuan
	 * @param member
	 * @return
     */
	@Token(remove = true)
	@RequestMapping("update")
	public String update(Member member){
		if(null != member.getGradeId()){
			Grade grade = gradeService.find(member.getGradeId());
			member.setGradeName(grade.getName());
		}

        memberService.update(member);
		return "redirect:/"+ PATH + "index";
	}

	/**
	 * <p>名称：</p>
	 * <p>描述：将总表导出为excel</p>
	 * @author：tianxuan
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@RequestMapping("export")
	public View exportAccountByDay(Filters filters, final Pageable pageable){
		filters.eq("deleted",0);
		final List<Member> memberList =  memberService.findToExport(filters,pageable);
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

				HSSFCell cell= getCell(sheet,0,0);
				cell.setCellValue("会员管理    总行数："+ memberList.size() + "    导出时间：" + StringUtil.nullToEmpty(TimeUtil.format(new Date())));
				sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));
				cell = getCell(sheet, 1, 0);
				cell.setCellValue("会员编号");
				cell.setCellStyle(style);

				cell = getCell(sheet, 1, 1);
				cell.setCellValue("用户名");
				cell.setCellStyle(style);

				cell = getCell(sheet, 1, 2);
				cell.setCellValue("手机号");
				cell.setCellStyle(style);

				cell=getCell(sheet, 1 , 3);
				cell.setCellValue("关注店铺");
				cell.setCellStyle(style);

				cell=getCell(sheet, 1 , 4);
				cell.setCellValue("店铺标签");
				cell.setCellStyle(style);

				cell=getCell(sheet, 1 , 5);
				cell.setCellValue("状态");
				cell.setCellStyle(style);

				cell=getCell(sheet, 1 , 6);
				cell.setCellValue("注册时间");
				cell.setCellStyle(style);

				int nextRowFlag = 2;
				int sheetNum = 1;
				Member current ;
				int prevSheetRowNum = 0 ;
				for(int i = 0;i<memberList.size();i++){
					current = memberList.get(i);
					//会员编号
					getCell(sheet,nextRowFlag,0).setCellValue(current.getMemberCode());
					//用户名
					getCell(sheet,nextRowFlag,1).setCellValue(current.getMemberName());
					//手机号
					getCell(sheet,nextRowFlag,2).setCellValue(current.getPhone());
					//状态
					getCell(sheet,nextRowFlag,5).setCellValue(getStatus(current.getStatus()));
					//注册时间
					getCell(sheet,nextRowFlag,6).setCellValue(TimeUtil.format(current.getRegisterDate()));

					nextRowFlag++;
					if((i+1) < memberList.size() &&  (i+1) >=  (prevSheetRowNum + Constants.SHEET_MAX_COUNT )){
						prevSheetRowNum = i + 1;
						sheet = workbook.createSheet("SHEET" + (++sheetNum));
						sheet.setDefaultColumnWidth(20);
						sheet.setDefaultRowHeightInPoints(20);
						nextRowFlag = 2;

						cell= getCell(sheet,0,0);
						cell.setCellValue("会员管理");
						sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));

						cell = getCell(sheet, 1, 0);
						cell.setCellValue("会员编号");
						cell.setCellStyle(style);

						cell = getCell(sheet, 1, 1);
						cell.setCellValue("用户名");
						cell.setCellStyle(style);

						cell = getCell(sheet, 1, 2);
						cell.setCellValue("手机号");
						cell.setCellStyle(style);

						cell=getCell(sheet, 1 , 3);
						cell.setCellValue("关注店铺");
						cell.setCellStyle(style);

						cell=getCell(sheet, 1 , 4);
						cell.setCellValue("店铺标签");
						cell.setCellStyle(style);

						cell=getCell(sheet, 1 , 5);
						cell.setCellValue("状态");
						cell.setCellStyle(style);

						cell=getCell(sheet, 1 , 6);
						cell.setCellValue("注册时间");
						cell.setCellStyle(style);
					}
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String filename= "member-" + df.format(new Date()) + ".xls";//设置下载时客户端excel的名称
				filename = new String(filename.getBytes("UTF-8"),"ISO-8859-1");//处理中文文件名
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition", "attachment;filename=" + filename);
				OutputStream outputStream = response.getOutputStream();
				workbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
			}
		};
	}

	/*解析会员状态*/
	private String getStatus(Integer  status){
		switch (status){
			case 1: return "正常";
			case 2: return "冻结";
		}
		return "";
	}
}
