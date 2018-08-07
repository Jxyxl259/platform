package com.yaic.servicelayer.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.yaic.servicelayer.codec.binary.Base64Encoder;


/**
 * Excel 工具类
 *
 * @author yuanyaqi
 */
public class ExcelUtil
{
	/**
	 * 创建一个Excel对象
	 *
	 * @param titleArr
	 * @param dataList
	 * @param sheetName
	 * @return HSSFWorkbook
	 */
	public static HSSFWorkbook createExcel(final String[] titleArr, final List<String> dataList, final String sheetName)
	{
		if (ArrayUtil.isEmpty(titleArr) || CollectionUtil.isEmpty(dataList) || StringUtil.isEmpty(sheetName))
		{
			return null;
		}

		//创建一个workbook，对应一个Excel文件
		final HSSFWorkbook workbook = new HSSFWorkbook();
		//在workbook中添加一个sheet
		final HSSFSheet sheet = workbook.createSheet(sheetName);
		//创建表头并且设置单元格格式
		HSSFRow row = sheet.createRow(0);
		final HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		for (int i = 0, len = titleArr.length; i < len; i++)
		{
			createCell(row, cellStyle, titleArr[i], i);
		}

		//写入数据
		String dataStr;
		String[] dataArr;
		for (int i = 0, size = dataList.size(); i < size; i++)
		{
			dataStr = dataList.get(i);
			dataArr = dataStr.split("\\|");

			row = sheet.createRow(i + 1);
			for (int j = 0, len = dataArr.length; j < len; j++)
			{
				createCell(row, null, dataArr[j], j);
			}
		}

		return workbook;
	}

	/**
	 * 设置单元格
	 *
	 * @param row
	 * @param cellStyle
	 * @param cellValue
	 * @param cellNum
	 */
	public static void createCell(final HSSFRow row, final HSSFCellStyle cellStyle, final Object cellValue, final int cellNum)
	{
		final HSSFCell cell = row.createCell(cellNum);
		if (cellStyle != null)
		{
			cell.setCellStyle(cellStyle);
		}
		if (cellValue instanceof String)
		{
			cell.setCellValue((String) cellValue);
		}
		if (cellValue instanceof Double)
		{
			cell.setCellValue((Double) cellValue);
		}
		if (cellValue instanceof Date)
		{
			cell.setCellValue((Date) cellValue);
		}
	}

	/**
	 * 将Excel对象转换成base64字符串
	 *
	 * @param workbook
	 * @return base64字符串
	 * @throws IOException
	 */
	public static String toString(final HSSFWorkbook workbook) throws IOException
	{
		if (ObjectUtil.isNull(workbook))
		{
			return null;
		}
		return Base64Encoder.encode(toByte(workbook));
	}

	/**
	 * 将Excel对象转换成数组
	 *
	 * @param workbook
	 * @return 转换的数组
	 * @throws IOException
	 */
	public static byte[] toByte(final HSSFWorkbook workbook) throws IOException
	{
		if (ObjectUtil.isNull(workbook))
		{
			return null;
		}

		try (final ByteArrayOutputStream baos = new ByteArrayOutputStream())
		{
			workbook.write(baos);
			return baos.toByteArray();
		}
	}
}
