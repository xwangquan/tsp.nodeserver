package cn.richinfo.tsp.nodeserver.message.persistence;

import java.util.HashMap;
import java.util.Map;

import cn.richinfo.tsp.nodeserver.base.PersistenceBaseTest;
import cn.richinfo.tsp.nodeserver.message.sutrans.DriveDataMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyDriveDataMessage;
import cn.richinfo.tsp.nodeserver.persistence.bean.CarDrivingBean;
import cn.richinfo.tsp.nodeserver.persistence.info.DriveData;
import cn.richinfo.tsp.nodeserver.persistence.service.CarDrivingService;

/**
 * 
 * @ClassName: {@link CarDrivingService}
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2014-8-6 ÏÂÎç6:16:26x
 */
public class CarDrivingTest extends PersistenceBaseTest {

	public void testgetByTuid() throws Exception {
		CarDrivingService userService = (CarDrivingService) context
				.getBean("carDrivingService");
		CarDrivingBean bean = new CarDrivingBean();
		bean.setUin(11111);
		bean.setNodeid(1);
		bean.setTrip_id("aaaaaaaaa");
		bean.setVtu_id("wangquan obd test");
		
		DriveDataMessage msg = DummyDriveDataMessage.getMessage();
		DriveData data = new DriveData(msg);
		bean.setCar_driving_info(data.toString());
		
		Map<String, Object> map = new HashMap<>();
		map.put("uin", bean.getUin());
		map.put("trip_id", bean.getTrip_id());
		map.put("car_driving_info", bean.getCar_driving_info());
		map.put("vtu_id", bean.getVtu_id());
		map.put("nodeid", bean.getNodeid());
		userService.storeDriveData(map);
		// assertNotNull(vtuInfo);
		// log.info(vtuInfo.toString());
	}

}
