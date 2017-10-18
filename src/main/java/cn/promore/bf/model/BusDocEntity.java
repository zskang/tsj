package cn.promore.bf.model;

/**
 * 业务涉及文档表 Created by zskang on 2017/6/15.
 */
public class BusDocEntity {
	private int id;
	private String docName;
	private String docPath;
	private String docType;
	private String state;
	private String busId;
	private String createTime;
	private String createMan;

	@Override
	public String toString() {
		return "BusDocEntity{" + "id=" + id + ", docName='" + docName + '\'' + ", docPath='" + docPath + '\''
				+ ", docType='" + docType + '\'' + ", state='" + state + '\'' + ", busId='" + busId + '\''
				+ ", createTime='" + createTime + '\'' + ", createMan='" + createMan + '\'' + '}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateMan() {
		return createMan;
	}

	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}
}
