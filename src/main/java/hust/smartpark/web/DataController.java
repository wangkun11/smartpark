/**
 * 
 */
package hust.smartpark.web;

import hust.smartpark.annotation.SystemControllerLog;
import hust.smartpark.dto.DtoPark;
import hust.smartpark.entity.CarInfo;
import hust.smartpark.service.CarInfoService;
import hust.smartpark.service.ParkInfoService;
import hust.smartpark.util.MyUtils;

import java.io.File;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 华中科技大学 王坤 （377816164@qq.com）
 *
 */
@Controller
public class DataController {
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this
			.getClass());
	@Autowired
	private ParkInfoService parkInfoService;
	@Autowired
	private CarInfoService carInfoService;

	// 1、解析Excel表格parks,并插入数据库
	@SystemControllerLog(description = "从Excel表中导入停车信息进入数据库")
	@ResponseBody
	@RequestMapping(value = "/parkexceltodb")
	public int parkexceltodb(
			@RequestParam(value = "path", defaultValue = "D:\\用户目录\\Desktop\\停车场项目\\park_info.xls") String path) {
		File file = new File(path);
		List<DtoPark> dtoParks = MyUtils.parkExcelToDb(file);
		int x = parkInfoService.insertBatch(dtoParks);
		System.out.println(x);
		return x;
	}

	// 2、解析Excel表格cars,并插入数据库
	@SystemControllerLog(description = "从Excel表中导入车主信息进入数据库")
	@ResponseBody
	@RequestMapping(value = "/carexceltodb")
	public int carexceltodb(
			@RequestParam(value = "path", defaultValue = "D:\\用户目录\\Desktop\\停车场项目\\car_info.xls") String path) {
		File file = new File(path);
		List<CarInfo> carInfos = MyUtils.carExcelToDb(file);
		int x = carInfoService.insertBatch(carInfos);
		System.out.println(x);
		return x;
	}

	// 3、插入停车数据
	@SystemControllerLog(description = "插入停车数据")
	@ResponseBody
	@RequestMapping(value = "/insertparks", method = RequestMethod.POST)
	public int insertparks(@RequestBody List<DtoPark> parklist) {
		if (parklist != null) {
			for (DtoPark dtoPark : parklist) {
				logger.debug(dtoPark.toString());
			}
			int x = parkInfoService.insertBatch(parklist);
			return x;
		} else {
			return 0;
		}
	}

	// 4、插入车主数据
	@SystemControllerLog(description = "插入车主数据")
	@ResponseBody
	@RequestMapping(value = "/insertcars", method = RequestMethod.POST)
	public int insertcars(@RequestBody List<CarInfo> carlist) {
		if (carlist != null) {
			for (CarInfo carInfo : carlist) {
				logger.debug(carInfo.toString());
			}
			int x = carInfoService.insertBatch(carlist);
			return x;
		} else {
			return 0;
		}
	}
}
