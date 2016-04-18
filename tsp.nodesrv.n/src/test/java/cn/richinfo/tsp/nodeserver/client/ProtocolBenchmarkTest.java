package cn.richinfo.tsp.nodeserver.client;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.HeartBeatMessage;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * Client entrance benchmark test!
 * 
 * @ClassName: MockTerminalBenchmarkTest
 * @Description: TODO() wait to do!
 * @author 王全
 * @date 2014-8-1 下午6:03:00
 */
public class ProtocolBenchmarkTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// cycle send message 8400万
		int thread = 300;
		int count = 200000;

		CyclicBarrier cyclicBarrier = new CyclicBarrier(thread + 1);
		AtomicLong sendMessageCount = new AtomicLong(0);
		AtomicLong receiveMessageCount = new AtomicLong(0);
		AtomicLong timeoutMessageCount = new AtomicLong(0);
		NodeServerSession session = null;
		for (int i = 0; i < thread; i++) {
			if (i % 100 == 0) {
				NodeServerSessionBuilder builder = new NodeServerSessionBuilder(
						new InetSocketAddress("localhost", 8888));
				builder.setMaxRespondTime(30 * 1000);
				session = builder.build();
			}
			new ProtocolBenchmarkTest().new Run(cyclicBarrier, session, count,
					sendMessageCount, receiveMessageCount, timeoutMessageCount)
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
				AtomicLong receiveMessageCount, AtomicLong timeoutMessageCount) {
			this.cyclicBarrier = cyclicBarrier;
			this.session = session;
			this.num = num;
			this.sendMessageCount = sendMessageCount;
			this.receiveMessageCount = receiveMessageCount;
			this.timeoutMessageCount = timeoutMessageCount;
		}

		@Override
		public void run() {
			try {
				cyclicBarrier.await();

				String TUID = "wang quan obd test tuid"
						+ new String(new byte[26]);
				// TUID = "otuid10036";
				TUID = "otuid15";

				byte[] tuid = TUID.getBytes();
				for (int i = 0; i < num; i++) {
					HeadMessage msg = new HeartBeatMessage();
					msg.setHeadLength(91);
					msg.setTuid(new String(tuid));
					// set body prop
					msg.setBodyProp(SignUtils.getUnsingedShort(msg
							.getMessageLength()));
					HeadMessage verifyCodeRespMesage = null;
					try {
						verifyCodeRespMesage = session.write(msg);
					}catch (TimeoutException e) {
						timeoutMessageCount.incrementAndGet();
						continue;
					}
					catch (IllegalStateException e) {
						timeoutMessageCount.incrementAndGet();
						cyclicBarrier.await();
						return;
					} finally {
						sendMessageCount.incrementAndGet();
					}
					if (verifyCodeRespMesage != null
							&& verifyCodeRespMesage.getSequenceNumber() == msg
									.getSequenceNumber()) {
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
