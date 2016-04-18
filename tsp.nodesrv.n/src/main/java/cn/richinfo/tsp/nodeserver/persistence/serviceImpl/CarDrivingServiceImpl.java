package cn.richinfo.tsp.nodeserver.persistence.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.richinfo.tsp.nodeserver.persistence.bean.CarDrivingBean;
import cn.richinfo.tsp.nodeserver.persistence.dao.CarDrivingDao;
import cn.richinfo.tsp.nodeserver.persistence.service.CarDrivingService;

/**
 * 
 * @ClassName: CarDrivingServiceImpl
 * @author ÍõÈ«
 * @date 2014-11-21 ÏÂÎç5:30:22
 */
@Service("carDrivingService")
public class CarDrivingServiceImpl implements CarDrivingService {

	@Autowired
	private CarDrivingDao carDrivingDao;

	@Override
	public void storeDriveData(Map<String, Object> map) {
		carDrivingDao.storeDriveData(map);
	}

	@Override
	public void storeDriveData1(CarDrivingBean bean) {
		carDrivingDao.storeDriveData1(bean);
	}

}
