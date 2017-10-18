package cn.promore.bf.model;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class MyTask {

	private String id;
	private String name;
	private String userId;
	private Date createTime;
	private String state;
	private String startTime;
	private Date endTime;
	private String endTime2;
	private String title;
	private String chinesename;
	private String procInstId;

	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
  
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
 

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEndTime2() {
		return endTime2;
	}

	public void setEndTime2(String endTime2) {
		this.endTime2 = endTime2;
	}

	@Override
	public String toString() {
		return "MyTask [id=" + id + ", name=" + name + ", userId=" + userId + ", createTime=" + createTime + ", state="
				+ state + ", startTime=" + startTime + ", endTime=" + endTime + ", title=" + title + ", chinesename="
				+ chinesename + ", procInstId=" + procInstId + "]";
	}
	
	

}
