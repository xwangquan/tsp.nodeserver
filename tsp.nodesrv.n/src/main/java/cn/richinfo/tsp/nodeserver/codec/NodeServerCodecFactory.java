package cn.richinfo.tsp.nodeserver.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;

/**
 * @ClassName: NodeServerCodec
 * @Description: obd protocol codec factory
 * @author ÍõÈ«
 * @date 2014-11-18 ÏÂÎç3:49:20
 */
public class NodeServerCodecFactory extends ByteToMessageCodec<HeadMessage> {
	private NodeServerEncoder encoder;
	private NodeServerDecoder decoder;

	public NodeServerCodecFactory(NodeServerEncoder encoder, NodeServerDecoder decoder) {
		this.encoder = encoder;
		this.decoder = decoder;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, HeadMessage msg,
			ByteBuf out) throws Exception {
		encoder.encode(ctx, msg, out);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		decoder.decode(ctx, in, out);
	}

}
