package cn.richinfo.tsp.nodeserver.message.serialization;

import java.util.ArrayList;

/**
 * @ClassName: CopyMessage
 * @Description: TODO()
 * @author ��ȫ
 * @date 2014-11-3 ����12:54:24
 */
public class CopyMessage extends NameServerSerializeMessage {

	private static final long serialVersionUID = 1L;
	String str = new String(
			"  ��������ð�ٵ���������֮�⣬΢��ҡһҡ����΢�Ÿ�����˵��������֮�������ھ���֮�У�����ƽҰţ��֮��"
					+ "�������߾���Ը���й������¶��������Ƕ��ӡ��������֣��������ݣ�Ը�����У��������ޡ�����˼���ǣ����ұ��¶�����֮������"
					+ "�����ھ���֮�У�������ԭҰ����ţ���Ⱥ֮�����ε����й��߾���ϣ�����й�һ�Ρ���������Ҳ�ǹ¶�һ�ˣ���į���ӣ���Ϊһ��֮����"
					+ "�������˶���������֮�֣�Ҳû��ʲô�����������֣�һ������һ���Ѿӣ�����ͼ޸��ң��������໥����");
	ArrayList<String> list = new ArrayList<String>();

	public CopyMessage() {
		for (int i = 0; i < 10000; i++) {
			list.add(str);
		}
	}
}
