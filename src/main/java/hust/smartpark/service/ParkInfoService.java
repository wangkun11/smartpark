package hust.smartpark.service;

import hust.smartpark.dto.DtoCar;
import hust.smartpark.dto.DtoPark;
import hust.smartpark.dto.ParkFrequency;
import hust.smartpark.dto.ParkTimeAndType;
import hust.smartpark.entity.CommonMap;
import hust.smartpark.entity.CountByDays;
import hust.smartpark.entity.ParkInfo;

import java.util.List;

public interface ParkInfoService {

	// 标签2
	List<DtoPark> selectByTime(String startDay, String endDay, String timeSlot,
			String parkName);

	// 标签3
	List<ParkFrequency> selectFreByTime(String startDay, String endDay,
			String parkName);

	// 标签4
	List<CountByDays> selectCountByDays(String startDay, String endDay,
			String parkName);

	// 标签4
	List<CommonMap> selectCountBycar(String startDay, String endDay,
			String parkName);

	// 标签3：详细信息
	List<DtoCar> selectSepcialFreDetail(String startDay, String endDay,
			String fre, String parkName);

	List<ParkTimeAndType> selectParkTimeAndType(String carNum, String parkName);

	int insert(DtoPark record);

	List<DtoPark> selectToDay();

	List<DtoPark> selectToDayKeyCars(List<String> carNums);

	int insertBatch(List<DtoPark> dtoParks);

	String selectAllCount();
}