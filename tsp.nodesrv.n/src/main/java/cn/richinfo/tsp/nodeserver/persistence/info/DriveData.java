package cn.richinfo.tsp.nodeserver.persistence.info;

import cn.richinfo.tsp.nodeserver.message.sutrans.DriveDataMessage;

/**
 * 
 * @ClassName: DriveDataInfo
 * @Description: map for {@link DriveDataMessage}
 * @author 王全
 * @date 2014-11-21 下午3:16:17
 */
public class DriveData extends Json {

	// 上传类型 0：主动上传；1：查询上传
	public byte type;

	// OBD报警标志位 BYTE[4] 暂保留
	// public long obdWarn;
	// OBD状态标志位 BYTE[4] 0x00000000：非休眠；0x80000000：休眠；
	public long obdStatus;

	// 瞬时速度 BYTE[2] 单位：0.1km/h
	public float speed;
	// 发动机转速 BYTE[2] 数值范围：0 - 30000rpm
	public int engineRotationRate;
	// 电瓶电压V BYTE[2] 单位：0.1V
	public float batteryVoltage;

	// 车辆总里程 BYTE[4] 单位：0.1km
	public float totalMileage;

	// 怠速瞬时油耗 BYTE[2] 单位：0.1L/h
	public float idleConsumption;

	// 行驶瞬时油耗 BYTE[2] 单位：0.1L/100km
	public float fuelConsumption;

	// 发动机负荷 BYTE[1] 数值范围： 0 - 100 %
	public byte engineLoad;

	// 冷却液温度 BYTE[2] 数值范围: -60 – 250 ℃ 有符号整数
	public int waterTemperature;

	// 燃油压力 BYTE[2] 数值范围: 0 - 999 kPa
	public int fuelPressure;

	// 进气歧管绝对压力 BYTE[2] 数值范围: 0 – 500 kPa
	public int manifoldPressure;

	// 进气温度 BYTE[2] 数值范围：-60 – 250 ℃ 有符号整数
	public int inletTemperature;

	// 进气流量 BYTE[2] 数值范围: 0 – 999 g/s
	public int inletTlow;

	// 节气门绝对位置 BYTE[1] 数值范围: 0 – 100 %
	public byte tthrottlePosition;

	// 行车状态 BYTE[1] 1：行车中；2：驻车中；3：熄火
	public byte status;

	// 瞬时油耗 BYTE[2] 单位：0.1L/H
	public float hourGallon;

	// 油门踏板相对位置 BYTE[2] 单位：0.01%
	public float pedalPosition;

	// 油门踏板状态 BYTE[1] 0：松开；1：踏下
	public byte pedalStatus;

	// 剩余油量（%） BYTE[2] 单位：0.1%
	public float restOil;

	// 时间 BCD[6] 时间格式：YYMMDDhhmmss
	public long time;

	public DriveData(DriveDataMessage message) {
		type = message.getType();
		// obdWarn = message.getObdWarn();
		if (message.getObdStatus() == 0x80000000)
			obdStatus = 1;
		else
			obdStatus = 0;
		speed = message.getSpeed();
		engineRotationRate = message.getEngineRotationRate();
		batteryVoltage = message.getBatteryVoltage();
		totalMileage = message.getTotalMileage();
		idleConsumption = message.getIdleConsumption();
		fuelConsumption = message.getFuelPressure();
		engineLoad = message.getEngineLoad();
		waterTemperature = message.getWaterTemperature();
		fuelPressure = message.getFuelPressure();
		manifoldPressure = message.getManifoldPressure();
		inletTemperature = message.getInletTemperature();
		inletTlow = message.getInletTlow();
		tthrottlePosition = message.getTthrottlePosition();
		status = message.getStatus();
		hourGallon = message.getHourGallon();
		pedalPosition = message.getPedalPosition();
		pedalStatus = message.getPedalStatus();
		restOil = message.getRestOil();
		time = message.getTime();
	}

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
