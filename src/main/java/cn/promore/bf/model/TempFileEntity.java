package cn.promore.bf.model;

public class TempFileEntity {
	private int id;

	private String projectId;

	private String moduleId;

	private String fileName;

	private String filePath;

	private String srcFilePath;

	private String createTime;

	private String lstUpdateTime;

	private String createMan;

	private String lstUpdateMan;

	private String fileType;

	private String ywid;

	private String state;
	
	private String wfKey;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLstUpdateTime() {
		return lstUpdateTime;
	}

	public void setLstUpdateTime(String lstUpdateTime) {
		this.lstUpdateTime = lstUpdateTime;
	}

	public String getCreateMan() {
		return createMan;
	}

	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}

	public String getLstUpdateMan() {
		return lstUpdateMan;
	}

	public void setLstUpdateMan(String lstUpdateMan) {
		this.lstUpdateMan = lstUpdateMan;
	}

	@Override
	public String toString() {
		return "TempFileEntity [id=" + id + ", projectId=" + projectId + ", moduleId=" + moduleId + ", fileName=" + fileName + ", filePath=" + filePath + ", createTime=" + createTime
				+ ", lstUpdateTime=" + lstUpdateTime + ", createMan=" + createMan + ", lstUpdateMan=" + lstUpdateMan + "]";
	}

	public String getSrcFilePath() {
		return srcFilePath;
	}

	public void setSrcFilePath(String srcFilePath) {
		this.srcFilePath = srcFilePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getYwid() {
		return ywid;
	}

	public void setYwid(String ywid) {
		this.ywid = ywid;
	}

	public String getWfKey() {
		return wfKey;
	}

	public void setWfKey(String wfKey) {
		this.wfKey = wfKey;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
