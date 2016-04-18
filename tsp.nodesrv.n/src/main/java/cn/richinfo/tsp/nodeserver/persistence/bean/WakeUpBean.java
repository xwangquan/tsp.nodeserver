package cn.richinfo.tsp.nodeserver.persistence.bean;

/**
 * @ClassName: ObdLocationBean
 * @Description: TODO()
 * @author 王全
 * @date 2015-8-11 下午4:01:05
 */
public class WakeUpBean {

	public byte type;
	public int status;
	public int longitude;
	public int lattitude;
	// 电瓶电压 WORD 单位：0.1V
	public float batteryVoltage;
	// 熄火时长 DWORD 单位：秒
	public int duration;

	public String create_time;
	public int id;
	public int nodeid;
	public String report_time;
	public String trip_id;
	public int uin;
	public String vtu_id;

	public int getId() {
		return id;
	}

	public byte getType() {
		return type;
	}

	public float getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(float batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getStatus() {
		return status;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getGsp_status() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if ((status & 1) == 1)
			sb.append("status:").append("1,");
		else
			sb.append("status:").append("0,");

		if ((status >> 1 & 1) == 1)
			sb.append("latType:").append("1,");
		else
			sb.append("latType:").append("0,");

		if ((status >> 2 & 1) == 1)
			sb.append("lonType").append("1");
		else
			sb.append("lonType").append("0");

		sb.append("}");
		return sb.toString();
	}

	public void setStatus(int gsp_status) {
		this.status = gsp_status;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLattitude() {
		return lattitude;
	}

	public void setLattitude(int lattitude) {
		this.lattitude = lattitude;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getNodeid() {
		return nodeid;
	}

	public void setNodeid(int nodeid) {
		this.nodeid = nodeid;
	}

	public String getReport_time() {
		return report_time;
	}

	public void setReport_time(String report_time) {
		this.report_time = report_time;
	}

	public String getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(String trip_id) {
		this.trip_id = trip_id;
	}

	public int getUin() {
		return uin;
	}

	public void setUin(int uin) {
		this.uin = uin;
	}

	public String getVtu_id() {
		return vtu_id;
	}

	public void setVtu_id(String vtu_id) {
		this.vtu_id = vtu_id;
	}

}
