package cn.richinfo.tsp.nodeserver.message.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.richinfo.tsp.nodeserver.message.sutrans.DriveDataMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyDriveDataMessage;
import cn.richinfo.tsp.nodeserver.persistence.bean.CarDrivingBean;
import cn.richinfo.tsp.nodeserver.persistence.info.DriveData;
import cn.richinfo.tsp.nodeserver.persistence.service.CarDrivingService;

/**
 * @ClassName: SpringContextTest
 * @Description: test inset from spring mybatis to mysql
 * @author 王全
 * @date 2014-8-5 下午12:57:39
 */
public class SpringContextTest {
	static {
		PropertyConfigurator.configure("./conf/log4j.properties");
	}

	Logger log = Logger.getLogger(getClass());

	public static void main(String[] args) throws Exception {
		int thread = 300;
		int count = 10000;
		CyclicBarrier cyclicBarrier = new CyclicBarrier(thread + 1);
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		AtomicLong sendMessageCount = new AtomicLong(0);
		for (int i = 0; i < thread; i++) {
			new SpringContextTest().new SWorkers(cyclicBarrier, context, count,
					sendMessageCount).start();
		}
		try {
			cyclicBarrier.await();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Cycle send message
	 * 
	 * @ClassName: SWorkers
	 * @Description: TODO()
	 * @author 王全
	 * @date 2014-8-5 下午1:45:54
	 */
	class SWorkers extends Thread {

		CyclicBarrier cyclicBarrier;

		int num;

		AtomicLong sendMessageCount;
		ApplicationContext context;

		/**
		 * 
		 * @param cyclicBarrier
		 * @param session
		 * @param num
		 * @param sendMessageCount
		 */
		public SWorkers(CyclicBarrier cyclicBarrier,
				ApplicationContext context, int num, AtomicLong sendMessageCount) {
			this.cyclicBarrier = cyclicBarrier;
			this.num = num;
			this.context = context;
			this.sendMessageCount = sendMessageCount;
		}

		@Override
		public void run() {
			try {
				cyclicBarrier.await();
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
				for (int i = 0; i < num; i++) {
					try {
						CarDrivingService userService = (CarDrivingService) context
								.getBean("carDrivingService");
						userService.storeDriveData(map);
					} catch (Exception e) {
						log.error(e);
					}

					TimeUnit.MILLISECONDS.sleep(2);
				}
				cyclicBarrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
