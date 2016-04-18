package cn.richinfo.tsp.nodeserver.asyn;

import cn.richinfo.tsp.nodeserver.message.sutrans.UploadTransparentTransmissionMessage;

/**
 * @ClassName: NodeServerQueen
 * @Description: for tt queen
 * @author ÍõÈ«
 * @date 2014-11-20 ÏÂÎç1:46:56
 */
public abstract class NodeServerQueen<I> extends
		CircularQueue<UploadTransparentTransmissionMessage> {

	private static final long serialVersionUID = 1L;

	public NodeServerQueen() {
		super(64);
	}

	@Override
	public synchronized boolean offer(UploadTransparentTransmissionMessage item) {
		return super.offer(item);
	}

	@Override
	public synchronized UploadTransparentTransmissionMessage poll() {
		return super.poll();
	}

}
