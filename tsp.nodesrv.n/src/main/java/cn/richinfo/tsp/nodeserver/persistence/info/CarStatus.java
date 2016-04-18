package cn.richinfo.tsp.nodeserver.persistence.info;

import cn.richinfo.tsp.nodeserver.message.sutrans.CarStatusMessage;

/**
 * @ClassName: CarStatus
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-12-1 ����2:43:28
 */
public class CarStatus extends Json {
	// type: �ϴ����� 0�������ϴ���1����ѯ�ϴ�
	public int type;
	// status: ̬��־λ0��������1����λ״̬��2���ϱ�γ 3�������� 4������ 5������
	public int status;
	// fatLamp: ��״̬(Զ���) 0:�ر�,1:��,2:����,3:δ֪
	public int fatLamp;
	// nearLamp: ��״̬(�����) 0:�ر�,1:��,2:����,3:δ֪
	public int nearLamp;
	// widthLamp: ��״̬(ʾ���) 0:�ر�,1:��,2:����,3:δ֪
	public int widthLamp;
	// fogLamp: ��״̬(���) 0:�ر�,1:��,2:����,3:δ֪
	public int fogLamp;
	// leftLamp: ��״̬(��ת��) 0:�ر�,1:��,2:����,3:δ֪
	public int leftLamp;
	// rightLamp: ��״̬(��ת��) 0:�ر�,1:��,2:����,3:δ֪
	public int rightLamp;
	// hazardLamp: ��״̬(Σ�յ�) 0:�ر�,1:��,2:����,3:δ֪
	public int hazardLamp;
	// leftFrontDoor: ��״̬(��ǰ��) 0:�ر�,1:��,2:����,3:δ֪
	public int leftFrontDoor;
	// rightFrontDoor: ��״̬(��ǰ��) 0:�ر�,1:��,2:����,3:δ֪
	public int rightFrontDoor;
	// leftRearDoor: ��״̬(�����) 0:�ر�,1:��,2:����,3:δ֪
	public int leftRearDoor;
	// rightRearDoor: ��״̬(�Һ���) 0:�ر�,1:��,2:����,3:δ֪
	public int rightRearDoor;
	// trunk: ��״̬(����\β��) 0:�ر�,1:��,2:����,3:δ֪
	public int trunk;
	// lock: ����(ȫ��) 0:����,1:����,2:����,3:δ֪
	public int lock;
	// leftFrontLock: ����(��ǰ��) 0:����,1:����,2:����,3:δ֪
	public int leftFrontLock;
	// rightFrontLock: ����(��ǰ��) 0:����,1:����,2:����,3:δ֪
	public int rightFrontLock;
	// leftRearLock: ����(�����) 0:����,1:����,2:����,3:δ֪
	public int leftRearLock;
	// rightRearLock: ����(�Һ���) 0:����,1:����,2:����,3:δ֪
	public int rightRearLock;
	// trunkLock: ����(����\β��) 0:����,1:����,2:����,3:δ֪
	public int trunkLock;
	// leftFrontWindow: ��״̬(��ǰ��) 0:�ر�,1:��,2:����,3:δ֪
	public int leftFrontWindow;
	// rightFrontWindow: ��״̬(��ǰ��) 0:�ر�,1:��,2:����,3:δ֪
	public int rightFrontWindow;
	// leftRearWindow: ��״̬(���) 0:�ر�,1:��,2:����,3:δ֪
	public int leftRearWindow;
	// rightRearWindow: ��״̬(�Һ�) 0:�ر�,1:��,2:����,3:δ֪
	public int rightRearWindow;
	// topWindow: ��״̬(�촰) 0:�ر�,1:��,2:����,3:δ֪
	public int topWindow;
	// ecmSignal: �����ź�(ECM) 0:����,1:����,2:����,3:δ֪
	public int ecmSignal;
	// absSignal: �����ź�(ABS) 0:����,1:����,2:����,3:δ֪
	public int absSignal;
	// srsSignal: �����ź�(SRS) 0:����,1:����,2:����,3:δ֪
	public int srsSignal;
	// oilAlarmSignal: �����ź�(����) 0:����,1:����,2:����,3:δ֪
	public int oilAlarmSignal;
	// pressureSignal: �����ź�(̥ѹ) 0:����,1:����,2:����,3:δ֪
	public int pressureSignal;
	// healthSignal: �����ź�(����) 0:����,1:����,2:����,3:δ֪
	public int healthSignal;
	// airbag: ��ȫ����״̬ 0:����,1:����,2:����,3:δ֪
	public int airbag;
	// handBrake: ��ɲ״̬ 0:�ɿ�,1:����,2:����,3:δ֪
	public int handBrake;
	// braking: ɲ��״̬(��ɲ) 0:�ɿ�,1:̤��,2:����,3:δ֪
	public int braking;
	// driverSeatBelt: ��ȫ��(��ʻԱ) 0:δ��,1:�ѿ�,2:����,3:δ֪
	public int driverSeatBelt;
	// copilotSeatBelt: ��ȫ��(����) 0:δ��,1:�ѿ�,2:����,3:δ֪
	public int copilotSeatBelt;
	// accSignal: ACC �ź� 0:��Ч,1:��Ч,2:����,3:δ֪
	public int accSignal;
	// keySignal: Կ��״̬ 0:��Ч,1:��Ч,2:����,3:δ֪
	public int keySignal;
	// remoteSignal: ң���ź� 0��δ����1��������2��������3��β������
	// 4������������5������������6:������7��δ֪
	public int remoteSignal;
	// wiper: ���״̬ 0:�ر�,1:��,2:����,3:δ֪
	public int wiper;
	// airConditioner: �յ����� 0:�ر�,1:��,2:����,3:δ֪
	public int airConditioner;
	// carStalls: ��λ 1��P����2��R����3��N����4��D����
	public int carStalls;
	// reportTime: �ϱ���¼ʱ�� ��ʽ: YYYYMMDDHHMMSS
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
