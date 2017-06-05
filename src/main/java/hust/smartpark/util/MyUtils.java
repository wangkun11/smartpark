/**
 * 
 */
package hust.smartpark.util;

import hust.smartpark.dto.DtoCar;
import hust.smartpark.dto.DtoPark;
import hust.smartpark.entity.CarInfo;
import hust.smartpark.entity.CommonMap;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author 华中科技大学 王坤 （377816164@qq.com）
 *
 */
public class MyUtils {

	/**
	 * @param carInfos
	 * @return
	 */
	public static List<DtoCar> CarInfoToDtoCar(List<CarInfo> carInfos) {
		List<DtoCar> dtoCars = new ArrayList<DtoCar>();
		for (CarInfo carInfo : carInfos) {
			DtoCar dtoCar = new DtoCar();
			dtoCar.setCarNum(carInfo.getCarNum());
			dtoCar.setTel(carInfo.getTel());

			if (carInfo.getRemark() == null
					|| !("1".equals(carInfo.getRemark().trim()))) {
				dtoCar.setIskeyCar("否");
			} else {
				dtoCar.setIskeyCar("是");
			}
			// System.out.println(dtoCar.getIskeyCar());
			dtoCar.setBlock("理想城");
			String string = carInfo.getRoomNum();
			if (string == null) {
				continue;
			}
			String[] temps = string.split("-");
			String dh = "";
			String dyh = "";
			String fjh = "";
			if (temps.length == 3) {
				dh = temps[0];
				dyh = temps[1];
				fjh = temps[2];
			} else if (temps.length == 2) {
				dh = temps[0];
				dyh = temps[1].substring(0, 1);
				fjh = temps[1].substring(1);
			} else if (temps.length == 1) {
				fjh = string;
			}
			dtoCar.setDh(dh);
			dtoCar.setDyh(dyh);
			dtoCar.setFjh(fjh);
			dtoCars.add(dtoCar);
		}
		return dtoCars;
	}

	/**
	 * @param list
	 * @return
	 */
	public static List<CommonMap> clearProvince(List<CommonMap> list) {
		List<CommonMap> list1 = new ArrayList<CommonMap>();
		for (CommonMap commonMap : list) {
			CommonMap commonMap2 = new CommonMap();
			commonMap2.setValue2(commonMap.getValue2());
			if ("".equals(commonMap.getValue1())
					|| "临".equals(commonMap.getValue1())
					|| "2".equals(commonMap.getValue1())) {
				continue;
			} else {
				commonMap2.setValue1(commonMap.getValue1());
				list1.add(commonMap2);
			}
		}
		return list1;
	}

	/**
	 * @param list
	 * @return
	 */
	public static List<CommonMap> clearBlock(List<CommonMap> list) {
		List<CommonMap> list1 = new ArrayList<CommonMap>();
		Set<String> set = new HashSet<String>();
		set.add("1");
		set.add("2");
		set.add("3");
		set.add("4");
		set.add("5");
		set.add("6");
		set.add("7");
		set.add("8");
		set.add("9");
		for (CommonMap commonMap : list) {
			if (set.contains(commonMap.getValue1())) {
				list1.add(commonMap);
			}
		}
		return list1;
	}

	// 解析Excel表格
	public static List<DtoPark> parkExcelToDb(File file) {
		List<DtoPark> dtoParks = new ArrayList<DtoPark>();
		try {
			// 创建Excel，读取文件内容
			HSSFWorkbook workbook = new HSSFWorkbook(
					FileUtils.openInputStream(file));
			// 获取第一个工作表workbook.getSheet("Sheet0");
			// 读取默认第一个工作表sheet
			HSSFSheet sheet = workbook.getSheetAt(0);
			int firstRowNum = 0 + 0;
			// 获取sheet中最后一行行号:行数
			int lastRowNum = sheet.getLastRowNum();
			// 获取列数(字段个数)
			int lastCellNum = sheet.getRow(0).getLastCellNum();
			for (int i = firstRowNum; i <= lastRowNum; i++) {
				HSSFRow row = sheet.getRow(i);
				if (row == null)
					continue;

				HSSFCell[] cells = new HSSFCell[lastCellNum];
				for (int j = 0; j < lastCellNum; j++) {
					if (row.getCell(j) == null) {
						cells[j] = null;
					} else {
						cells[j] = row.getCell(j);
					}
				}

				if ("".equals(getCellValue(cells[2]).trim())
						|| getCellValue(cells[2]) == "  "
						|| getCellValue(cells[2]) == null)
					continue;
				DtoPark dtoPark = new DtoPark();
				dtoPark.setCarNum(getCellValue(cells[1]));
				dtoPark.setTime(getCellValue(cells[5]));
				dtoPark.setType("出口");
				dtoPark.setParkName("理想城停车场");
				dtoParks.add(dtoPark);

			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return dtoParks;
	}

	private static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
						.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {// 日期
					sdf = new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");
				}
				Date date = cell.getDateCellValue();
				cellValue = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy/MM/dd HH:MM:ss");
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil
						.getJavaDate(value);
				cellValue = sdf.format(date);
			} else {
				double value = cell.getNumericCellValue();
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				// 单元格设置成常规
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				cellValue = format.format(value);
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			break;
		default:
			cellValue = "  ";
		}
		// System.out.println(cellValue);
		return cellValue;
	}
	/**
	 * @return
	 */
	public static String creatUUID() {		
		return  UUID.randomUUID().toString();
	}

	/**
	 * @param logId
	 * @return
	 */
	public static boolean isBlank(String logId) {
		if (logId==null) {
			return true;
		}
		return false;
	}

	/**
	 * @param file
	 * @return
	 */
	public static List<CarInfo> carExcelToDb(File file) {
		List<CarInfo> carInfos = new ArrayList<CarInfo>();
		try {
			// 创建Excel，读取文件内容
			HSSFWorkbook workbook = new HSSFWorkbook(
					FileUtils.openInputStream(file));
			// 获取第一个工作表workbook.getSheet("Sheet0");
			// 读取默认第一个工作表sheet
			HSSFSheet sheet = workbook.getSheetAt(0);
			int firstRowNum = 0 + 0;
			// 获取sheet中最后一行行号:行数
			int lastRowNum = sheet.getLastRowNum();
			System.out.println("lastRowNum="+lastRowNum);
			// 获取列数(字段个数)
			int lastCellNum = sheet.getRow(0).getLastCellNum();
			for (int i = firstRowNum; i <= lastRowNum; i++) {
				HSSFRow row = sheet.getRow(i);
				if (row == null)
					continue;

				HSSFCell[] cells = new HSSFCell[lastCellNum];
				for (int j = 0; j < lastCellNum; j++) {
					if (row.getCell(j) == null) {
						cells[j] = null;
					} else {
						cells[j] = row.getCell(j);
					}
				}

				if ("".equals(getCellValue(cells[5]).trim())
						|| getCellValue(cells[5]) == "  "
						|| getCellValue(cells[5]) == null)
					continue;
				CarInfo carInfo=new CarInfo();
				carInfo.setRoomNum(getCellValue(cells[1]));
				carInfo.setTel(getCellValue(cells[2]));
				carInfo.setRemark(getCellValue(cells[3]));
				carInfo.setReceiptNum(getCellValue(cells[4]));
				carInfo.setCarNum(getCellValue(cells[5]));
				carInfos.add(carInfo);

			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return carInfos;
	}

}
