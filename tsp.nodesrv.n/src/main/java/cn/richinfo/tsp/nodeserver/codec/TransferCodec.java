package cn.richinfo.tsp.nodeserver.codec;

import cn.richinfo.tsp.nodeserver.message.EchoMessage;

/**
 * @ClassName: NodeServerBaseCodec
 * @Description:For node server transferred meaning!
 * @author ÍõÈ«
 * @date 2014-11-18 ÏÂÎç4:34:16
 */
public interface TransferCodec {
	public static final byte MARK_1 = 0x7e;
	public static final byte MARK_2 = 0x7d;
	public static final byte MARK_1_sec = 0x02;
	public static final byte MARK_2_sec = 0x01;
	public static EchoMessage wallMe = new EchoMessage();
}
