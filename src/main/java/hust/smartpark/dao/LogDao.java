/**
 * 
 */
package hust.smartpark.dao;

import hust.smartpark.entity.Log;

/**
 * @author 华中科技大学 王坤 （377816164@qq.com）
 *
 */
public interface LogDao {

	/**
	 * @param log
	 */
	public void insert(Log log);

	/**
	 * @param log
	 */
	public void update(Log log);
}
