package hust.smartpark.web;

import hust.smartpark.annotation.SystemControllerLog;
import hust.smartpark.dto.DtoCar;
import hust.smartpark.dto.DtoKeyCarPercent;
import hust.smartpark.dto.DtoPark;
import hust.smartpark.dto.ParkFrequency;
import hust.smartpark.dto.ParkTimeAndType;
import hust.smartpark.entity.CommonMap;
import hust.smartpark.entity.CountByDays;
import hust.smartpark.service.CarInfoService;
import hust.smartpark.service.ParkInfoService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lisa on 2016/8/30.
 */
@Controller
// @Service @Compent
public class MainController {
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this
			.getClass());
	@Autowired
	private ParkInfoService parkInfoService;
	@Autowired
	private CarInfoService carInfoService;

	// 1、车辆
	// 1。1 所有车辆信息
	@SystemControllerLog(description="查询所有车辆")
	@ResponseBody
	@RequestMapping(value = "/allcars")
	public List<DtoCar> selectallcars() {
		List<DtoCar> list = carInfoService.selectAllCars();
		return list;
	}

	// 1。2 重点车辆比例
	@SystemControllerLog(description="查询重点车辆比例")
	@ResponseBody
	@RequestMapping(value = "/keycarpercent")
	public DtoKeyCarPercent keycarpercent() {
		DtoKeyCarPercent dtoKeyCarPercent = carInfoService
				.selectKeycarpercent();
		return dtoKeyCarPercent;
	}

	// 1.3 重点车辆信息
	@SystemControllerLog(description="查询重点车辆详细信息")
	@ResponseBody
	@RequestMapping(value = "/keycardetail")
	public List<DtoCar> keycardetail() {
		List<DtoCar> list = carInfoService.selectKeycars();
		return list;
	}

	// 1.4 设为重点车辆
	@SystemControllerLog(description="设置为重点车辆")
	@ResponseBody
	@RequestMapping(value = "/addkeycar")
	public String addkeycar(
			@RequestParam(value = "carNum", defaultValue = "鄂A92D3D") String carNum) {
		carInfoService.addKeycar(carNum);
		return "1";
	}

	// 1.5 取消重点车辆标记
	@SystemControllerLog(description="取消重点车辆")
	@ResponseBody
	@RequestMapping(value = "/delkeycar")
	public String delkeycar(
			@RequestParam(value = "carNum", defaultValue = "鄂A92D3D") String carNum) {
		carInfoService.delKeycar(carNum);
		return "1";
	}

	// 2 特殊时段
	@SystemControllerLog(description="查询特殊时段的停车信息")
	@RequestMapping(value = "/specialtime")
	@ResponseBody
	public List<DtoPark> specialtime(
			@RequestParam(value = "startDay", defaultValue = "2017-03-20") String startDay,
			@RequestParam(value = "endDay", defaultValue = "2017-03-22") String endDay,
			@RequestParam(value = "timeSlot", defaultValue = "08:00-10:00") String timeSlot,
			@RequestParam(value = "parkName", defaultValue = "理想城停车场") String parkName) {
		// timeStart="2017-03-29 22:39:51";
		// timeEnd="2017-03-30 22:39:51";
		System.out.println("startDay:" + startDay + "endDay:" + endDay
				+ ",timeSlot:" + timeSlot);
		List<DtoPark> parkList = parkInfoService.selectByTime(startDay,
				endDay, timeSlot, parkName);
		return parkList;

	}

	// 3 频度
	// 3.1 频度柱状图
	@SystemControllerLog(description="查询特殊频度车辆")
	@RequestMapping(value = "/sepcialfre")
	@ResponseBody
	public List<ParkFrequency> sepcialFrequencies(
			@RequestParam(value = "startDay", defaultValue = "2017-03-20") String startDay,
			@RequestParam(value = "endDay", defaultValue = "2017-04-22") String endDay,
			@RequestParam(value = "parkName", defaultValue = "理想城停车场") String parkName) {
		System.out.println("进入sepcialfrequencies");
		System.out.println(startDay + " " + endDay + " " + parkName);
		List<ParkFrequency> list = parkInfoService.selectFreByTime(startDay,
				endDay, parkName);
		return list;
	}

	// 3.2 大于某一频度的所有车辆的详细信息
	@SystemControllerLog(description="查询大于某一频度的车辆的信息")
	@RequestMapping(value = "/sepcialfredetail")
	@ResponseBody
	public List<DtoCar> sepcialFreDetail(
			@RequestParam(value = "startDay", defaultValue = "2017-04-15") String startDay,
			@RequestParam(value = "endDay", defaultValue = "2017-04-22") String endDay,
			@RequestParam(value = "fre", defaultValue = "10") String fre,
			@RequestParam(value = "parkName", defaultValue = "理想城停车场") String parkName) {
		System.out.println("进入sepcialfredetail");
		System.out
				.println(startDay + " " + endDay + " " + fre + " " + parkName);
		List<DtoCar> list = parkInfoService.selectSepcialFreDetail(startDay,
				endDay, fre, parkName);
		System.out.println(list.size());
		return list;
	}

	// 4 统计分析
	// 4.1 每天的停车量柱状图
	@SystemControllerLog(description="查询每天的停车量")
	@RequestMapping(value = "/prakcount")
	@ResponseBody
	public List<CountByDays> prakcount(
			@RequestParam(value = "startDay", defaultValue = "2017-03-12") String startDay,
			@RequestParam(value = "endDay", defaultValue = "2017-04-22") String endDay,
			@RequestParam(value = "parkName", defaultValue = "理想城停车场") String parkName) {
		System.out.println("进入sepcialfrequencies");
		System.out.println(startDay + " " + endDay + " " + parkName);
		List<CountByDays> list = parkInfoService.selectCountByDays(startDay,
				endDay, parkName);
		return list;
	}

	// 4.2 每个省份的车辆数
	@SystemControllerLog(description="查询每个省份的车辆数")
	@ResponseBody
	@RequestMapping(value = "/carperprovince")
	public List<CommonMap> carperprovince() {
		return carInfoService.selectCarPerProvince();
	}

	// 4.3 每一栋的车辆数
	@SystemControllerLog(description="查询每栋楼的车辆数")
	@ResponseBody
	@RequestMapping(value = "/carperblock")
	public List<CommonMap> carperblock() {
		return carInfoService.selectCarPerBlock();
	}

	// 4.4 每一栋的具体车辆
	@SystemControllerLog(description="查询每栋楼的具体车辆")
	@ResponseBody
	@RequestMapping(value = "/blockcardetail")
	public List<DtoCar> blockcardetail(
			@RequestParam(value = "dh", defaultValue = "1") String dh) {
		return carInfoService.selectBlockCarDetail(dh);
	}

	// 4.5 总车辆
	@SystemControllerLog(description="查询总车辆数")
	@ResponseBody
	@RequestMapping(value = "/allcarount")
	public String allcarCount() {
		return carInfoService.selectAllCount();
	}

	// 4.6 总出入量
	@SystemControllerLog(description="查询总出入量数")
	@ResponseBody
	@RequestMapping(value = "/allparkcount")
	public String allparkCount() {
		return parkInfoService.selectAllCount();
	}

	// 4.7 每天出入车辆的具体信息
	@SystemControllerLog(description="查询某一天的停车信息")
	@ResponseBody
	@RequestMapping(value = "/daycardetail")
	public List<DtoCar> daycardetail(
			@RequestParam(value = "day", defaultValue = "2017-03-22") String day) {
		return carInfoService.selectDayCarDetail(day);
	}
	// 4.8 每个省份的具体车辆
	@SystemControllerLog(description="查询每个省的具体车辆")
	@ResponseBody
	@RequestMapping(value = "/provencecardetail")
	public List<DtoCar> provencecardetail(
			@RequestParam(value = "provence", defaultValue = "鄂") String provence) {
		return carInfoService.selectProvenceCarDetail(provence);
	}
	
	// 5 通用接口：
	// 5.1、查询某辆车的所有进出时间
	@SystemControllerLog(description="查询某辆车的所有进出时间")
	@ResponseBody
	@RequestMapping(value = "/parktimeandtype")
	public List<ParkTimeAndType> selectParkTimeAndType(
			@RequestParam(value = "carNum", defaultValue = "鄂A92D3D") String carNum,
			@RequestParam(value = "parkName", defaultValue = "理想城停车场") String parkName) {
		System.out.println("进入parktimeandtype");
		System.out.println(carNum + " " + parkName);
		return parkInfoService.selectParkTimeAndType(carNum, parkName);
	}

	// 5.2、判断一辆车是否是重点车辆
	@SystemControllerLog(description="查询某辆车是否是重点车辆")
	@ResponseBody
	@RequestMapping(value = "/iskeycar")
	public String isKeyCar(
			@RequestParam(value = "carNum", defaultValue = "鄂AEV407") String carNum) {
		String iskeycar = carInfoService.isKeyCar(carNum);
		logger.info(carNum + " iskeycar:" + iskeycar);
		return iskeycar;
	}

	// 6、实时
	// 6.1 今天所有停车记录
	//@SystemControllerLog(description="查询今天的所有停车记录")
	@ResponseBody
	@RequestMapping(value = "/currentcar")
	public List<DtoPark> currentcar() {
		/*//1、模拟一条出入记录：
		List<String> carNums = carInfoService.selectAllCarNum();
		Random random = new Random();
		int x = random.nextInt(800);
		String carNum = carNums.get(x);
				
		DtoPark parkInfo = new DtoPark();
		parkInfo.setCarNum(carNum);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		parkInfo.setTime(df.format(new Date()));
		System.out.println(x);
		parkInfo.setType(x % 2 == 1 ? "入口" : "出口");
		parkInfo.setParkName("理想城停车场");
		
		//2、将这条记录插入数据库
		parkInfoService.insert(parkInfo);*/
		//3、查询今天的所有记录并返回
		List<DtoPark> list=parkInfoService.selectToDay();
		
		return list;
	}
	//6.2 查询今天的所有重点停车记录
	//@SystemControllerLog(description="查询今天的所有重点停车记录")
	@ResponseBody
	@RequestMapping(value = "/currentkeycar")
	public List<DtoPark> currentkeycar() {
				
		/*List<DtoPark> list=parkInfoCurrentService.selectToDay();
		List<DtoPark> list2=new ArrayList<DtoPark>();
		for (DtoPark parkInfoCurrent : list) {
			if ("1".equals(carInfoService.isKeyCar(parkInfoCurrent.getCarNum()))) {
				list2.add(parkInfoCurrent);
			}
		}*/
		List<DtoCar> list = carInfoService.selectKeycars();
		List<String> carNums=new ArrayList<String>();
		for (DtoCar dtoCar : list) {
			carNums.add(dtoCar.getCarNum());
		}
		List<DtoPark> list2 = parkInfoService.selectToDayKeyCars(carNums);
		return list2;
	}
}
