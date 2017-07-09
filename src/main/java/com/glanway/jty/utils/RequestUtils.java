/**
 * Copyright (c) 2005, 2014  glanway.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * RequestUtils 实现
 * update  2015-8-6 :添加RequestUtils类，并实现基本方法
 * @author zuo yang
 */
package com.glanway.jty.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RequestUtils {
	private static final Logger logger=Logger.getLogger(RequestUtils.class);
	
	/**
	 * 获取request请求中的信息，如果不空返回Integer类型
	 * @param name
	 * @param request
	 * @return
	 */
	public static Integer getParameterInt(String name,HttpServletRequest request){
		String obj = request.getParameter(name);
		Integer i = null;
		if (StringUtils.isNotBlank(obj)){
			i = Integer.parseInt(obj);
		}
		return i;
	}
	
	/**
	 * 获取request请求中的信息，如果不空返回String类型(防中文乱码)
	 * @param name
	 * @param request
	 * @return
	 */
	public static String getRequestGETParameterString(String name,HttpServletRequest request){
		String obj = request.getParameter(name);
		String i = null;
		if (StringUtils.isNotBlank(obj)){
			try {
				i = new String(obj.getBytes("UTF-8"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.info("UnsupportedEncodingException: "+e,e);
			}
		}
		return i;
	}
	
	/**
	 * 调用当前浏览器下载器下载excel文件
	 * @param workbook
	 * @param fileName
	 * @param request
	 * @param response
	 */
	public static void downloadByBrowserForExcel(HSSFWorkbook workbook,String fileName,
			HttpServletRequest request,HttpServletResponse response){
		
	    //调用浏览器下载器
	    response.reset();     
	    response.setContentType("application/x-msdownload");
	    OutputStream ouputStream = null;
	    try {
	    	String fileNameStr = "";
	    	if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {  
	    		fileNameStr = URLEncoder.encode(fileName, "UTF8"); ;  
	    	} else {  
	    		fileNameStr = new String(fileName.getBytes("UTF-8"), "ISO8859-1");  
	    	} 
//	    	String fileNameStr = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			response.setHeader("Content-Disposition","attachment; filename="+fileNameStr);
			ouputStream = response.getOutputStream();    
			workbook.write(ouputStream); 
	    } catch (UnsupportedEncodingException e) {
			logger.info("ExportDemandReport UnsupportedEncodingException:"+e,e);
		} catch (IOException e) {
			logger.info("ExportDemandReport IOException:"+e,e);
		}finally{
        	if(ouputStream!=null){
   		       try {
   		    	ouputStream.close();
				} catch (IOException e) {
					logger.info("Export demand excel close Exception:"+e,e);
				} 
   	         }
		}
	}
	/**
	 * 调用当前浏览器下载器下载文件
	 * @param filePath
	 * @param request
	 * @param response
	 */
	public static void downloadByBrowserForFile(String filePath,
			HttpServletRequest request,HttpServletResponse response){
		
		//调用浏览器下载器
		File file = new File(filePath);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("multipart/form-data");
		String agent = request.getHeader("USER-AGENT");
		
		String downLoadName = null;
		try {
			byte[] bytes = agent.contains("MSIE")? URLEncoder.encode(file.getName(), "UTF-8").getBytes("UTF-8")
					:file.getName().getBytes("UTF-8");
			downLoadName = new String(bytes,"iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.info("UnsupportedEncodingException:"+e, e);
		}
		
		response.addHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", downLoadName));
		response.addHeader("Content-Length", String.valueOf(file.length()));
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[1024];
			int byteRead = -1;
			while((byteRead = bis.read(buff,0,buff.length))!=-1){
				bos.write(buff, 0, byteRead);
			}
		} catch (FileNotFoundException e) {
			logger.info("FileNotFoundException:"+e, e);
		} catch (IOException e) {
			logger.info("IOException:"+e, e);
		}finally{
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					logger.info("IOException:"+e, e);
				}
			}
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					logger.info("IOException:"+e, e);
				}
			}
		}
	}
	
	/**
	 * 判断字符串是否全由数字组成(是否符合数字格式)
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		  for (int i = 0; i < str.length(); i++){
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }
	

	protected static String render(HttpServletResponse response, String text, String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {

		}
		return null;
	}
	
	/**
	 * 往页面上 传json
	 * @param response
	 * @param json
	 * @return
	 */
	public static String renderJson(HttpServletResponse response, String json) {
		return render(response, json, "text/json;charset=UTF-8");
	}

    //todo 纯粹验证本次token是否正常
    public static boolean idRepeat(HttpServletRequest request,HttpSession session){

        List<String> uniques=(List<String>)session.getAttribute("token");
        String token=request.getParameter("uniquePage");
        if(null==uniques||null==token||token.trim().length()<1||uniques.size()<1){
            return false;
        }
        int num=0;
        for(String unique:uniques){
            if(token.equals(unique)){
                num++;
            }
        }
        if(num==0)
            return false;
        else
            return true;
    }

    //todo 如果form正常操作成功，清楚session中的数据
    public static void removeToken(HttpServletRequest request,HttpSession session){
        List<String> uniques=(List<String>)session.getAttribute("token");
        String token=request.getParameter("uniquePage");
        List<String> duplicateRemoval=new ArrayList<String>();
        for(String unique:uniques){
            if(!token.equals(unique)){
                duplicateRemoval.add(unique);
            }
        }
        session.removeAttribute("token");
        if(duplicateRemoval.size()>0){
            session.setAttribute("token",duplicateRemoval);
        }
    }





}
