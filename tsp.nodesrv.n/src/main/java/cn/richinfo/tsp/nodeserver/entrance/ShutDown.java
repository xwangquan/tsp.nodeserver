package cn.richinfo.tsp.nodeserver.entrance;

import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ShutDown
 * @Description: Safity exit nodeserver
 * @author ÍõÈ«
 * @date 2014-12-3 ÏÂÎç3:34:55
 */
public class ShutDown {

	private final static String SHUTDOWN = "SHUTDOWN";

	private String ip = "127.0.0.1";
	private int port = 8318;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShutDown down = new ShutDown();
		if (args != null && args.length >= 2) {
			down.ip = args[0];
			down.port = Integer.parseInt(args[1]);
		}
		down.shutdown();

	}

	public void shutdown() {
		try {
			Socket client = new Socket(ip, port);
			OutputStream out = client.getOutputStream();
			out.write(SHUTDOWN.getBytes());
			out.flush();
			TimeUnit.SECONDS.sleep(1);
			out.close();
			System.out.println("Node server " + ip
					+ " was shutdown successfull!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
