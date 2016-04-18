package cn.richinfo.tsp.nodeserver.persistence.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.richinfo.tsp.nodeserver.persistence.bean.CarDrivingBean;
import cn.richinfo.tsp.nodeserver.persistence.dao.CarDrivingDao;
import cn.richinfo.tsp.nodeserver.persistence.service.CarDrivingService;

/**
 * @ClassName: CarDrivingDaoImpl
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-11-21 ÏÂÎç5:14:24
 */
@Service("carDrivingDao")
public class CarDrivingDaoImpl implements CarDrivingDao {

	@Autowired
	CarDrivingService carDrivingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.richinfo.tsp.nodeserver.persistence.dao.CarDrivingDao#storeDriveData
	 * (java.util.Map)
	 */
	@Override
	public void storeDriveData(Map<String, Object> map) {
		carDrivingService.storeDriveData(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.richinfo.tsp.nodeserver.persistence.dao.CarDrivingDao#storeDriveData
	 * (cn.richinfo.tsp.nodeserver.persistence.bean.CarDrivingBean)
	 */
	@Override
	public void storeDriveData1(CarDrivingBean bean) {
		carDrivingService.storeDriveData1(bean);
	}

}
