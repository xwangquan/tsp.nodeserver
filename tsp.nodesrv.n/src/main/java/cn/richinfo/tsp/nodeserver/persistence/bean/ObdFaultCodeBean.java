package cn.richinfo.tsp.nodeserver.persistence.bean;

/**
 * @ClassName: ObdFaultCodeBean
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2015-11-6 ÏÂÎç4:45:03
 */
public class ObdFaultCodeBean {
	public byte type;
	public int id;
	public int nodeid;
	public String fault_code;
	public int fault_count;
	public String report_time;
	public String trip_id;
	public int uin;
	public String vtu_id;

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNodeid() {
		return nodeid;
	}

	public void setNodeid(int nodeid) {
		this.nodeid = nodeid;
	}

	public String getFault_code() {
		return fault_code;
	}

	public void setFault_code(String fault_code) {
		this.fault_code = fault_code;
	}

	public int getFault_count() {
		return fault_count;
	}

	public void setFault_count(int fault_count) {
		this.fault_count = fault_count;
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
