package cn.richinfo.tsp.nodeserver.message.third;

import cn.richinfo.tsp.nodeserver.message.HeadMessage;

/**
 * @ClassName: CarManagerPlantPushMessage
 * @Description: VG protocol 
 * @author ÍõÈ«
 * @date 2015-4-9 ÏÂÎç4:08:30
 */
@Deprecated
public class CarManagerPlantPushMessage extends HeadMessage {

	@Override
	public int read(byte[] receivedData) throws Exception {
		// TODO Auto-generated method stub
		return super.read(receivedData);
	}

	@Override
	public int read(byte[] receivedData, int offset) throws Exception {
		// TODO Auto-generated method stub
		return super.read(receivedData, offset);
	}

	@Override
	public byte[] write() {
		// TODO Auto-generated method stub
		return super.write();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
