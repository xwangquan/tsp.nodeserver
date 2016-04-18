package cn.richinfo.tsp.nodeserver.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

import org.apache.log4j.Logger;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;
import cn.richinfo.tsp.nodeserver.message.MessageProcessor;
import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: NodeServerDecoder
 * @Description: node server decoder
 * @author ÍõÈ«
 * @date 2014-11-18 ÏÂÎç4:06:32
 */
public class NodeServerDecoder extends ByteToMessageDecoder implements
		TransferCodec {

	private static Logger log = Logger.getLogger(NodeServerDecoder.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.
	 * ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
			List<Object> out) throws Exception {
		if (buffer.readableBytes() >= 1) {
			if (buffer.readByte() == MARK_1) {// try tt
				buffer.readerIndex(buffer.readerIndex() - 1);
				transDecode(ctx, buffer, out);
				return;
			}
			buffer.clear();
			out.add(wallMe);
			return;
		}
	}

	// transtion transmission decode
	private void transDecode(ChannelHandlerContext session, ByteBuf buffer,
			List<Object> out) throws Exception {
		int count = 0;
		// skip first mark
		buffer.readByte();
		while (buffer.isReadable() && buffer.readByte() != MARK_1) {
			count++;
			if (!buffer.isReadable()) {// need data
				buffer.readerIndex(buffer.readerIndex() - count - 1);
				return;
			}
			continue;
		}
		if (count == 0) {// need data
			buffer.readerIndex(buffer.readerIndex() - count - 1);
			return;
		}
		buffer.readerIndex(buffer.readerIndex() - count - 2);
		byte[] msg = new byte[count + 2];
		buffer.readBytes(msg);
		byte[] decode = deciphering(msg);
		// check code
		if (decode[decode.length - 2] != getCheck(decode)) {// invaild check
			// force close
			buffer.clear();
			out.add(wallMe);
			return;
		}
		transDecode(decode, out);
	}

	private void transDecode(byte[] source, List<Object> out) throws Exception {
		// body properties
		byte[] bodyProp = new byte[2];
		System.arraycopy(source, 4, bodyProp, 0, 2);
		boolean hasSubPackage = hasSubPackage(bodyProp);
		// int bodyLen = getMessageLength(bodyProp);
		// head length
		int headLen = 91;
		if (hasSubPackage)
			headLen = 95;
		// message len
		// int len = 0;
		// len = SignUtils.getUnsignedByte(ConversionUtil.byte2short(source,
		// 40));
		HeadMessage message = MessageProcessor.processReceive(source, headLen);
		if(message==null){
			//return error!
			return ;
		}
		log.debug("Decoder received = " + message);
		out.add(message);
	}

	/**
	 * check
	 * 
	 * @param send
	 * @return
	 */
	private byte getCheck(byte[] send) {
		byte re = send[1];
		for (int i = 2; i < send.length - 2; i++) {
			re = (byte) (re ^ send[i]);
		}
		return re;
	}

	/**
	 * check bodyProp ,then return body length
	 * 
	 * @param bodyProp
	 * @return message length
	 */
	public int getMessageLength(byte[] bodyProp) {
		int prop = SignUtils.getUnsignedByte(ConversionUtil.byte2short(
				bodyProp, 0));
		int len = prop & 0x3FF;
		return len;
	}

	/**
	 * check bodyProp,is tea base on bit point
	 * 
	 * @param bodyProp
	 * @return true=rsa
	 */
	public final static boolean _hasRSA(byte[] bodyProp) {
		boolean rsa = false;
		int body = SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(bodyProp));
		if (((body >> 10) & 7) == 1)
			rsa = true;
		return rsa;
	}

	/**
	 * check bodyProp is tea base on string
	 * 
	 * @param bodyProp
	 * @return true=rsa
	 */
	public final static boolean hasRSA(byte[] bodyProp) {
		boolean rsa = false;
		byte[] bit = ConversionUtil.bytes2BinaryByte(bodyProp);
		if (bit[5] == 49)
			rsa = true;
		return rsa;
	}

	/**
	 * check bodyProp ,has package base on bit
	 * 
	 * @param bodyProp
	 * @return true=has sub package
	 */
	public final static boolean hasSubPackage(byte[] bodyProp) {
		boolean subPackage = false;
		int body = SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(bodyProp));
		if (((body >> 13) & 1) == 1)
			subPackage = true;
		return subPackage;
	}

	/**
	 * check bodyProp ,has package
	 * 
	 * @param bodyProp
	 * @return true=has sub package
	 */
	public final static boolean _hasSubPackage(byte[] bodyProp) {
		boolean subPackage = false;
		byte[] bit = ConversionUtil.bytes2BinaryByte(bodyProp);
		if (bit[2] == 49)
			subPackage = true;
		return subPackage;
	}

	/**
	 * de transtion transmission
	 * 
	 * @param source
	 * @return
	 */
	public byte[] deciphering(byte[] source) {
		ByteBuf send = Unpooled.buffer(source.length);
		for (int i = 0; i < source.length; i++) {
			if ((source[i] == MARK_2) && (source[i + 1] == MARK_1_sec)) {
				send.writeByte(MARK_1);
				i = i + 1;
			} else if ((source[i] == MARK_2) && (source[i + 1] == MARK_2_sec)) {
				send.writeByte(MARK_2);
				i = i + 1;
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
