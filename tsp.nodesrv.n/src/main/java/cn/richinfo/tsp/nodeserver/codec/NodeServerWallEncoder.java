package cn.richinfo.tsp.nodeserver.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import cn.richinfo.tsp.nodeserver.message.EchoMessage;
import cn.richinfo.tsp.nodeserver.message.HeadMessage;

/**
 * @ClassName: NodeServerWallEncoder
 * @Description: TODO()
 * @author ÍõÈ«
 * @param <I>
 * @date 2014-11-20 ÏÂÎç4:21:23
 */
public class NodeServerWallEncoder extends MessageToByteEncoder<HeadMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, HeadMessage msg,
			ByteBuf buf) throws Exception {
		if (msg instanceof EchoMessage) {
			ChannelFuture future = ctx
					.writeAndFlush(buf.writeBytes(msg.write()));
			if (future.await().isDone())
				ctx.channel().disconnect();
		}
		ctx.writeAndFlush(msg);
	}
}
