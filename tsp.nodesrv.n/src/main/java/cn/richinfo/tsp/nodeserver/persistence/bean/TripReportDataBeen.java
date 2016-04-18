package cn.richinfo.tsp.nodeserver.persistence.bean;

import cn.richinfo.tsp.nodeserver.message.sutrans.TripReportDataMessage;

/**
 * @ClassName: TripReportDataBeen
 * @Description: TODO()
 * @author ÍõÈ«
 * @date 2015-11-6 ÏÂÎç5:38:40
 */
public class TripReportDataBeen {
	public TripReportDataMessage message;
	public int id;
	public int nodeid;
	public String trip_id;
	public int uin;
	public String vtu_id;

	public TripReportDataMessage getMessage() {
		return message;
	}

	public void setMessage(TripReportDataMessage message) {
		this.message = message;
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
