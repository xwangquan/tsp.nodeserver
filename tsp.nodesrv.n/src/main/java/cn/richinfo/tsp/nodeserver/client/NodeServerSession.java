package cn.richinfo.tsp.nodeserver.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;

/**
 * @ClassName: NameServerSession
 * @Description: For client RMI
 * @author ÍõÈ«
 * @date 2014-9-11 ÏÂÎç5:25:47
 */
public class NodeServerSession {

	/**
	 * name server session
	 */
	private Channel session;

	/**
	 * Default operation timeout,if the operation is not returned in 5
	 * second,throw TimeoutException
	 */
	public static long DEFAULT_OP_TIMEOUT = 5000L;

	/**
	 * seq message pool
	 */
	private ConcurrentHashMap<Integer, Object> commands;

	/**
	 * message sequence
	 */
	private AtomicInteger sequence = new AtomicInteger();

	public NodeServerSession(Channel session) {
		this(session, DEFAULT_OP_TIMEOUT,
				new ConcurrentHashMap<Integer, Object>());
	}

	public NodeServerSession(Channel session, long time,
			ConcurrentHashMap<Integer, Object> commands) {
		if (time <= 0)
			time = DEFAULT_OP_TIMEOUT;
		DEFAULT_OP_TIMEOUT = time;
		this.session = session;
		this.commands = commands;
	}

	/**
	 * Commit command to server without respond
	 * 
	 * @param message
	 * @throws Exception
	 */
	public void writeWithNoRespond(HeadMessage message) throws Exception {
		if (session == null || !session.isActive())
			throw new IllegalArgumentException(
					"NameServer session was already closed!");
		int num = sequence.incrementAndGet();
		message.setSequenceNumber(num);
		ChannelFuture future = session.write(message);
		if (future.await().isSuccess())
			return;
	}

	/**
	 * Commit command to server then waiting for respond
	 * 
	 * @param message
	 *            command
	 * @return Server respond message
	 * @throws Exception
	 */
	public HeadMessage write(HeadMessage message) throws Exception {
		if (session == null || !session.isActive())
			throw new IllegalStateException(
					"NameServer session was already closed!");
		CountDownLatch lock = new CountDownLatch(1);
		int num = sequence.incrementAndGet();
		if (num >= 65535) {
			sequence.set(0);
			num = sequence.incrementAndGet();
		}
		message.setSequenceNumber(num);
		commands.put(num, lock);
		ChannelFuture future = session.writeAndFlush(message);
		if (future.awaitUninterruptibly().isSuccess()) {
			latchWait(lock, DEFAULT_OP_TIMEOUT);
			// timeout,remove
			if (commands.get(num) instanceof CountDownLatch) {
				commands.remove(num);
				return null;
			}
			// return respond
			HeadMessage respond = (HeadMessage) commands.get(num);
			commands.remove(num);
			return respond;
		}
		return null;
	}

	/**
	 * Commit command to server then return null,but if server respond,will
	 * invoke callback function
	 * 
	 * @param message
	 * @throws Exception
	 */
	public void writeWithCallback(HeadMessage message) throws Exception {
		if (session == null || !session.isActive())
			throw new IllegalArgumentException(
					"NameServer session was already closed!");

		if (session.attr(AttributeKey.valueOf(SessionStatusItem.CALLBACK
				.getName())) == null)
			throw new IllegalArgumentException("CallbackProcessor was null!");
		int num = sequence.incrementAndGet();
		message.setSequenceNumber(num);
		ChannelFuture future = session.write(message);
		if (future.await().isSuccess())
			return;
	}

	private void latchWait(final CountDownLatch lock, final long timeout)
			throws InterruptedException, TimeoutException {
		if (!lock.await(timeout, TimeUnit.MILLISECONDS)) {
			throw new TimeoutException("Timed out(" + timeout
					+ ") waiting for operation");
		}
	}

	/**
	 * Close the current session
	 */
	public void close() {
		if (session != null && session.isActive())
			session.close();
	}
}
