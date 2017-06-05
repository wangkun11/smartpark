package hust.smartpark.service;

import java.util.List;

import hust.smartpark.dto.DtoCar;
import hust.smartpark.dto.DtoKeyCarPercent;
import hust.smartpark.dto.ParkTimeAndType;
import hust.smartpark.entity.CarInfo;
import hust.smartpark.entity.CommonMap;

public interface CarInfoService {
	
	List<DtoCar> selectAllCars();

	DtoKeyCarPercent selectKeycarpercent();

	List<DtoCar> selectKeycars();

	void addKeycar(String carNum);

	void delKeycar(String carNum);

	List<CommonMap> selectCarPerProvince();

	List<CommonMap> selectCarPerBlock();

	List<DtoCar> selectBlockCarDetail(String dh);

	String isKeyCar(String carNum);

	List<DtoCar> selectDayCarDetail(String day);

	List<String> selectAllCarNum();

	List<DtoCar> selectProvenceCarDetail(String provence);

	int insertBatch(List<CarInfo> carlist);

	String selectAllCount();
}