package cn.richinfo.tsp.nodeserver.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;

import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;

/**
 * @ClassName: NodeServerEncoder
 * @Description: node server encoder
 * @author ÍõÈ«
 * @date 2014-11-18 ÏÂÎç4:07:28
 */
public class NodeServerEncoder extends MessageToByteEncoder<HeadMessage>
		implements TransferCodec {
	Logger logger = Logger.getLogger(NodeServerEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, HeadMessage message,
			ByteBuf buf) throws Exception {
		HeadMessage m = (HeadMessage) message;
		byte[] source = m.write();
		buf.writeByte(MARK_1);
		byte[] send = new byte[source.length + 1];
		System.arraycopy(source, 0, send, 0, source.length);
		send[send.length - 1] = getCheck(source);
		buf.writeBytes(encrypt(send));
		buf.writeByte(MARK_1);
	}

	/**
	 * calc check code
	 * 
	 * @param send
	 * @return
	 */
	private byte getCheck(byte[] send) {
		byte re = send[0];
		for (int i = 1; i < send.length; i++) {
			re = (byte) (re ^ send[i]);
		}
		return re;
	}

	/**
	 * transtaion transmission
	 */
	public static byte[] encrypt(byte[] source) {
		ByteBuf send = Unpooled.buffer(source.length);
		for (int i = 0; i < source.length; i++) {
			if (source[i] == MARK_1) {
				send.writeByte(MARK_2).writeByte(MARK_1_sec);
			} else if (source[i] == MARK_2) {
				send.writeByte(MARK_2).writeByte(MARK_2_sec);
			} else {
				send.writeByte(source[i]);
			}
		}
		byte[] out = new byte[send.readableBytes()];
		send.readBytes(out);
		ReferenceCountUtil.release(send);
		return out;
	}

}
