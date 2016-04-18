package cn.richinfo.tsp.nodeserver.message.sutrans;

import cn.richinfo.tsp.nodeserver.utils.ConversionUtil;
import cn.richinfo.tsp.nodeserver.utils.SignUtils;

/**
 * @ClassName: DriveDataMessage
 * @Description: OBD�г�����
 * @author ��ȫ
 * @date 2014-11-19 ����4:21:14
 */
public class DriveDataMessage extends UploadTransparentTransmissionMessage {

	// ʱ�� BCD[6] ʱ���ʽ��YYMMDDhhmmss
	public byte[] time = new byte[6];

	// �ϴ����� 0�������ϴ���1����ѯ�ϴ�
	public byte type;

	// OBD������־λ BYTE[4] �ݱ���
	public byte[] obdWarn = new byte[4];
	// OBD״̬��־λ BYTE[4] 0x00000000�������ߣ�0x80000000�����ߣ�
	public byte[] obdStatus = new byte[4];

	// ˲ʱ�ٶ� BYTE[2] ��λ��0.1km/h
	public byte[] speed = new byte[2];;
	// ������ת�� BYTE[2] ��ֵ��Χ��0 - 30000rpm
	public byte[] engineRotationRate = new byte[2];;
	// ��ƿ��ѹV BYTE[2] ��λ��0.1V
	public byte[] batteryVoltage = new byte[2];;

	// ��������� BYTE[4] ��λ��0.1km
	public byte[] totalMileage = new byte[4];;

	// ����˲ʱ�ͺ� BYTE[2] ��λ��0.1L/h
	public byte[] idleConsumption = new byte[2];;

	// ��ʻ˲ʱ�ͺ� BYTE[2] ��λ��0.1L/100km
	public byte[] fuelConsumption = new byte[2];;

	// ���������� BYTE[1] ��ֵ��Χ�� 0 - 100 %
	public byte engineLoad;

	// ��ȴҺ�¶� BYTE[2] ��ֵ��Χ: -60 �C 250 �� �з�������
	public byte[] waterTemperature = new byte[2];;

	// ȼ��ѹ�� BYTE[2] ��ֵ��Χ: 0 - 999 kPa
	public byte[] fuelPressure = new byte[2];;

	// ������ܾ���ѹ�� BYTE[2] ��ֵ��Χ: 0 �C 500 kPa
	public byte[] manifoldPressure = new byte[2];;

	// �����¶� BYTE[2] ��ֵ��Χ��-60 �C 250 �� �з�������
	public byte[] inletTemperature = new byte[2];;

	// �������� BYTE[2] ��ֵ��Χ: 0 �C 999 g/s
	public byte[] inletTlow = new byte[2];;

	// �����ž���λ�� BYTE[1] ��ֵ��Χ: 0 �C 100 %
	public byte tthrottlePosition;

	// �г�״̬ BYTE[1] 1���г��У�2��פ���У�3��Ϩ��
	public byte status;

	// ˲ʱ�ͺ� BYTE[2] ��λ��0.1L/H
	public byte[] hourGallon = new byte[2];;

	// ����̤�����λ�� BYTE[2] ��λ��0.01%
	public byte[] pedalPosition = new byte[2];;

	// ����̤��״̬ BYTE[1] 0���ɿ���1��̤��
	public byte pedalStatus;

	// ʣ��������%�� BYTE[2] ��λ��0.1%
	public byte[] restOil = new byte[2];;

	// --inner
	private float UNIT = 0.1f;

	public DriveDataMessage() {
		super(SuTransTypes.DRIVEDATA);
	}

	private int SUBLEN = 49;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#hei(byte[], int)
	 */
	@Override
	public int hei(byte[] receivedData, int offset) throws Exception {
		System.arraycopy(receivedData, offset, time, 0, 6);
		type = receivedData[offset + 6];
		System.arraycopy(receivedData, offset + 7, obdWarn, 0, 4);
		System.arraycopy(receivedData, offset + 11, obdStatus, 0, 4);
		System.arraycopy(receivedData, offset + 15, speed, 0, 2);
		System.arraycopy(receivedData, offset + 17, engineRotationRate, 0, 2);
		System.arraycopy(receivedData, offset + 19, batteryVoltage, 0, 2);
		System.arraycopy(receivedData, offset + 21, totalMileage, 0, 4);
		System.arraycopy(receivedData, offset + 25, idleConsumption, 0, 2);
		System.arraycopy(receivedData, offset + 27, fuelConsumption, 0, 2);
		engineLoad = receivedData[offset + 29];
		System.arraycopy(receivedData, offset + 30, waterTemperature, 0, 2);
		System.arraycopy(receivedData, offset + 32, fuelPressure, 0, 2);
		System.arraycopy(receivedData, offset + 34, manifoldPressure, 0, 2);
		System.arraycopy(receivedData, offset + 36, inletTemperature, 0, 2);
		System.arraycopy(receivedData, offset + 38, inletTlow, 0, 2);
		tthrottlePosition = receivedData[offset + 40];
		status = receivedData[offset + 41];
		System.arraycopy(receivedData, offset + 42, hourGallon, 0, 2);
		System.arraycopy(receivedData, offset + 44, pedalPosition, 0, 2);
		pedalStatus = receivedData[offset + 46];
		System.arraycopy(receivedData, offset + 47, restOil, 0, 2);
		return offset + SUBLEN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.richinfo.tsp.nodeserver.message.sutrans.
	 * UploadTransparentTransmissionMessage#xiu()
	 */
	@Override
	public byte[] xiu() {
		byte[] send = new byte[SUBLEN];
		System.arraycopy(time, 0, send, 0, 6);
		send[6] = type;
		System.arraycopy(obdWarn, 0, send, 7, 4);
		System.arraycopy(obdStatus, 0, send, 11, 4);
		System.arraycopy(speed, 0, send, 15, 2);
		System.arraycopy(engineRotationRate, 0, send, 17, 2);
		System.arraycopy(batteryVoltage, 0, send, 19, 2);
		System.arraycopy(totalMileage, 0, send, 21, 4);
		System.arraycopy(idleConsumption, 0, send, 25, 2);
		System.arraycopy(fuelConsumption, 0, send, 27, 2);
		send[29] = engineLoad;
		System.arraycopy(waterTemperature, 0, send, 30, 2);
		System.arraycopy(fuelPressure, 0, send, 32, 2);
		System.arraycopy(manifoldPressure, 0, send, 34, 2);
		System.arraycopy(inletTemperature, 0, send, 36, 2);
		System.arraycopy(inletTlow, 0, send, 38, 2);
		send[40] = tthrottlePosition;
		send[41] = status;
		System.arraycopy(hourGallon, 0, send, 42, 2);
		System.arraycopy(pedalPosition, 0, send, 44, 2);
		send[46] = pedalStatus;
		System.arraycopy(restOil, 0, send, 47, 2);
		return send;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append("],BODY[");
		sb.append("time=").append(getTime()).append("| ");
		sb.append("obdWarn=").append(getObdWarn()).append("| ");
		sb.append("obdStatus=").append(getObdStatus()).append("| ");
		sb.append("speed=").append(getSpeed()).append("| ");
		sb.append("engineRotationRate=").append(getEngineRotationRate())
				.append("| ");
		sb.append("batteryVoltage=").append(getBatteryVoltage()).append("| ");
		sb.append("totalMileage=").append(getTotalMileage()).append("| ");
		sb.append("idleConsumption=").append(getIdleConsumption()).append("| ");
		sb.append("fuelConsumption=").append(getFuelConsumption()).append("| ");
		sb.append("engineLoad =").append(engineLoad).append("| ");
		sb.append("waterTemperature=").append(getWaterTemperature())
				.append("| ");
		sb.append("fuelPressure=").append(getFuelPressure()).append("| ");
		sb.append("manifoldPressure=").append(getManifoldPressure())
				.append("| ");
		sb.append("inletTemperature=").append(getInletTemperature())
				.append("| ");
		sb.append("inletTlow=").append(getInletTlow()).append("| ");
		sb.append("tthrottlePosition =").append(tthrottlePosition).append("| ");
		sb.append("status =").append(status).append("| ");
		sb.append("hourGallon=").append(getHourGallon()).append("| ");
		sb.append("pedalPosition=").append(getPedalPosition()).append("| ");
		sb.append("pedalStatus =").append(getPedalStatus()).append("| ");
		sb.append("restOil=").append(getRestOil()).append("]}");
		return sb.toString();
	}

	// ----------------------------get set method

	public long getTime() {
		return ConversionUtil.bcd2long(time);
	}

	public void setTime(byte[] time) {
		this.time = time;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public long getObdWarn() {
		return SignUtils.getUnsignedIntt(ConversionUtil.byte2int(obdWarn));
	}

	public void setObdWarn(byte[] obdWarn) {
		this.obdWarn = obdWarn;
	}

	public long getObdStatus() {
		return SignUtils.getUnsignedIntt(ConversionUtil.byte2int(obdStatus));
	}

	public void setObdStatus(byte[] obdStatus) {
		this.obdStatus = obdStatus;
	}

	public float getSpeed() {
		return SignUtils.getUnsignedByte(ConversionUtil.byte2short(speed))
				* UNIT;
	}

	public void setSpeed(byte[] speed) {
		this.speed = speed;
	}

	public int getEngineRotationRate() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(engineRotationRate));
	}

	public void setEngineRotationRate(byte[] engineRotationRate) {
		this.engineRotationRate = engineRotationRate;
	}

	public float getBatteryVoltage() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(batteryVoltage)) * UNIT;
	}

	public void setBatteryVoltage(byte[] batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public float getTotalMileage() {
		return SignUtils.getUnsignedIntt(ConversionUtil.byte2int(totalMileage))
				* UNIT;
	}

	public void setTotalMileage(byte[] totalMileage) {
		this.totalMileage = totalMileage;
	}

	public float getIdleConsumption() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(idleConsumption)) * UNIT;
	}

	public void setIdleConsumption(byte[] idleConsumption) {
		this.idleConsumption = idleConsumption;
	}

	public float getFuelConsumption() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(fuelConsumption)) * UNIT;
	}

	public void setFuelConsumption(byte[] fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public byte getEngineLoad() {
		return engineLoad;
	}

	public void setEngineLoad(byte engineLoad) {
		this.engineLoad = engineLoad;
	}

	public int getWaterTemperature() {
		return ConversionUtil.byte2short(waterTemperature);
	}

	public void setWaterTemperature(byte[] waterTemperature) {
		this.waterTemperature = waterTemperature;
	}

	public int getFuelPressure() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(fuelPressure));
	}

	public void setFuelPressure(byte[] fuelPressure) {
		this.fuelPressure = fuelPressure;
	}

	public int getManifoldPressure() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(manifoldPressure));
	}

	public void setManifoldPressure(byte[] manifoldPressure) {
		this.manifoldPressure = manifoldPressure;
	}

	public int getInletTemperature() {
		return ConversionUtil.byte2short(inletTemperature);
	}

	public void setInletTemperature(byte[] inletTemperature) {
		this.inletTemperature = inletTemperature;
	}

	public int getInletTlow() {
		return SignUtils.getUnsignedByte(ConversionUtil.byte2short(inletTlow));
	}

	public void setInletTlow(byte[] inletTlow) {
		this.inletTlow = inletTlow;
	}

	public byte getTthrottlePosition() {
		return tthrottlePosition;
	}

	public void setTthrottlePosition(byte tthrottlePosition) {
		this.tthrottlePosition = tthrottlePosition;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public float getHourGallon() {
		return SignUtils.getUnsignedByte(ConversionUtil.byte2short(hourGallon))
				* UNIT;
	}

	public void setHourGallon(byte[] hourGallon) {
		this.hourGallon = hourGallon;
	}

	public float getPedalPosition() {
		return SignUtils.getUnsignedByte(ConversionUtil
				.byte2short(pedalPosition)) * UNIT;
	}

	public void setPedalPosition(byte[] pedalPosition) {
		this.pedalPosition = pedalPosition;
	}

	public byte getPedalStatus() {
		return pedalStatus;
	}

	public void setPedalStatus(byte pedalStatus) {
		this.pedalStatus = pedalStatus;
	}

	public float getRestOil() {
		return SignUtils.getUnsignedByte(ConversionUtil.byte2short(restOil))
				* UNIT;
	}

	public void setRestOil(byte[] restOil) {
		this.restOil = restOil;
	}

}
