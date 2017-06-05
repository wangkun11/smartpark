/**
 * 
 */
package hust.smartpark.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.smartpark.dao.LogDao;
import hust.smartpark.entity.Log;
import hust.smartpark.service.LogService;

/**
 * @author 华中科技大学 王坤 （377816164@qq.com）
 *
 */
@Service
public class LogServiceImpl implements LogService{
	@Autowired
	LogDao logDao;

	public void createLog(Log log) {
		//System.out.println("createLog"+log.toString());
		logDao.insert(log);
	}

	/* (non-Javadoc)
	 * @see hust.smartpark.service.LogService#updateLog(hust.smartpark.entity.Log)
	 */
	public void updateLog(Log log) {
		//System.out.println("updateLog"+log.toString());
		logDao.update(log);
	}

}
