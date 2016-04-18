package cn.richinfo.tsp.nodeserver.persistence.info;

import cn.richinfo.tsp.nodeserver.message.sutrans.ObdLocationMessage;

/**
 * @ClassName: ObdLocation
 * @Description: map for {@link ObdLocationMessage}
 * @author 王全
 * @date 2015-8-11 下午3:55:31
 */
public class ObdLocation extends Json {

	// 时间 BCD[6] 时间格式：YYMMDDhhmmss
	public long time;

	// 上传类型 BYTE 0：主动上传；1：查询上传
	public byte type;

	// 状态 DWORD 状态位定义见图7
	public int status;

	// 纬度 DWORD 以度为单位的纬度值乘以10的六次方，精确到百万分之一度
	public double lattitude;

	// 经度 DWORD 以度为单位的经度值乘以10的六次方，精确到百万分之一度
	public double longitude;

	// 高程 WORD 海拔高度，单位为米
	public int height;

	// 速度 WORD 1/10km/h
	public int speed;

	// 方向 WORD 0-359°，正北为0，顺时针
	public int direction;

	public ObdLocation(ObdLocationMessage message) {
		time = message.getTime();
		type = message.getType();
		status = message.getStatus();
		lattitude = message.getLattitude();
		longitude = message.getLongitude();
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
