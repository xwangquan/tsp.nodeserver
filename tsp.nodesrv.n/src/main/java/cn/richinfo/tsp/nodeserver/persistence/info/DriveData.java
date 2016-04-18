package cn.richinfo.tsp.nodeserver.persistence.info;

import cn.richinfo.tsp.nodeserver.message.sutrans.DriveDataMessage;

/**
 * 
 * @ClassName: DriveDataInfo
 * @Description: map for {@link DriveDataMessage}
 * @author ��ȫ
 * @date 2014-11-21 ����3:16:17
 */
public class DriveData extends Json {

	// �ϴ����� 0�������ϴ���1����ѯ�ϴ�
	public byte type;

	// OBD������־λ BYTE[4] �ݱ���
	// public long obdWarn;
	// OBD״̬��־λ BYTE[4] 0x00000000�������ߣ�0x80000000�����ߣ�
	public long obdStatus;

	// ˲ʱ�ٶ� BYTE[2] ��λ��0.1km/h
	public float speed;
	// ������ת�� BYTE[2] ��ֵ��Χ��0 - 30000rpm
	public int engineRotationRate;
	// ��ƿ��ѹV BYTE[2] ��λ��0.1V
	public float batteryVoltage;

	// ��������� BYTE[4] ��λ��0.1km
	public float totalMileage;

	// ����˲ʱ�ͺ� BYTE[2] ��λ��0.1L/h
	public float idleConsumption;

	// ��ʻ˲ʱ�ͺ� BYTE[2] ��λ��0.1L/100km
	public float fuelConsumption;

	// ���������� BYTE[1] ��ֵ��Χ�� 0 - 100 %
	public byte engineLoad;

	// ��ȴҺ�¶� BYTE[2] ��ֵ��Χ: -60 �C 250 �� �з�������
	public int waterTemperature;

	// ȼ��ѹ�� BYTE[2] ��ֵ��Χ: 0 - 999 kPa
	public int fuelPressure;

	// ������ܾ���ѹ�� BYTE[2] ��ֵ��Χ: 0 �C 500 kPa
	public int manifoldPressure;

	// �����¶� BYTE[2] ��ֵ��Χ��-60 �C 250 �� �з�������
	public int inletTemperature;

	// �������� BYTE[2] ��ֵ��Χ: 0 �C 999 g/s
	public int inletTlow;

	// �����ž���λ�� BYTE[1] ��ֵ��Χ: 0 �C 100 %
	public byte tthrottlePosition;

	// �г�״̬ BYTE[1] 1���г��У�2��פ���У�3��Ϩ��
	public byte status;

	// ˲ʱ�ͺ� BYTE[2] ��λ��0.1L/H
	public float hourGallon;

	// ����̤�����λ�� BYTE[2] ��λ��0.01%
	public float pedalPosition;

	// ����̤��״̬ BYTE[1] 0���ɿ���1��̤��
	public byte pedalStatus;

	// ʣ��������%�� BYTE[2] ��λ��0.1%
	public float restOil;

	// ʱ�� BCD[6] ʱ���ʽ��YYMMDDhhmmss
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
