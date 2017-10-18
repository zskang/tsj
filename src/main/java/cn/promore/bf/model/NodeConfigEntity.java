package cn.promore.bf.model;

public class NodeConfigEntity {
	private int id;
	private String nodeName;
	private String nodeid;
	private String wfkey;

	private String datestr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getWfkey() {
		return wfkey;
	}

	public void setWfkey(String wfkey) {
		this.wfkey = wfkey;
	}

	public String getDatestr() {
		return datestr;
	}

	public void setDatestr(String datestr) {
		this.datestr = datestr;
	}

}
