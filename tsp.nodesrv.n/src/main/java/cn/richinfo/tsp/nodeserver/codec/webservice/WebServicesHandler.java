package cn.richinfo.tsp.nodeserver.codec.webservice;

import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_ENCODING;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TRANSFER_ENCODING;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import cn.richinfo.tsp.nodeserver.entrance.NodeServer;
import cn.richinfo.tsp.nodeserver.entrance.NodeServerContext;

/**
 * @ClassName: WebServicesHandler
 * @Description: For web service servlet
 * @author ÍõÈ«
 * @date 2014-12-3 ÉÏÎç9:26:57
 */
public class WebServicesHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof HttpRequest) {
			HttpRequest req = (HttpRequest) msg;

			if (is100ContinueExpected(req)) {
				ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
			}
			boolean keepAlive = isKeepAlive(req);
			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
					OK, Unpooled.wrappedBuffer(print().getBytes()));
			// response.headers().set(CONTENT_TYPE, "text/plain");
			response.headers().set(CONTENT_TYPE, "text/html");
			response.headers().set(CONTENT_ENCODING, "GBK");
			response.headers().set(CONTENT_TRANSFER_ENCODING, "GBK");

			response.headers().set(CONTENT_LENGTH,
					response.content().readableBytes());

			if (!keepAlive) {
				ctx.writeAndFlush(response).addListener(
						ChannelFutureListener.CLOSE);
			} else {
				response.headers().set(CONNECTION, Values.KEEP_ALIVE);
				ctx.writeAndFlush(response);
			}
		}
	}

	private String print() {
		DecimalFormat decimalFormat = new DecimalFormat(",000");

		SimpleDateFormat sdfShortTime = new SimpleDateFormat(
				"YYYY-MM-dd HH:mm:ss");
		StringBuffer sb = new StringBuffer(
				"<h3>Node Server Moudle Monitor </h3>");

		sb.append("<br>&nbsp; Start On:").append(
				sdfShortTime.format(NodeServer.STARTTIME));
		sb.append("<hr/>");

		sb.append("<h5>Put Queen</h5>");
		long size = NodeServerContext.getContext().getDrive().size();
		sb.append("&nbsp; Drive Data queen:").append(
				(size > 999) ? decimalFormat.format(size) : size);
		size = NodeServerContext.getContext().getObdLocation().size();
		sb.append("<br>&nbsp; Obd Location Queen:").append(
				(size > 999) ? decimalFormat.format(size) : size);
		size = NodeServerContext.getContext().getCarStatus().size();
		sb.append("<br>&nbsp; Car Status Queen:").append(
				(size > 999) ? decimalFormat.format(size) : size);
		sb.append("<hr/>");

		sb.append("<h5>Thread Alive</h5>");
		ThreadGroup group = NodeServerContext.getContext().getWorkGroups();
		sb.append("&nbsp; Convert size:").append(group.activeCount());

		sb.append("<hr/>");
		sb.append("<h5>User & Callback</h5>");
		sb.append("<hr/>");
		return sb.toString();

	}
}
