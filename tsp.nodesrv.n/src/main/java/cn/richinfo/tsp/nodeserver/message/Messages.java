package cn.richinfo.tsp.nodeserver.message;

/**
 * @ClassName: Messages
 * @Description: �ն���ϢID�б�
 * @author ��ȫ
 * @date 2014-7-30 ����1:10:50
 */
public class Messages {
	// �ն�����
	public static final int TERMINAL_HEART_REQ = 0x0002;

	// ����������֤��������Ϣ
	public static final int TERMINAL_CAR_VTU_REQ = 0x101;

	// �������������֤��Ӧ��
	public static final int TERMINAL_CAR_VTU_RESP = 0x8101;

	// ����/OBDע������
	public static final int TERMINAL_REGISTER_REQ = 0x0100;

	// ����/OBDע��Ӧ��
	public static final int TERMINAL_REGISTER_RESP = 0x8100;

	// �ն�ע��
	public static final int TERMINAL_LOGOFF_REQ = 0x0003;

	// �ն˲�ѯ�ڵ������ ����
	public static final int TERMINAL_GET_NODES_REQ = 0x0103;

	// �ն˲�ѯ�ڵ������ ����
	public static final int TERMINAL_GET_NODES_RESP = 0x8103;

	// 9.14. ��ѯ�ն˲���
	public static final int TERMINAL_QUERY_REQ = 0x8104;

	// 9.15. ��ѯ�ն˲���Ӧ��
	public static final int TERMINAL_QUERY_RESP = 0x0104;

	// 9.11. �����ն˲���
	public static final int PLATFORM_SET_TERMINAL_ITEM_REQ = 0x8106;

	// �ն����� 9.16. �ն˿���
	public static final int TERMINAL_CONTROL_REQ = 0x8105;

	// �ն�����--> �ն����������ļ���Ϣ
	public static final int UPDATE_TERMINAL_REQ_INFO = 0x301;

	// �ն�����-->ƽ̨���������ļ���Ϣ
	public static final int UPDATE_PLATFORM_RESP_INFO = 0x8301;

	// �ն�����-->�ն���������������
	public static final int UPDATE_TERMINAL_REQ_DATA = 0x302;

	// �ն�����--> ƽ̨�·�����������
	public static final int UPDATE_PLATFORM_RESP_DATA = 0x8302;

	// �ն�����-->�ն�֪ͨƽ̨�������
	public static final int UPDATE_TERMINAL_REQ_RESULT = 0x303;

	// 9.17. ��������͸��
	public static final int TT_DOWNLOAD_REQ = 0x8900;
	// 9.18. ��������͸��
	public static final int TT_UPLOAD_REQ = 0x0900;
	//9.20.	�������͸��
	public static final int MULTI_UPLOAD_REQ = 0x0999;
	// 9.2. ƽ̨ͨ��Ӧ��
	public static final int COMMON_PLANTFORM_RESP = 0x8001;
	// 9.1. �ն�ͨ��Ӧ��
	public static final int COMMON_TERMINAL_RESP = 0x0001;

	// -----------------admin message id
	// ��������
	public static final int ADMIN_PROPERTIES_REQ = 0x9001;
	// ������Ӧ
	public static final int ADMIN_PROPERTIES_RESP = 0x9100;

	// server ״̬����
	public static final int ADMIN_STATUS_REQ = 0x9002;
	// server ״̬Ӧ��
	public static final int ADMIN_STATUS_RESP = 0x9200;
	// sms recover
	public static final int ADMIN_SMS_RECOVER_REQ = 0x9006;
	// sms test
	public static final int ADMIN_SMS_CHECKER_REQ = 0x9007;
	// serialization message
	public static final int SERVER_OBJECT_SERIALIZE_MESSAGE = 0x9008;
	//server echo message
	public static final int SERVER_ECHO_MESAGE = 0x9808;
	


}
