/**
 * 
 */
package hust.smartpark.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.smartpark.dao.CarInfoDao;
import hust.smartpark.dto.DtoCar;
import hust.smartpark.dto.DtoKeyCarPercent;
import hust.smartpark.entity.CarInfo;
import hust.smartpark.entity.CommonMap;
import hust.smartpark.service.CarInfoService;
import hust.smartpark.util.MyUtils;

/**
 * @author 华中科技大学 王坤 （377816164@qq.com）
 *
 */
@Service
public class CarInfoServiceImpl implements CarInfoService {

	@Autowired
	private CarInfoDao carInfoDao;
	public List<DtoCar> selectAllCars() {
		List<CarInfo> carInfos=carInfoDao.selectAllCars();		
		return MyUtils.CarInfoToDtoCar(carInfos);
	}
	
	public DtoKeyCarPercent selectKeycarpercent() {
		int keyCarCount=carInfoDao.selectKeyCarCount();
		int allCarCount=carInfoDao.selectAllCarCount();
		DtoKeyCarPercent dtoKeyCarPercent = new DtoKeyCarPercent();
		dtoKeyCarPercent.setKeyCarCount(keyCarCount+"");
		dtoKeyCarPercent.setAllCarCount(allCarCount+"");
		return dtoKeyCarPercent;
	}

	public List<DtoCar> selectKeycars() {
		List<CarInfo> carInfos=carInfoDao.selectKeyCars();		
		return MyUtils.CarInfoToDtoCar(carInfos);
	}

	public void addKeycar(String carNum) {
		carInfoDao.addKeycar(carNum);		
	}

	public void delKeycar(String carNum) {
		carInfoDao.delKeycar(carNum);		
	}

	public List<CommonMap> selectCarPerProvince() {
		List<CommonMap> list=carInfoDao.selectCarPerProvince();
		return MyUtils.clearProvince(list);
	}
	
	public List<CommonMap> selectCarPerBlock() {
		List<CommonMap> list=carInfoDao.selectCarPerBlock();
		return MyUtils.clearBlock(list);
	}

	public List<DtoCar> selectBlockCarDetail(String dh) {
		List<CarInfo> carInfos=carInfoDao.selectBlockCars(dh+"%");
		return MyUtils.CarInfoToDtoCar(carInfos);
	}

	public String isKeyCar(String carNum) {
		
		String string=carInfoDao.selectRemark(carNum);
		if ("1".equals(string)) {
			return string;
		}else {
			return "0";
		} 
	}

	public List<DtoCar> selectDayCarDetail(String day) {
		String timeStart=day+" 00:00:00";
		String timeEnd=day+" 24:00:00";
		List<CarInfo> carInfos=carInfoDao.selectDayCars(timeStart,timeEnd);
		return MyUtils.CarInfoToDtoCar(carInfos);
	}

	public List<String> selectAllCarNum() {
		
		return carInfoDao.selectAllCarNum();
	}

	public List<DtoCar> selectProvenceCarDetail(String provence) {
		List<CarInfo> carInfos=carInfoDao.selectProvenceCars(provence+"%");
		return MyUtils.CarInfoToDtoCar(carInfos);
	}
	//插入car,逐个插入，若car_num存在则更新
	public int insertBatch(List<CarInfo> carlist) {
		for (CarInfo carInfo : carlist) {
			//过滤车牌号异常
			if (carInfo.getCarNum()==null) continue;
			if ("临时".equals(carInfo.getCarNum())||carInfo.getCarNum().length()==7) {
				//先尝试插入，若发生异常，则证明主键冲突，采用更新
				try {
					carInfoDao.insert(carInfo);
				} catch (Exception e) {
					carInfoDao.update(carInfo);
				}
			}else {
				continue;
			}
		}
		//return carInfoDao.insertBatch(carlist);
		return 1;
	}

	public String selectAllCount() {
		// TODO Auto-generated method stub
		return carInfoDao.selectAllCarCount()+"";
	}

}
