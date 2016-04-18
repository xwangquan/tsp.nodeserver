package cn.richinfo.tsp.nodeserver.persistence.info;

import cn.richinfo.tsp.nodeserver.message.sutrans.CarStatusMessage;

/**
 * @ClassName: CarStatus
 * @Description: TODO()
 * @author 王全
 * @date 2014-12-1 下午2:43:28
 */
public class CarStatus extends Json {
	// type: 上传类型 0：主动上传；1：查询上传
	public int type;
	// status: 态标志位0：保留；1：定位状态；2：南北纬 3：东西经 4：保留 5：加密
	public int status;
	// fatLamp: 灯状态(远光灯) 0:关闭,1:打开,2:保留,3:未知
	public int fatLamp;
	// nearLamp: 灯状态(近光灯) 0:关闭,1:打开,2:保留,3:未知
	public int nearLamp;
	// widthLamp: 灯状态(示宽灯) 0:关闭,1:打开,2:保留,3:未知
	public int widthLamp;
	// fogLamp: 灯状态(雾灯) 0:关闭,1:打开,2:保留,3:未知
	public int fogLamp;
	// leftLamp: 灯状态(左转向) 0:关闭,1:打开,2:保留,3:未知
	public int leftLamp;
	// rightLamp: 灯状态(右转向) 0:关闭,1:打开,2:保留,3:未知
	public int rightLamp;
	// hazardLamp: 灯状态(危险灯) 0:关闭,1:打开,2:保留,3:未知
	public int hazardLamp;
	// leftFrontDoor: 门状态(左前门) 0:关闭,1:打开,2:保留,3:未知
	public int leftFrontDoor;
	// rightFrontDoor: 门状态(右前门) 0:关闭,1:打开,2:保留,3:未知
	public int rightFrontDoor;
	// leftRearDoor: 门状态(左后门) 0:关闭,1:打开,2:保留,3:未知
	public int leftRearDoor;
	// rightRearDoor: 门状态(右后门) 0:关闭,1:打开,2:保留,3:未知
	public int rightRearDoor;
	// trunk: 门状态(后备箱\尾箱) 0:关闭,1:打开,2:保留,3:未知
	public int trunk;
	// lock: 门锁(全车) 0:解锁,1:上锁,2:保留,3:未知
	public int lock;
	// leftFrontLock: 门锁(左前门) 0:解锁,1:上锁,2:保留,3:未知
	public int leftFrontLock;
	// rightFrontLock: 门锁(右前门) 0:解锁,1:上锁,2:保留,3:未知
	public int rightFrontLock;
	// leftRearLock: 门锁(左后门) 0:解锁,1:上锁,2:保留,3:未知
	public int leftRearLock;
	// rightRearLock: 门锁(右后门) 0:解锁,1:上锁,2:保留,3:未知
	public int rightRearLock;
	// trunkLock: 门锁(后备箱\尾箱) 0:解锁,1:上锁,2:保留,3:未知
	public int trunkLock;
	// leftFrontWindow: 窗状态(左前窗) 0:关闭,1:打开,2:保留,3:未知
	public int leftFrontWindow;
	// rightFrontWindow: 窗状态(右前窗) 0:关闭,1:打开,2:保留,3:未知
	public int rightFrontWindow;
	// leftRearWindow: 窗状态(左后窗) 0:关闭,1:打开,2:保留,3:未知
	public int leftRearWindow;
	// rightRearWindow: 窗状态(右后窗) 0:关闭,1:打开,2:保留,3:未知
	public int rightRearWindow;
	// topWindow: 窗状态(天窗) 0:关闭,1:打开,2:保留,3:未知
	public int topWindow;
	// ecmSignal: 故障信号(ECM) 0:正常,1:故障,2:保留,3:未知
	public int ecmSignal;
	// absSignal: 故障信号(ABS) 0:正常,1:故障,2:保留,3:未知
	public int absSignal;
	// srsSignal: 故障信号(SRS) 0:正常,1:故障,2:保留,3:未知
	public int srsSignal;
	// oilAlarmSignal: 报警信号(机油) 0:正常,1:故障,2:保留,3:未知
	public int oilAlarmSignal;
	// pressureSignal: 报警信号(胎压) 0:正常,1:故障,2:保留,3:未知
	public int pressureSignal;
	// healthSignal: 报警信号(保养) 0:正常,1:故障,2:保留,3:未知
	public int healthSignal;
	// airbag: 安全气囊状态 0:正常,1:故障,2:保留,3:未知
	public int airbag;
	// handBrake: 手刹状态 0:松开,1:拉紧,2:保留,3:未知
	public int handBrake;
	// braking: 刹车状态(脚刹) 0:松开,1:踏下,2:保留,3:未知
	public int braking;
	// driverSeatBelt: 安全带(驾驶员) 0:未扣,1:已扣,2:保留,3:未知
	public int driverSeatBelt;
	// copilotSeatBelt: 安全带(副驾) 0:未扣,1:已扣,2:保留,3:未知
	public int copilotSeatBelt;
	// accSignal: ACC 信号 0:无效,1:有效,2:保留,3:未知
	public int accSignal;
	// keySignal: 钥匙状态 0:无效,1:有效,2:保留,3:未知
	public int keySignal;
	// remoteSignal: 遥控信号 0：未按；1：开锁；2：关锁；3：尾箱锁；
	// 4：长按开锁；5：长按关锁；6:保留；7：未知
	public int remoteSignal;
	// wiper: 雨刮状态 0:关闭,1:打开,2:保留,3:未知
	public int wiper;
	// airConditioner: 空调开关 0:关闭,1:打开,2:保留,3:未知
	public int airConditioner;
	// carStalls: 档位 1：P档；2：R档；3：N档；4：D档；
	public int carStalls;
	// reportTime: 上报记录时间 格式: YYYYMMDDHHMMSS
	public long reportTime;

	public CarStatus(CarStatusMessage message) {
		type = message.type;
		reportTime = message.getTime();
		fatLamp = message.a & 3;
		nearLamp = message.a >> 2 & 3;
		widthLamp = message.a >> 4 & 3;
		fogLamp = message.a >> 6 & 3;
		// b
		leftLamp = message.b & 3;
		rightLamp = message.b >> 2 & 3;
		hazardLamp = message.b >> 4 & 3;
		leftFrontDoor = message.b >> 6 & 3;
		// c
		rightFrontDoor = message.c & 3;
		leftRearDoor = message.c >> 2 & 3;
		rightRearDoor = message.c >> 4 & 3;
		trunk = message.c >> 6 & 3;
		// d
		lock = message.d & 3;
		leftFrontLock = message.d >> 2 & 3;
		rightFrontLock = message.d >> 4 & 3;
		leftRearLock = message.d >> 6 & 3;
		// e
		rightRearLock = message.e & 3;
		trunkLock = message.e >> 2 & 3;
		leftFrontWindow = message.e >> 4 & 3;
		rightFrontWindow = message.e >> 6 & 3;
		// f
		leftRearWindow = message.f & 3;
		rightRearWindow = message.f >> 2 & 3;
		topWindow = message.f >> 4 & 3;
		ecmSignal = message.f >> 6 & 3;
		// g
		absSignal = message.g & 3;
		srsSignal = message.g >> 2 & 3;
		oilAlarmSignal = message.g >> 4 & 3;
		pressureSignal = message.g >> 6 & 3;
		// h
		healthSignal = message.h & 3;
		airbag = message.h >> 2 & 3;
		handBrake = message.h >> 4 & 3;
		braking = message.h >> 6 & 3;
		// i
		driverSeatBelt = message.i & 3;
		copilotSeatBelt = message.i >> 2 & 3;
		accSignal = message.i >> 4 & 3;
		keySignal = message.i >> 6 & 3;
		// j
		remoteSignal = message.j & 0xf;
		wiper = message.j >> 4 & 3;
		airConditioner = message.j >> 6 & 3;
		// k
		carStalls = message.j & 0xf;
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
