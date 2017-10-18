package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * 系统日志
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

@Entity
@Table(name="sys_log")
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Log implements Serializable{
	
	private static final long serialVersionUID = -948695562123477580L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false)
	private String username;
	@Column(nullable=false)
	private String clientIp;
	private String operateModule;
	private String operateModuleName;
	private String operateType;
	private Date operateTime;
	@Column(length=4000)
	private String operateContent;
	private String operateResult;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getOperateModule() {
		return operateModule;
	}
	public void setOperateModule(String operateModule) {
		this.operateModule = operateModule;
	}
	
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperateContent() {
		return operateContent;
	}
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	public String getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}
	public String getOperateModuleName() {
		return operateModuleName;
	}
	public void setOperateModuleName(String operateModuleName) {
		this.operateModuleName = operateModuleName;
	}
	@Override
	public String toString() {
		return "日志 [编码=" + id + ", 用户=" + username + ", IP=" + clientIp + ", 操作模块=" + operateModule + ", 模块中文名=" + operateModuleName + ", 操作类型=" + operateType + ", 操作时间=" + operateTime + ", 操作内容=" + operateContent + ", 操作结果=" + operateResult + "]";
	}
	
}
