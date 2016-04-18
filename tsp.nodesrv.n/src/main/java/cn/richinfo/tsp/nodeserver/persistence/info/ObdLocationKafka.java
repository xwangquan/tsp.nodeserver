package cn.richinfo.tsp.nodeserver.persistence.info;

import cn.richinfo.tsp.nodeserver.message.sutrans.ObdLocationMessage;

/**
 * @ClassName: ObdLocation
 * @Description: map for {@link ObdLocationMessage}
 * @author ÍõÈ«
 * @date 2015-8-11 ÏÂÎç3:55:31
 */
public class ObdLocationKafka extends Json {
	// {uin:10000,carId:0,vtuId:00001000-0000-1000-8000-00805F9B34FB,status:-1,lat:4294.967295,lon:4294.967295,height:65535,speed:6553.5,direction:65535,reportTime:20140808160434}

	public int uin;
	public int carId;
	public String vtuId;
	public int status;
	public double lat;
	public double lon;
	public int height;
	public int speed;
	public int direction;
	public long time;

	public ObdLocationKafka(ObdLocationMessage message, int uin, int carId) {
		this.uin = uin;
		this.carId = carId;
		vtuId = message.getTuid().trim();
		time = message.getTime();
		status = message.getStatus();
		lat = message.getLattitude();
		lon = message.getLongitude();
		height = message.getHeight();
		speed = message.getSpeed();
		direction = message.getDirection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.persistence.info.Json#toString()
	 */
	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			// just ignore
		}
		return null;
	}

}
