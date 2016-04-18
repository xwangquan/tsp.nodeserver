package cn.richinfo.tsp.nodeserver.client.sutrans;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import cn.richinfo.tsp.nodeserver.client.NodeServerSession;
import cn.richinfo.tsp.nodeserver.client.NodeServerSessionBuilder;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyCarStatusMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyDriveCustomMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyDriveDataMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyObdFaultCodeMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyObdLocationMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyTripReportDataMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyWakeUpMessage;
import cn.richinfo.tsp.nodeserver.message.sutrans.DummyWarnDataMessage;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * Client entrance benchmark test!
 * 
 * @ClassName: MockTerminalBenchmarkTest
 * @Description: wait to do!
 * @author 王全
 * @date 2014-8-1 下午6:03:00
 */
public class BenchmarkTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// cycle send message 8400万
		int thread = 1;
		int count = 1000;
		// int count = 10000;

		CyclicBarrier cyclicBarrier = new CyclicBarrier(thread + 1);
		AtomicLong sendMessageCount = new AtomicLong(0);
		AtomicLong receiveMessageCount = new AtomicLong(0);
		AtomicLong timeoutMessageCount = new AtomicLong(0);
		NodeServerSession session = null;
		for (int i = 0; i < thread; i++) {
			if (i % 100 == 0) {
				NodeServerSessionBuilder builder = new NodeServerSessionBuilder(
						new InetSocketAddress("localhost", 8317));
				builder.setMaxRespondTime(30 * 1000);
				session = builder.build();
			}
			new BenchmarkTest().new Run(cyclicBarrier, session, count,
					sendMessageCount, receiveMessageCount, timeoutMessageCount,i)
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

		NodeServerSession session;

		int num;

		AtomicLong sendMessageCount;
		AtomicLong receiveMessageCount;
		AtomicLong timeoutMessageCount;
		int name;
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
		public Run(CyclicBarrier cyclicBarrier, NodeServerSession session,
				int num, AtomicLong sendMessageCount,
				AtomicLong receiveMessageCount, AtomicLong timeoutMessageCount,int i) {
			this.cyclicBarrier = cyclicBarrier;
			this.session = session;
			this.num = num;
			this.sendMessageCount = sendMessageCount;
			this.receiveMessageCount = receiveMessageCount;
			this.timeoutMessageCount = timeoutMessageCount;
			this.name=i;
			setName("Work"+i);
		}

		@Override
		public void run() {
			try {
				cyclicBarrier.await();

				String TUID = "wang quan obd test tuid"
						+ new String(new byte[26]);
				// TUID = "otuid10036";
				TUID = "otuid15";
				TUID="wangquan@"+getName();
				HeadMessage msg = null;
				msg = new DummyWarnDataMessage();// 7800
				msg = new DummyWakeUpMessage();// 6800
				msg = new DummyDriveCustomMessage();// 9000
				msg = new DummyDriveDataMessage();
				msg = new DummyCarStatusMessage();// 3000
				msg = new DummyObdLocationMessage();// 3000
				msg = new DummyObdFaultCodeMessage();// 2300
				msg = new DummyTripReportDataMessage();// 900

				msg.setHeadLength(91);
				msg.setTuid(TUID);
				
				// set body prop
				msg.setBodyProp(SignUtils.getUnsingedShort(msg
						.getMessageLength()));
				for (int i = 0; i < num; i++) {
					msg.setTuid(TUID+"@"+i);
					HeadMessage verifyCodeRespMesage = null;
					try {
						verifyCodeRespMesage = session.write(msg);
					} catch (Exception e) {
						// e.printStackTrace();
						timeoutMessageCount.incrementAndGet();
						continue;
					} finally {
						sendMessageCount.incrementAndGet();
					}
					if (verifyCodeRespMesage != null
							&& verifyCodeRespMesage.getSequenceNumber() == msg
									.getSequenceNumber()) {
						// System.out.println(verifyCodeRespMesage);
						receiveMessageCount.incrementAndGet();
					}
					TimeUnit.MILLISECONDS.sleep(3);
				}
				cyclicBarrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
