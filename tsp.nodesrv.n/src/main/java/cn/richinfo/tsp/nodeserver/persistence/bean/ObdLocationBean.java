package cn.richinfo.tsp.nodeserver.persistence.bean;

/**
 * @ClassName: ObdLocationBean
 * @Description: TODO()
 * @author ��ȫ
 * @date 2015-8-11 ����4:01:05
 */
public class ObdLocationBean {
	public String car_position_info;
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
	public void setId(int id) {
		this.id = id;
	}
	public String getCar_position_info() {
		return car_position_info;
	}

	public void setCar_position_info(String car_position_info) {
		this.car_position_info = car_position_info;
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
