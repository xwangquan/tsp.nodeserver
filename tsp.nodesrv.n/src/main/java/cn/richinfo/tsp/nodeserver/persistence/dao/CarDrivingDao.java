package cn.richinfo.tsp.nodeserver.persistence.dao;

import java.util.Map;

import cn.richinfo.tsp.nodeserver.persistence.bean.CarDrivingBean;

/**
 * @ClassName: CarDrivingDao
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-11-21 ����5:09:06
 */
public interface CarDrivingDao {
	public void storeDriveData(Map<String, Object> map);

	public void storeDriveData1(CarDrivingBean bean);
}
