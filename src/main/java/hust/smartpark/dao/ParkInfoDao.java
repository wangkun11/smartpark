package hust.smartpark.dao;

import hust.smartpark.dto.DtoPark;
import hust.smartpark.entity.CommonMap;
import hust.smartpark.entity.CountByDays;
import hust.smartpark.entity.ParkInfo;

import java.util.List;

public interface ParkInfoDao {

	// <!-- 每辆车的停车频次 -->
	List<CommonMap> selectCountBycar(String timeStart, String timeEnd,
			String parkName);

	//  <!-- 每天的停车频次 -->
	List<CountByDays> selectCountByDay(String timeStart, String timeEnd,
			String parkName);

	//  <!-- 时间段的所有停车记录 -->
	List<DtoPark> selectParkInfoByTime(String dateStart, String dateEnd,String timeStart,String timeEnd,
			String parkName);

	// <!-- 一辆车的所有停车记录 -->
	List<DtoPark> selectParkTimeByCar(String carNum);

	//<!-- 插入一条停车记录 -->
	int insert(DtoPark record);
	
	//<!-- 今天所有停车记录 -->
	List<DtoPark> selectToDay(String timeStart, String timeEnd);
	
	//<!-- 今天所有重点车辆停车记录（in语句） -->
	List<DtoPark> selectToDayKeyCars(String timeStart, String timeEnd,
			List<String> carNums);
	//<!-- 批量插入停车记录 -->
	int insertBatch(List<DtoPark> dtoParks);

	int selectAllCount();

}