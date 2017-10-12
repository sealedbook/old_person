/*
 * Created on 2005-5-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.esite.framework.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import com.esite.framework.core.exception.BaseException;

/**
 * @author zhangzf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadExcel {

	private String fileName;
	private HSSFWorkbook wb = null;
//	private HSSFSheet currentSheet = null;
	private SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
	public ReadExcel() {
		super();
	}
	
	public ReadExcel(String fileName) {
		super();
		this.fileName = fileName;
		init();
	}
	
	public ReadExcel(String fileName,InputStream in) {
		super();
		this.fileName = fileName;
		init(in);
	}
	
	public int getSheetNumber(){
		if(wb==null)
			return -1;
		return wb.getNumberOfSheets();
	}
	
	public void setDateFormat(String format){
		defaultFormat = new SimpleDateFormat(format);
	}
	
	/**
	 * 读取sheet
	 * @param sheetIndex
	 * @return
	 */
	public List Read(int sheetIndex){
		List list = null;
		list = read(this.getSheet(sheetIndex));
		return list;
	}
	/**
	 * 读取sheet
	 * @param sheetName
	 * @return
	 */
	public List Read(String sheetName){
		List list = null;
		list = read(this.getSheet(sheetName));
		return list;
	}
	/**
	 * 读取sheet的实际method
	 * @param sheet
	 * @return
	 */
	private List read(HSSFSheet sheet){
		List list = new ArrayList();
		int rowFirst = sheet.getFirstRowNum();
		int rowLast = sheet.getLastRowNum();
		System.out.println("first = "+rowFirst+";last = "+rowLast);
		for(int rowIndex=rowFirst;rowIndex<=rowLast;rowIndex++){
			HSSFRow row = sheet.getRow(rowIndex);
			if(row==null)
				continue;
			List rowList = new ArrayList();
			short cellFirst = row.getFirstCellNum();
			short cellLast = row.getLastCellNum();
			for (short cellIndex = 0; cellIndex < cellLast; cellIndex++) {
				HSSFCell cell = row.getCell(cellIndex);
				if (cell == null) {
					rowList.add(null);
				} else {
					rowList.add(getCellValue(cell));
				}
			}
			
			list.add(rowList);
		}
		return list;
	}
    /**
     * 初始化
     */
	public void init() {
		POIFSFileSystem fs = null;
		if(fileName==null)
			throw new BaseException("请指定数据文件!");
		try {
			fs = new POIFSFileSystem(new FileInputStream(fileName));
			wb = new HSSFWorkbook(fs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BaseException("文件不存在!");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BaseException("不能读取文件," + e.getMessage());
		}
	}
	
	public void init(InputStream in) {
		POIFSFileSystem fs = null;
		if(fileName==null)
			throw new BaseException("请指定数据文件!");
		try {
			fs = new POIFSFileSystem(in);
			wb = new HSSFWorkbook(fs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BaseException("文件不存在!");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BaseException("不能读取文件," + e.getMessage());
		}
	}
	
	/**
	 * 获取Workbook
	 * @return
	 */
	public HSSFWorkbook getWorkbook() {
		if(wb==null){
			init();
		}
		return wb;
	}
	/**
	 * 
	 * @return
	 */
	public String getFileName(){
		return fileName;
	}
	/**
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName){
		this.fileName= fileName;
	}
	/**
	 * 
	 * @param index
	 * @return
	 */
	public HSSFSheet getSheet(int index){
		return wb.getSheetAt(index);
	}
	/**
	 * 
	 * @param sheetName
	 * @return
	 */
	public HSSFSheet getSheet(String sheetName){
		return wb.getSheet(sheetName);
	}
	/**
	 * 读取cell的内容
	 * @param cell
	 * @return
	 */
	private String getCellValue(HSSFCell cell){
		String value = null;
		switch(cell.getCellType()){
			case Cell.CELL_TYPE_BLANK:
				 value=null;
				 break;
		    case Cell.CELL_TYPE_BOOLEAN:
		    	 value=String.valueOf(cell.getBooleanCellValue());
		         break;
		    case Cell.CELL_TYPE_ERROR:
		    	 value=String.valueOf(cell.getErrorCellValue());
		         break;
		    case Cell.CELL_TYPE_FORMULA:
		    	 value=String.valueOf(cell.getCellFormula());
		         break;
		    case Cell.CELL_TYPE_NUMERIC:
		    	 double tempValue=cell.getNumericCellValue();
		    	 String format = cell.getCellStyle().getDataFormatString();
		    	 if (DateUtil.isCellDateFormatted(cell)) {  
		    		 Date date = cell.getDateCellValue();
		    		 if("h:mm".equals(format)||"h:mm;@".equals(format)){
		    			 value = DateHelper.foramt(date, "HH:mm");
		    		 }else{
		    			 value = this.defaultFormat.format(date);
		    		 }
		    	 }else{
					if (Math.round(tempValue) == tempValue)
						value = String.valueOf(Math.round(tempValue));
					else
						value = String.valueOf(tempValue);
		    	 }		    	 
		         break;
		    case Cell.CELL_TYPE_STRING:
		    	 value=cell.getStringCellValue();
		         break;
		}
		return value==null?null:value.trim();
	}
	
	public static void main(String[] args) {
		ReadExcel ie = new ReadExcel("e:/a.xls");
		List list = ie.Read(0);
		System.out.println(list);
	}
}
