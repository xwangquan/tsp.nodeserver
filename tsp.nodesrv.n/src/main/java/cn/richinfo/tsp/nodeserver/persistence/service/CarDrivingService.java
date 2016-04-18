package cn.richinfo.tsp.nodeserver.persistence.service;

import java.util.Map;

import cn.richinfo.tsp.nodeserver.persistence.bean.CarDrivingBean;

/**
 * @ClassName: CarDrivingDao
 * @author ÍõÈ«
 * @date 2014-11-21 ÏÂÎç5:09:06
 */
public interface CarDrivingService {
	public void storeDriveData(Map<String, Object> map);

	public void storeDriveData1(CarDrivingBean bean);
}
