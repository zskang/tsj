package cn.promore.bf.model;

public class SysActModuleEntity {
	private Integer id;

	private String developKey;

	private String moduleId; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getDevelopKey() {
		return developKey;
	}

	public void setDevelopKey(String developKey) {
		this.developKey = developKey;
	}

	@Override
	public String toString() {
		return "SysActModuleEntity [id=" + id + ", developKey=" + developKey + ", moduleId=" + moduleId + "]";
	}

}
