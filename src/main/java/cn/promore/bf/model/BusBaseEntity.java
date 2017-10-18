package cn.promore.bf.model;

/**
 * 业务实体类
 *
 * @author : zg
 * @date 2017年5月11日 下午9:30:53
 */
public class BusBaseEntity {

	private Integer id; // 编号

	private String remark1; // 备注1

	private String bgbh;

	private String remark2; // 备注2

	private String ywName;// ywName

	private int parentId; // 计划id

	private String jsgljdfl;//技术管理交底分类
	
	private String wdbh;// 文档编号

	private String gcdwmc;// 工程单位名称

	private String fbfxmc;// 分部分项名称

	private String jsjdmc;// 技术交底名称

	private String zrrenid;// 任务责任人

	private String lxfs;// 联系方式

	private String jhwcsj1;// 计划完成时间 主流程

	private String jhwcsj2;// 计划完成时间 子流程
	private String sjwcsj2;// 实际 完成时间 子流程

	private String sjwcsj;// 实际完成时间

	private String jhkssj;// 计划开始时间

	private String sjkssj;// 实际开始时间
	
	private String bjjr; //被交接人id

	private String jcsj;// 检查时间

	private String wfKey;// 业务实体类型

	private String moduleId;// 模块id

	private String state; // 审核状态 未提交 审核中 审核通过 审核未通过

	private String processInstanceId; // 流程实例Id

	private String projectId;// 项目id

	private String projectName;// 项目名称

	private String projectType;// 项目类型

	private String userid; // 申请人id

	private String pubTime;

	private String jdsj;

	private String taskExcuter;

	private String dqczr;

	private int nodes;

	private String ziping=" ";// 自评

	private String xmjlb=" ";// 项目经理部

	private String jianli=" ";// 监理

	private String yezhu=" ";// 业主

	private String zigs=" ";// 子（分）公司

	private String gsi=" "; // 公司

	private String gfgs=" ";// 股份公司

	private String sfjd="";// 是否交底

	private String sfff="";// 是否发放

	private String sftz=""; // 是否调整

	private String sfgfxznd;
	private String fadj;
	private String fwdw;

	@Override
	public String toString() {
		return "BusBaseEntity [id=" + id + ", remark1=" + remark1 + ", bgbh=" + bgbh + ", remark2=" + remark2
				+ ", ywName=" + ywName + ", parentId=" + parentId + ", wdbh=" + wdbh + ", gcdwmc=" + gcdwmc
				+ ", jsjdmc=" + jsjdmc + ", zrrenid=" + zrrenid + ", lxfs=" + lxfs + ", jhwcsj1=" + jhwcsj1
				+ ", jhwcsj2=" + jhwcsj2 + ", sjwcsj=" + sjwcsj + ", jhkssj=" + jhkssj + ", sjkssj=" + sjkssj
				+ ", jcsj=" + jcsj + ", wfKey=" + wfKey + ", moduleId=" + moduleId + ", state=" + state
				+ ", processInstanceId=" + processInstanceId + ", projectId=" + projectId + ", projectName="
				+ projectName + ", projectType=" + projectType + ", userid=" + userid + ", pubTime=" + pubTime
				+ ", jdsj=" + jdsj + ", taskExcuter=" + taskExcuter + ", dqczr=" + dqczr + ", nodes=" + nodes
				+ ", ziping=" + ziping + ", xmjlb=" + xmjlb + ", jianli=" + jianli + ", yezhu=" + yezhu + ", zigs="
				+ zigs + ", gsi=" + gsi + ", gfgs=" + gfgs + ", sfjd=" + sfjd + ", sfff=" + sfff + ", sftz=" + sftz
				+ ", sfgfxznd=" + sfgfxznd + ", fadj=" + fadj + ", fwdw=" + fwdw + "]";
	}

	public String getSfgfxznd() {
		return sfgfxznd;
	}

	public void setSfgfxznd(String sfgfxznd) {
		this.sfgfxznd = sfgfxznd;
	}

	public String getFadj() {
		return fadj;
	}

	public void setFadj(String fadj) {
		this.fadj = fadj;
	}

	public String getJhkssj() {
		return jhkssj;
	}

	public void setJhkssj(String jhkssj) {
		this.jhkssj = jhkssj;
	}

	public String getSjkssj() {
		return sjkssj;
	}

	public void setSjkssj(String sjkssj) {
		this.sjkssj = sjkssj;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getWdbh() {
		return wdbh;
	}

	public void setWdbh(String wdbh) {
		this.wdbh = wdbh;
	}

	public String getGcdwmc() {
		return gcdwmc;
	}

	public void setGcdwmc(String gcdwmc) {
		this.gcdwmc = gcdwmc;
	}

	public String getJsjdmc() {
		return jsjdmc;
	}

	public String getZiping() {
		return ziping;
	}

	public void setZiping(String ziping) {
		this.ziping = ziping;
	}

	public String getXmjlb() {
		return xmjlb;
	}

	public void setXmjlb(String xmjlb) {
		this.xmjlb = xmjlb;
	}

	public String getJianli() {
		return jianli;
	}

	public void setJianli(String jianli) {
		this.jianli = jianli;
	}

	public String getYezhu() {
		return yezhu;
	}

	public void setYezhu(String yezhu) {
		this.yezhu = yezhu;
	}

	public String getZigs() {
		return zigs;
	}

	public void setZigs(String zigs) {
		this.zigs = zigs;
	}

	public String getGsi() {
		return gsi;
	}

	public void setGsi(String gsi) {
		this.gsi = gsi;
	}

	public String getGfgs() {
		return gfgs;
	}

	public void setGfgs(String gfgs) {
		this.gfgs = gfgs;
	}

	public String getSfjd() {
		return sfjd;
	}

	public void setSfjd(String sfjd) {
		this.sfjd = sfjd;
	}

	public String getSfff() {
		return sfff;
	}

	public void setSfff(String sfff) {
		this.sfff = sfff;
	}

	public String getSftz() {
		return sftz;
	}

	public void setSftz(String sftz) {
		this.sftz = sftz;
	}

	public void setJsjdmc(String jsjdmc) {
		this.jsjdmc = jsjdmc;
	}

	public String getZrrenid() {
		return zrrenid;
	}

	public void setZrrenid(String zrrenid) {
		this.zrrenid = zrrenid;
	}

	public String getLxfs() {
		return lxfs;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}

	public String getJhwcsj1() {
		return jhwcsj1;
	}

	public void setJhwcsj1(String jhwcsj1) {
		this.jhwcsj1 = jhwcsj1;
	}

	public String getJhwcsj2() {
		return jhwcsj2;
	}

	public void setJhwcsj2(String jhwcsj2) {
		this.jhwcsj2 = jhwcsj2;
	}

	public String getSjwcsj() {
		return sjwcsj;
	}

	public void setSjwcsj(String sjwcsj) {
		this.sjwcsj = sjwcsj;
	}

	public String getJcsj() {
		return jcsj;
	}

	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getJdsj() {
		return jdsj;
	}

	public void setJdsj(String jdsj) {
		this.jdsj = jdsj;
	}

	public String getTaskExcuter() {
		return taskExcuter;
	}

	public void setTaskExcuter(String taskExcuter) {
		this.taskExcuter = taskExcuter;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public int getNodes() {
		return nodes;
	}

	public void setNodes(int nodes) {
		this.nodes = nodes;
	}

	public String getWfKey() {
		return wfKey;
	}

	public void setWfKey(String wfKey) {
		this.wfKey = wfKey;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public String getYwName() {
		return ywName;
	}

	public void setYwName(String ywName) {
		this.ywName = ywName;
	}

	public String getBgbh() {
		return bgbh;
	}

	public void setBgbh(String bgbh) {
		this.bgbh = bgbh;
	}

	public String getFwdw() {
		return fwdw;
	}

	public void setFwdw(String fwdw) {
		this.fwdw = fwdw;
	}

	public String getDqczr() {
		return dqczr;
	}

	public void setDqczr(String dqczr) {
		this.dqczr = dqczr;
	}

	public String getSjwcsj2() {
		return sjwcsj2;
	}

	public void setSjwcsj2(String sjwcsj2) {
		this.sjwcsj2 = sjwcsj2;
	}

	public String getFbfxmc() {
		return fbfxmc;
	}

	public void setFbfxmc(String fbfxmc) {
		this.fbfxmc = fbfxmc;
	}

	public String getBjjr() {
		return bjjr;
	}

	public void setBjjr(String bjjr) {
		this.bjjr = bjjr;
	}

	public String getJsgljdfl() {
		return jsgljdfl;
	}

	public void setJsgljdfl(String jsgljdfl) {
		this.jsgljdfl = jsgljdfl;
	}

}
