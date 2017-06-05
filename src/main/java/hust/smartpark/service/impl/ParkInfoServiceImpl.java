package hust.smartpark.service.impl;

import hust.smartpark.dao.CarInfoDao;
import hust.smartpark.dao.ParkInfoDao;
import hust.smartpark.dto.DtoCar;
import hust.smartpark.dto.DtoPark;
import hust.smartpark.dto.ParkFrequency;
import hust.smartpark.dto.ParkTimeAndType;
import hust.smartpark.entity.CarInfo;
import hust.smartpark.entity.CommonMap;
import hust.smartpark.entity.CountByDays;
import hust.smartpark.service.ParkInfoService;
import hust.smartpark.util.MyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkInfoServiceImpl implements ParkInfoService{
	@Autowired
	private ParkInfoDao parkInfoDao;
	@Autowired
	private CarInfoDao carInfoDao;
	
	public List<DtoPark> selectByTime(String startDay, String endDay,
			String timeSlot, String parkName) {
		//ymd=2017-03-20 time=12:00-14:00
		String timeStart=startDay+" "+timeSlot.split("-")[0]+":00";
		String timeEnd=endDay+" "+timeSlot.split("-")[1]+":00";
		List<DtoPark> list=parkInfoDao.selectParkInfoByTime(timeStart,timeEnd,parkName);
		/*//时间格式化
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		for (DtoPark dtoPark : list) {
			dtoPark.setTime(df.format(dtoPark.getTime()));			
		}*/
		return list;
	}
    public List<ParkFrequency> selectFreByTime(String startDay,String endDay,String parkName){
    	startDay+=" 00:00:00";
    	endDay+=" 24:00:00";
    	List<ParkFrequency> list=new ArrayList<ParkFrequency>();
    	for (int i = 0; i < 5; i++) {
			ParkFrequency parkFrequency=new ParkFrequency();
			parkFrequency.setFrequency((i+1)*10);
			list.add(parkFrequency);
		}
    	List<CommonMap> list2=parkInfoDao.selectCountBycar(startDay, endDay,parkName);    	
    	int x1=0;
    	int x2=0;
    	int x3=0;
    	int x4=0;
    	int x5=0;    	
    	for (CommonMap entry : list2) {
			if (entry.getValue2()>=50) {
				list.get(0).setCount(x1++);
				list.get(1).setCount(x2++);
				list.get(2).setCount(x3++);
				list.get(3).setCount(x4++);
				list.get(4).setCount(x5++);
			} else if (entry.getValue2()>=40) {
				list.get(0).setCount(x1++);
				list.get(1).setCount(x2++);
				list.get(2).setCount(x3++);
				list.get(3).setCount(x4++);
			}else if (entry.getValue2()>=30) {
				list.get(0).setCount(x1++);
				list.get(1).setCount(x2++);
				list.get(2).setCount(x3++);
			}else if (entry.getValue2()>=20) {
				list.get(0).setCount(x1++);
				list.get(1).setCount(x2++);
			}else if (entry.getValue2()>=10) {
				list.get(0).setCount(x1++);
			}
		}
    	return list;
    }
	public List<CountByDays> selectCountByDays(String startDay, String endDay,
			String parkName) {
		startDay+=" 00:00:00";
    	endDay+=" 24:00:00";
    	List<CountByDays> list=parkInfoDao.selectCountByDay(startDay, endDay, parkName);
    	return list;
	}
	public List<CommonMap> selectCountBycar(String startDay, String endDay,
			String parkName) {
		startDay+=" 00:00:00";
    	endDay+=" 24:00:00";
    	return parkInfoDao.selectCountBycar(startDay, endDay, parkName);
	}
	public List<DtoCar> selectSepcialFreDetail(String startDay,
			String endDay, String fre, String parkName) {
		startDay+=" 00:00:00";
    	endDay+=" 24:00:00";
    	Set<String> carNums=new HashSet<String>();
    	int x=Integer.valueOf(fre.trim());
    	List<CommonMap> list2=parkInfoDao.selectCountBycar(startDay, endDay,parkName);    	    	
    	for (CommonMap entry : list2) {
			if (entry.getValue2()>=x) {
				carNums.add(entry.getValue1());
			} 
		}
    	List<CarInfo> carInfos=carInfoDao.selectAllCars();
    	List<CarInfo> list1=new ArrayList<CarInfo>();
    	for (CarInfo carInfo : carInfos) {
			if (carNums.contains(carInfo.getCarNum())) {
				list1.add(carInfo);
			}
		}
		return MyUtils.CarInfoToDtoCar(list1);
	}
	
	public List<ParkTimeAndType> selectParkTimeAndType(String carNum,
			String parkName) {
		List<ParkTimeAndType> list =new ArrayList<ParkTimeAndType>();
		List<DtoPark> list2=parkInfoDao.selectParkTimeByCar(carNum);
		
		//SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		for (DtoPark dtoPark : list2) {
			ParkTimeAndType parkTimeAndType=new ParkTimeAndType();
			parkTimeAndType.setTime(dtoPark.getTime());
			parkTimeAndType.setType(dtoPark.getType());			
			list.add(parkTimeAndType);
		}
		return list;
	}
	
	public List<DtoPark> selectToDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date=df.format(new Date());
		String timeStart=date+" 00:00:00";
		String timeEnd=date+" 24:00:00";
		return parkInfoDao.selectToDay(timeStart,timeEnd);
	}

	public List<DtoPark> selectToDayKeyCars(List<String> carNums) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date=df.format(new Date());
		String timeStart=date+" 00:00:00";
		String timeEnd=date+" 24:00:00";
		return parkInfoDao.selectToDayKeyCars(timeStart,timeEnd,carNums);
	}
	public int insert(DtoPark record) {
		return parkInfoDao.insert(record);
	}
	public int insertBatch(List<DtoPark> dtoParks) {
		return parkInfoDao.insertBatch(dtoParks);
	}
	/* (non-Javadoc)
	 * @see hust.smartpark.service.ParkInfoService#selectAllCount()
	 */
	public String selectAllCount() {
		// TODO Auto-generated method stub
		return parkInfoDao.selectAllCount()+"";
	}	
}