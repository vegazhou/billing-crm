package com.kt.util;

import com.kt.repo.edr.bean.BillDetail;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FileUtil {
	private static final String CSV_SEPARATOR = ",";
	private static final Log logger = LogFactory.getLog(FileUtil.class);
	// 将信息写入输出流的方法。

	/*
	public static <T> void exportExcel(String filePath,List<Object[]> fieldSettingList, List<T> fieldDatasList) throws Exception {
		logger.debug( "Export Excel ! ");
		HSSFWorkbook workBook = new HSSFWorkbook();// 创建一个工作簿
		int rows = fieldDatasList.size();// 清点出输入数据的行数
		int columns = fieldSettingList.size();
		HSSFSheet sheet = workBook.createSheet("Page");// 创建工作表
		HSSFRow headRow = sheet.createRow(0); // 创建第一栏，抬头栏
		for (int j = 0; j < columns; j++) {
			HSSFCell cell = headRow.createCell(j);// 创建抬头栏单元格
			// 设置单元格格式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sheet.setColumnWidth(j, 6000);
			HSSFCellStyle style = workBook.createCellStyle();
			HSSFFont font = workBook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			short color = HSSFColor.RED.index;
			font.setColor(color);
			style.setFont(font);
			// 将数据填入单元格
			Object[] field = fieldSettingList.get(j);
			if (field != null) {
				cell.setCellStyle(style);
				cell.setCellValue(field[1].toString());
			} else {
				cell.setCellStyle(style);
				cell.setCellValue("-");
			}
		}
		// 创建数据栏单元格并填入数据
		
		HSSFCellStyle cellStringStyle = workBook.createCellStyle();
		HSSFDataFormat format = workBook.createDataFormat();
		cellStringStyle.setDataFormat(format.getFormat("@"));

		for (int k = 0; k < rows; k++) {
			HSSFRow row = sheet.createRow(k + 1);
			Object o = fieldDatasList.get(k);
			for (int n = 0; n < columns; n++) {
				HSSFCell cell = row.createCell(n);
				Method m = (Method)fieldSettingList.get(n)[0];
				Object value = null;
				try {
					value = m.invoke(o);
				} catch (IllegalAccessException e) {
					logger.error("ExcelFileUtil.exportExcel IllegalAccessException:" + filePath, e);
				} catch (IllegalArgumentException e) {
					logger.error("ExcelFileUtil.exportExcel IllegalArgumentException:" + filePath, e);
				} catch (InvocationTargetException e) {
					logger.error("ExcelFileUtil.exportExcel InvocationTargetException:" + filePath, e);
				}
				String classType = m.getGenericReturnType().toString();
				logger.debug( classType);
				String v = null;
				if(value != null){
					v = value.toString();
				}
				if (StringUtils.isNotBlank(v)) {
					if("Integer".equals(classType) || "int".equals(classType)){
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Integer.parseInt(v));
					}else if("Double".equals(classType) || "double".equals(classType)){
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.parseDouble(v));
					}else if("Float".equals(classType) || "float".equals(classType)){
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Float.parseFloat(v));
					}else{
						if(StringUtils.isNumeric(v)){
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							cell.setCellStyle(cellStringStyle);
							if(v.indexOf(".") == -1){
								cell.setCellValue(Long.parseLong(v));
							}else{
								cell.setCellValue(Double.parseDouble(v));								
							}
							
						}else{
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(new HSSFRichTextString(v));
						}
					}
					
				} else {
					cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					cell.setCellValue("");
				}
			}
		}
		
//		for (int j = 0; j < columns; j++) {
//			 sheet.autoSizeColumn(j);
//		}
		
		try {
			OutputStream os = new FileOutputStream(filePath);
			workBook.write(os);
			os.close();
		} catch (Exception e) {
			logger.error("ExcelFileUtil.exportExcel write Exception:" + filePath, e);
			throw new Exception("Export file error: filePath--> " + filePath);
		}
	}
*/
	public static <T> void exportExcel(String filePath, List<Object[]> fieldSettingList, List<T> fieldDatasList) throws Exception {

		File file = new File(filePath);
		File parentFile = file.getParentFile();
		if(!parentFile.exists()){
			parentFile.mkdirs();
		}

		HSSFWorkbook workBook = getHssfWorkbook(fieldSettingList, fieldDatasList);

		try {
			OutputStream os = new FileOutputStream(filePath);
			workBook.write(os);
			os.close();
		} catch (Exception e) {
			logger.error("ExcelFileUtil.exportExcel write Exception:" + filePath, e);
			throw new Exception("Export file error: filePath--> " + filePath);
		}
	}

	private static <T> HSSFWorkbook getHssfWorkbook(List<Object[]> fieldSettingList, List<T> fieldDatasList) {
		HSSFWorkbook workBook = new HSSFWorkbook();// 创建一个工作簿
		int rows = fieldDatasList.size();// 清点出输入数据的行数
		int columns = fieldSettingList.size();
		short blackColor = HSSFColor.BLACK.index;

		HSSFSheet sheet = workBook.createSheet("page");// 创建工作表
		sheet.setDefaultRowHeightInPoints(20);

		HSSFFont titleFont = workBook.createFont();
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //微软雅黑
		titleFont.setFontHeightInPoints((short)14);
		titleFont.setFontName("微软雅黑");
		titleFont.setColor(blackColor);

		HSSFCellStyle titleCellStyle = workBook.createCellStyle();
		titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		titleCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		titleCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		titleCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		titleCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		titleCellStyle.setFont(titleFont);

		HSSFFont headerFont = workBook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //微软雅黑
		headerFont.setFontHeightInPoints((short)11);
		headerFont.setFontName("微软雅黑");
		headerFont.setColor(blackColor);

		HSSFCellStyle headerCellStyle = workBook.createCellStyle();
		headerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		headerCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		headerCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		headerCellStyle.setFont(headerFont);

		HSSFFont dataFont = workBook.createFont();
//		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //微软雅黑
		dataFont.setFontHeightInPoints((short)8);
		dataFont.setFontName("微软雅黑");
		dataFont.setColor(blackColor);

		HSSFCellStyle dataCellStyle = workBook.createCellStyle();
		dataCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		dataCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		dataCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		dataCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		dataCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		dataCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		dataCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		HSSFDataFormat format = workBook.createDataFormat();
		dataCellStyle.setDataFormat(format.getFormat("@"));
		dataCellStyle.setFont(dataFont);

		HSSFCellStyle timeCellStyle = workBook.createCellStyle();
		timeCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		timeCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		timeCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		timeCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		timeCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		timeCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		timeCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		HSSFDataFormat timeFormat = workBook.createDataFormat();
		timeCellStyle.setDataFormat(timeFormat.getFormat("yyyy年m月d日"));
		timeCellStyle.setFont(dataFont);

		HSSFCellStyle floatCellStyle = workBook.createCellStyle();
		floatCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		floatCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		floatCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		floatCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		floatCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		floatCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		floatCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		floatCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		floatCellStyle.setFont(dataFont);

		HSSFCellStyle intCellStyle = workBook.createCellStyle();
		intCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		intCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		intCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		intCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		intCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		intCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		intCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		intCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
		intCellStyle.setFont(dataFont);

//		HSSFRow titleRow = sheet.createRow(0); // 创建第一栏，标题栏
//		HSSFCell titleCell = titleRow.createCell(0);
//
//		titleRow.setHeightInPoints(100);//设置行高
//		titleCell.setCellStyle(titleCellStyle);
//		titleCell.setCellType(HSSFCell.CELL_TYPE_STRING);
//		titleCell.setCellValue(new HSSFRichTextString(title));

//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns-1));
//		HSSFRow headRow = sheet.createRow(1); // 创建第一栏，抬头栏
//		titleRow.setHeightInPoints(30);//设置行高
		HSSFPatriarch p=sheet.createDrawingPatriarch();

//		HSSFWorkbook workBook = new HSSFWorkbook();// 创建一个工作簿
//		int rows = fieldDatasList.size();// 清点出输入数据的行数
//		int columns = fieldSettingList.size();
//		HSSFSheet sheet = workBook.createSheet("Page");// 创建工作表
		HSSFRow headRow = sheet.createRow(0); // 创建第一栏，抬头栏
		for (int j = 0; j < columns; j++) {
			HSSFCell cell = headRow.createCell(j);// 创建抬头栏单元格
			// 设置单元格格式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(headerCellStyle);
			sheet.setColumnWidth(j, 6000);
			// 将数据填入单元格
			Object[] field = fieldSettingList.get(j);
			if (field != null) {
				cell.setCellValue(field[1].toString());
			} else {
				cell.setCellValue("-");
			}
			if(field.length > 2 && field[2] != null && StringUtils.isNotBlank(field[2].toString())){
				HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
				//输入批注信息
				comment.setString(new HSSFRichTextString(field[2].toString()));
				//添加作者,选中B5单元格,看状态栏
//			  comment.setAuthor("toad");
				//将批注添加到单元格对象中
				cell.setCellComment(comment);
			}
		}
		// 创建数据栏单元格并填入数据

		for (int k = 0; k < rows; k++) {
			HSSFRow row = sheet.createRow(k + 1);
			Object o = fieldDatasList.get(k);
			if(o == null){
				logger.error("Error data: rows " + k);
				continue;
			}
			for (int n = 0; n < columns; n++) {
				HSSFCell cell = row.createCell(n);
				cell.setCellStyle(dataCellStyle);
				Method m = (Method)fieldSettingList.get(n)[0];
				if(m == null){
					logger.error("Error data: colums " + n);
					//throw new Exception("Error data.");
				}
				Object value = null;
				try {
					value = m.invoke(o);
				} catch (IllegalAccessException e) {
					logger.error("ExcelFileUtil.exportExcel IllegalAccessException:", e);
				} catch (IllegalArgumentException e) {
					logger.error("ExcelFileUtil.exportExcel IllegalArgumentException:", e);
				} catch (InvocationTargetException e) {
					logger.error("ExcelFileUtil.exportExcel InvocationTargetException:", e);
				}
				String classType = m.getGenericReturnType().toString();
				String v = null;
				if(value != null){
					v = value.toString();
				}
				if (StringUtils.isNotBlank(v)) {
					if("Integer".equals(classType) || "int".equals(classType)){
						cell.setCellStyle(intCellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Integer.parseInt(v));
					}else if("Double".equals(classType) || "double".equals(classType)){
						cell.setCellStyle(intCellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.parseDouble(v));
					}else if("Float".equals(classType) || "float".equals(classType)){
						cell.setCellStyle(intCellStyle);
						cell.setCellStyle(floatCellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Float.parseFloat(v));
					}else{
						if(StringUtils.isNumeric(v)){
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//							cell.setCellStyle(cellStringStyle);
							if(v.length()>10){
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								cell.setCellValue(new HSSFRichTextString(v));
							}else if(v.indexOf(".") == -1){
								cell.setCellValue(Long.parseLong(v));
							}else{
								cell.setCellValue(Double.parseDouble(v));
							}

						}else{
							if(classType.indexOf("java.util.Date") != -1) {

								v = DateUtils.format2DefaultFullTime((Date)value);
//								logger.debug( value + " >>>>> " + v);
								cell.setCellStyle(timeCellStyle);
							}else{
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							}

							if("Boolean".equals(classType) || "boolean".equals(classType)){
								cell.setCellValue(new HSSFRichTextString((Boolean)value? "是":"否"));
							}else{
								cell.setCellValue(new HSSFRichTextString(v));
							}
						}
					}

				} else {
					cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					cell.setCellValue("");
				}
			}
		}

//		for (int j = 0; j < columns; j++) {
//			 sheet.autoSizeColumn(j);
//		}
		return workBook;
	}

	public static <T> void exportExcel(String filePath, String title, List<Object[]> fieldSettingList, List<T> fieldDatasList, boolean withTitle) throws Exception {

		File file = new File(filePath);
		File parentFile = file.getParentFile();
		if(!parentFile.exists()){
			parentFile.mkdirs();
		}

		HSSFWorkbook workBook = getHssfWorkbook(title, fieldSettingList, fieldDatasList, withTitle);
		
		try {
			OutputStream os = new FileOutputStream(filePath);
			workBook.write(os);
			os.close();
		} catch (Exception e) {
			logger.error("ExcelFileUtil.exportExcel write Exception:" + filePath, e);
			throw new Exception("Export file error: filePath--> " + filePath);
		}
	}

	public static <T> byte[] getExcelFileByte(String title, List<Object[]> fieldSettingList, List<T> fieldDatasList, boolean withTitle) throws Exception {

		HSSFWorkbook workBook = getHssfWorkbook(title, fieldSettingList, fieldDatasList, withTitle);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workBook.write(bos);
		} catch (Exception e) {
			logger.error("ExcelFileUtil.exportExcel write Exception:", e);
			throw new Exception("write excel file data error!", e);
		} finally {
			bos.close();
		}
		byte[] bytes = bos.toByteArray();

		return bytes;
	}

	private static <T> HSSFWorkbook getHssfWorkbook(String title, List<Object[]> fieldSettingList, List<T> fieldDatasList, boolean withTitle) {
		int headerIndex = 0;
		HSSFWorkbook workBook = new HSSFWorkbook();// 创建一个工作簿
		int rows = fieldDatasList.size();// 清点出输入数据的行数
		int columns = fieldSettingList.size();
		short blackColor = HSSFColor.BLACK.index;

		String sheetSubject = title;
		if(title != null && title.length() > 20){
			if(title.indexOf(" ") > 0){
				sheetSubject = title.substring(0, title.indexOf(" "));
			}else{
				sheetSubject = title.substring(0, 20);
			}
		}
		HSSFSheet sheet = workBook.createSheet(sheetSubject);// 创建工作表
		sheet.setDefaultRowHeightInPoints(20);

		HSSFFont titleFont = workBook.createFont();
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //微软雅黑
		titleFont.setFontHeightInPoints((short)14);
		titleFont.setFontName("微软雅黑");
		titleFont.setColor(blackColor);

		HSSFCellStyle titleCellStyle = workBook.createCellStyle();
		titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		titleCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		titleCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		titleCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		titleCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		titleCellStyle.setFont(titleFont);
		HSSFFont headerFont = workBook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //微软雅黑
		headerFont.setFontHeightInPoints((short)11);
		headerFont.setFontName("微软雅黑");
		headerFont.setColor(blackColor);

		HSSFCellStyle headerCellStyle = workBook.createCellStyle();
		headerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		headerCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
		headerCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		headerCellStyle.setFont(headerFont);

		HSSFFont dataFont = workBook.createFont();
//		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //微软雅黑
		dataFont.setFontHeightInPoints((short)8);
		dataFont.setFontName("微软雅黑");
		dataFont.setColor(blackColor);

		HSSFCellStyle dataCellStyle = workBook.createCellStyle();
		dataCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		dataCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		dataCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		dataCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		dataCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		dataCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		dataCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		HSSFDataFormat format = workBook.createDataFormat();
		dataCellStyle.setDataFormat(format.getFormat("@"));
		dataCellStyle.setFont(dataFont);

		HSSFCellStyle timeCellStyle = workBook.createCellStyle();
		timeCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		timeCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		timeCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		timeCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		timeCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		timeCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		timeCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		HSSFDataFormat timeFormat = workBook.createDataFormat();
		timeCellStyle.setDataFormat(timeFormat.getFormat("yyyy-m-d h:mm:ss"));
		timeCellStyle.setFont(dataFont);

		HSSFCellStyle floatCellStyle = workBook.createCellStyle();
		floatCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		floatCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		floatCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		floatCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		floatCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		floatCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		floatCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		floatCellStyle.setDataFormat(timeFormat.getFormat("0.00"));//HSSFDataFormat.getBuiltinFormat("0.00")
		floatCellStyle.setFont(dataFont);

		HSSFCellStyle intCellStyle = workBook.createCellStyle();
		intCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平方向的对齐方式
		intCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直方向的对齐方式
		intCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		intCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		intCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		intCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		intCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		intCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
		intCellStyle.setFont(dataFont);

		if(withTitle) {
			HSSFRow titleRow = sheet.createRow(0); // 创建第一栏，标题栏
			HSSFCell titleCell = titleRow.createCell(0);

			titleRow.setHeightInPoints(100);//设置行高
			titleCell.setCellStyle(titleCellStyle);
			titleCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			titleCell.setCellValue(new HSSFRichTextString(title));

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns - 1));
			headerIndex = 1;
			titleRow.setHeightInPoints(30);//设置行高
		}

		HSSFRow headRow = sheet.createRow(headerIndex); // 创建第一栏，抬头栏
		HSSFPatriarch p=sheet.createDrawingPatriarch();
		for (int j = 0; j < columns; j++) {
			HSSFCell cell = headRow.createCell(j);// 创建抬头栏单元格
			// 设置单元格格式
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(headerCellStyle);
			sheet.setColumnWidth(j, 6000);
			// 将数据填入单元格
			Object[] field = fieldSettingList.get(j);
			if (field != null) {
				cell.setCellValue(field[1].toString());
			} else {
				cell.setCellValue("-");
			}
			if(field.length > 2 && field[2] != null && StringUtils.isNotBlank(field[2].toString())){
			  HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
			  //输入批注信息
			  comment.setString(new HSSFRichTextString(field[2].toString()));
			  //添加作者,选中B5单元格,看状态栏
//			  comment.setAuthor("toad");
			  //将批注添加到单元格对象中
			  cell.setCellComment(comment);
			}
		}
		// 创建数据栏单元格并填入数据

		for (int k = 0; k < rows; k++) {
			HSSFRow row = sheet.createRow(k + 1 + headerIndex);
			Object o = fieldDatasList.get(k);
			if(o == null){
				logger.error("Error data: rows " + k);
				continue;
			}
			for (int n = 0; n < columns; n++) {
				HSSFCell cell = row.createCell(n);
				cell.setCellStyle(dataCellStyle);
				Method m = (Method)fieldSettingList.get(n)[0];
				if(m == null){
					logger.error("Error data: colums " + n + "; field name: " + (fieldSettingList.get(n).length>1? fieldSettingList.get(n)[1]:""));
					throw new RuntimeException("Error data: colums " + n + "; " + m.getName());
				}
				Object value = null;
				try {
					value = m.invoke(o);
				} catch (IllegalAccessException e) {
					logger.error("ExcelFileUtil.exportExcel IllegalAccessException:", e);
				} catch (IllegalArgumentException e) {
					logger.error("ExcelFileUtil.exportExcel IllegalArgumentException:", e);
				} catch (InvocationTargetException e) {
					logger.error("ExcelFileUtil.exportExcel InvocationTargetException:", e);
				}
				String classType = m.getGenericReturnType().toString();
				String v = null;
				if(value != null){
					v = value.toString();
				}
				if("class java.math.BigDecimal".equals(classType)) {
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellStyle(floatCellStyle);

					if (!StringUtils.isNotBlank(v)) {
						v = "0";
					}
					if(v.indexOf(".") == -1){
						cell.setCellStyle(intCellStyle);
						cell.setCellValue(Long.parseLong(v));
					}else{
						cell.setCellStyle(floatCellStyle);
						cell.setCellValue(Double.parseDouble(v));
					}
				}else if (StringUtils.isNotBlank(v)) {
					if("Integer".equals(classType) || "int".equals(classType)){
						cell.setCellStyle(intCellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Integer.parseInt(v));
					}else if("Double".equals(classType) || "double".equals(classType)){
						cell.setCellStyle(floatCellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.parseDouble(v));
					}else if("Float".equals(classType) || "float".equals(classType)){
						cell.setCellStyle(floatCellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Float.parseFloat(v));

					}else{
						if(StringUtils.isNumeric(v)){
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							if(v.indexOf(".") == -1){
								cell.setCellStyle(intCellStyle);
								cell.setCellValue(Long.parseLong(v));
							}else{
								cell.setCellStyle(floatCellStyle);
								cell.setCellValue(Double.parseDouble(v));
							}

						}else{
							if(classType.indexOf("java.util.Date") != -1) {
								v = DateUtils.format2DefaultFullTime((Date)value);
//								logger.debug( value + " >>>>> " + v);
								cell.setCellStyle(timeCellStyle);
							}
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							if("Boolean".equals(classType) || "boolean".equals(classType)){
								cell.setCellValue(new HSSFRichTextString((Boolean)value? "是":"否"));
							}else{
								cell.setCellValue(new HSSFRichTextString(v));
							}
						}
					}

				} else {
					cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					cell.setCellValue("");
				}
			}
		}

//		for (int j = 0; j < columns; j++) {
//			 sheet.autoSizeColumn(j);
//		}
		return workBook;
	}

	public static <T> void exportCSV(String csvFilePath, List<T> productList, List<Object[]> methodObjs, String charset) {

		try {
			byte[] bom ={(byte) 0xEF,(byte) 0xBB,(byte) 0xBF};
			File bcpFile = new File(csvFilePath);
			//boolean newFile = false;
			FileOutputStream bcpFileWriter = new FileOutputStream(bcpFile);
			if(charset.equalsIgnoreCase("utf-8")){
				bcpFileWriter.write(bom);
			}
			StringBuffer oneLine = new StringBuffer();
			for ( int i=0, n=methodObjs.size(); i<n; i++) {
				Object[] obj = methodObjs.get(i);
				oneLine.append(obj[1]);
				if(i + 1 < n) {
					oneLine.append(CSV_SEPARATOR);
				}
			}

//			byte[] tempb= oneLine.toString().getBytes("utf-8");
//			byte[] newtempb=new String(tempb,"utf-8").getBytes("gbk");
//			String gbkstr=new String(newtempb,"gbk");


			bcpFileWriter.write(oneLine.toString().getBytes(charset));
			bcpFileWriter.write("\n".getBytes());

			for (Object billDetail : productList) {
				oneLine = new StringBuffer();
//                oneLine.append(product.getId() <=0 ? "" : product.getId());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(product.getName().trim().length() == 0? "" : product.getName());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(product.getCostPrice() < 0 ? "" : product.getCostPrice());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(product.isVatApplicable() ? "Yes" : "No");


				for ( int i=0, n=methodObjs.size(); i<n; i++) {
					Object[] obj = methodObjs.get(i);
					Method m = (Method) obj[0];
					if (m == null) {
						logger.error("Error data: colums ");
						//throw new Exception("Error data.");
					}
					Object value = null;
					try {
						value = m.invoke(billDetail);
					} catch (IllegalAccessException e) {
						logger.error("ExcelFileUtil.exportExcel IllegalAccessException:", e);
					} catch (IllegalArgumentException e) {
						logger.error("ExcelFileUtil.exportExcel IllegalArgumentException:", e);
					} catch (InvocationTargetException e) {
						logger.error("ExcelFileUtil.exportExcel InvocationTargetException:", e);
					}
					String classType = m.getGenericReturnType().toString();
					String v = null;
					if (value != null) {
						v = value.toString();
						if (classType.indexOf("java.util.Date") != -1) {
							v = DateUtils.format2DefaultFullTime((Date) value);
//							logger.debug(value + " >>>>> " + v);
						} else if ("Boolean".equals(classType) || "boolean".equals(classType)) {
							v = ((Boolean) value ? "是" : "否");
						}
					} else {
						v = "";
					}
					oneLine.append(v);
					if(i + 1 < n) {
						oneLine.append(CSV_SEPARATOR);
					}
				}
				bcpFileWriter.write(oneLine.toString().getBytes(charset));
//				bcpFileWriter.write((new String(oneLine.toString().getBytes("utf-8"), charset)).getBytes());
				bcpFileWriter.write("\n".getBytes());
			}
			bcpFileWriter.close();
		} catch (UnsupportedEncodingException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
}