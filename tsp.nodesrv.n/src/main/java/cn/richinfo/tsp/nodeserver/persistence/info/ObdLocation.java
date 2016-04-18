package cn.richinfo.tsp.nodeserver.persistence.info;

import cn.richinfo.tsp.nodeserver.message.sutrans.ObdLocationMessage;

/**
 * @ClassName: ObdLocation
 * @Description: map for {@link ObdLocationMessage}
 * @author ��ȫ
 * @date 2015-8-11 ����3:55:31
 */
public class ObdLocation extends Json {

	// ʱ�� BCD[6] ʱ���ʽ��YYMMDDhhmmss
	public long time;

	// �ϴ����� BYTE 0�������ϴ���1����ѯ�ϴ�
	public byte type;

	// ״̬ DWORD ״̬λ�����ͼ7
	public int status;

	// γ�� DWORD �Զ�Ϊ��λ��γ��ֵ����10�����η�����ȷ�������֮һ��
	public double lattitude;

	// ���� DWORD �Զ�Ϊ��λ�ľ���ֵ����10�����η�����ȷ�������֮һ��
	public double longitude;

	// �߳� WORD ���θ߶ȣ���λΪ��
	public int height;

	// �ٶ� WORD 1/10km/h
	public int speed;

	// ���� WORD 0-359�㣬����Ϊ0��˳ʱ��
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
