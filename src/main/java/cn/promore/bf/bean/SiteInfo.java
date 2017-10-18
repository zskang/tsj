package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * 站点信息
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_siteinfo")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class SiteInfo implements Serializable{
	
	private static final long serialVersionUID = 1943978858792063665L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(unique=true)
	private String systemName;
	@Column(unique=true)
	private String systemEnName;
	@Column(unique=true)
	private String systemAddress;
	private String companyName;
	@Temporal(TemporalType.DATE)
	@Column(nullable=false,updatable=false)
	private Date createTime = new Date();
	private String ipcNo;
	private boolean watermark;
	private String watermarkContent;
	
	private Boolean useTabs = true;
	private String  theme;
	private Integer validateCodeType;
	private Integer loginFailLockTimes;
	private Boolean showValidateCode;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getSystemAddress() {
		return systemAddress;
	}
	public void setSystemAddress(String systemAddress) {
		this.systemAddress = systemAddress;
	}
	public String getIpcNo() {
		return ipcNo;
	}
	public void setIpcNo(String ipcNo) {
		this.ipcNo = ipcNo;
	}

	public String getWatermarkContent() {
		return watermarkContent;
	}
	public void setWatermarkContent(String watermarkContent) {
		this.watermarkContent = watermarkContent;
	}
	public boolean isWatermark() {
		return watermark;
	}
	public void setWatermark(boolean watermark) {
		this.watermark = watermark;
	}
	public String getSystemEnName() {
		return systemEnName;
	}
	public void setSystemEnName(String systemEnName) {
		this.systemEnName = systemEnName;
	}
	public Boolean getUseTabs() {
		return useTabs;
	}
	public void setUseTabs(Boolean useTabs) {
		this.useTabs = useTabs;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public Integer getValidateCodeType() {
		return validateCodeType;
	}
	public void setValidateCodeType(Integer validateCodeType) {
		this.validateCodeType = validateCodeType;
	}
	public Integer getLoginFailLockTimes() {
		return loginFailLockTimes;
	}
	public void setLoginFailLockTimes(Integer loginFailLockTimes) {
		this.loginFailLockTimes = loginFailLockTimes;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Boolean getShowValidateCode() {
		return showValidateCode;
	}
	public void setShowValidateCode(Boolean showValidateCode) {
		this.showValidateCode = showValidateCode;
	}
	@Override
	public String toString() {
		return "站点设置 [编码=" + id + ", 系统中文名称=" + systemName + ", 系统英文名称=" + systemEnName + ", 系统地址=" + systemAddress + ", 备案号=" + ipcNo + ", 水印=" + watermark + ", 水印内容=" + watermarkContent + ", 是否使用Tabs=" + useTabs + ", 主题=" + theme + ", 验证码类型=" + validateCodeType + ", 登录失败次数锁定=" + loginFailLockTimes + ", 公司名称："+companyName+"，系统创建时间："+createTime+",是否显示登录验证码="+showValidateCode+"]";
	}
}
