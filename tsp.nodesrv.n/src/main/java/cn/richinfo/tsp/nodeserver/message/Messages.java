package cn.richinfo.tsp.nodeserver.message;

/**
 * @ClassName: Messages
 * @Description: 终端消息ID列表
 * @author 王全
 * @date 2014-7-30 下午1:10:50
 */
public class Messages {
	// 终端心跳
	public static final int TERMINAL_HEART_REQ = 0x0002;

	// 车机短信验证码请求消息
	public static final int TERMINAL_CAR_VTU_REQ = 0x101;

	// 车机申请短信验证码应答
	public static final int TERMINAL_CAR_VTU_RESP = 0x8101;

	// 车机/OBD注册请求
	public static final int TERMINAL_REGISTER_REQ = 0x0100;

	// 车机/OBD注册应答
	public static final int TERMINAL_REGISTER_RESP = 0x8100;

	// 终端注销
	public static final int TERMINAL_LOGOFF_REQ = 0x0003;

	// 终端查询节点服务器 请求
	public static final int TERMINAL_GET_NODES_REQ = 0x0103;

	// 终端查询节点服务器 请求
	public static final int TERMINAL_GET_NODES_RESP = 0x8103;

	// 9.14. 查询终端参数
	public static final int TERMINAL_QUERY_REQ = 0x8104;

	// 9.15. 查询终端参数应答
	public static final int TERMINAL_QUERY_RESP = 0x0104;

	// 9.11. 设置终端参数
	public static final int PLATFORM_SET_TERMINAL_ITEM_REQ = 0x8106;

	// 终端升级 9.16. 终端控制
	public static final int TERMINAL_CONTROL_REQ = 0x8105;

	// 终端升级--> 终端请求升级文件信息
	public static final int UPDATE_TERMINAL_REQ_INFO = 0x301;

	// 终端升级-->平台发送升级文件信息
	public static final int UPDATE_PLATFORM_RESP_INFO = 0x8301;

	// 终端升级-->终端请求升级包数据
	public static final int UPDATE_TERMINAL_REQ_DATA = 0x302;

	// 终端升级--> 平台下发升级包数据
	public static final int UPDATE_PLATFORM_RESP_DATA = 0x8302;

	// 终端升级-->终端通知平台升级结果
	public static final int UPDATE_TERMINAL_REQ_RESULT = 0x303;

	// 9.17. 数据下行透传
	public static final int TT_DOWNLOAD_REQ = 0x8900;
	// 9.18. 数据上行透传
	public static final int TT_UPLOAD_REQ = 0x0900;
	//9.20.	多包上行透传
	public static final int MULTI_UPLOAD_REQ = 0x0999;
	// 9.2. 平台通用应答
	public static final int COMMON_PLANTFORM_RESP = 0x8001;
	// 9.1. 终端通用应答
	public static final int COMMON_TERMINAL_RESP = 0x0001;

	// -----------------admin message id
	// 请求配置
	public static final int ADMIN_PROPERTIES_REQ = 0x9001;
	// 配置响应
	public static final int ADMIN_PROPERTIES_RESP = 0x9100;

	// server 状态请求
	public static final int ADMIN_STATUS_REQ = 0x9002;
	// server 状态应答
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
