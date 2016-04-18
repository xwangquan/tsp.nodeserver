package cn.richinfo.tsp.nodeserver.client;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cn.richinfo.tsp.nodeserver.message.sutrans.DummyDriveDataMessage;

public class Lo4jPress {

	static {
		PropertyConfigurator.configure("./conf/log4j.properties");
	}
	Logger loger = Logger.getLogger(Log4JLogger.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// cycle send message 8400Íò
		int thread = 300;
		int count = 200000;

		CyclicBarrier cyclicBarrier = new CyclicBarrier(thread + 1);
		AtomicLong sendMessageCount = new AtomicLong(0);
		AtomicLong receiveMessageCount = new AtomicLong(0);
		AtomicLong timeoutMessageCount = new AtomicLong(0);
		for (int i = 0; i < thread; i++) {
			new Lo4jPress().new Run(cyclicBarrier, count, sendMessageCount)
					.start();
		}
		try {
			cyclicBarrier.await();
			// statistics send message count
			while (true) {
				System.out.println(new Date() + " Send : "
						+ sendMessageCount.get() + ",Receicve : "
						+ receiveMessageCount.get() + ",Timeout : "
						+ timeoutMessageCount.get());
				TimeUnit.SECONDS.sleep(5);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Cycle send message
	 * 
	 * @author William Wang
	 * @version $Rev: $, Dec 5, 2011 11:32:00 PM
	 */
	class Run extends Thread {

		CyclicBarrier cyclicBarrier;

		int num;

		AtomicLong sendMessageCount;
		AtomicLong receiveMessageCount;
		AtomicLong timeoutMessageCount;

		public Run() {
			// TODO Auto-generated constructor stub
		}

		/**
		 * 
		 * @param cyclicBarrier
		 * @param session
		 * @param num
		 * @param sendMessageCount
		 */
		public Run(CyclicBarrier cyclicBarrier, int num,
				AtomicLong sendMessageCount) {
			this.cyclicBarrier = cyclicBarrier;
			this.num = num;
			this.sendMessageCount = sendMessageCount;
		}

		@Override
		public void run() {
			try {
				cyclicBarrier.await();

				String TUID = "wang quan obd test tuid"
						+ new String(new byte[26]);
				// TUID = "otuid10036";
				//TUID = "otuid15";
				DummyDriveDataMessage d = new DummyDriveDataMessage();
				d.setTuid(TUID);
				for (int i = 0; i < num; i++) {
					try {
						loger.info(d);
					} catch (Exception e) {
						timeoutMessageCount.incrementAndGet();
						continue;
					} finally {
						sendMessageCount.incrementAndGet();
					}
					//TimeUnit.MILLISECONDS.sleep(3);
				}
				cyclicBarrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
