package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * 用户类
 */
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name="sys_user")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class User implements Serializable{
	private static final long serialVersionUID = 417291825096305135L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(unique=true)
	private String username;
	@Column(nullable=false)
	private String password;
	private String chinesename;
	private String telephone;
	private String mobilephone;
	private String email;
	private Integer status;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="sys_user_role",joinColumns={@JoinColumn(name="user_id")},inverseJoinColumns={@JoinColumn(name="role_id")})
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	private List<Role> roles;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="org_id")	
	private Organization org;
	
	public User(){}
	
	public User(Integer id){
		this.id = id;
	}

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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getChinesename() {
		return chinesename;
	}
	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public Organization getOrg() {
		return org;
	}
	public void setOrg(Organization org) {
		this.org = org;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "用户 [编码=" + id + ", 用户名=" + username + ", 中文名=" + chinesename + ", 固话=" + telephone + ", 手机=" + mobilephone + ",邮箱："+email+", 状态=" + status + ", 角色=" + roles + ", 组织=" + (null!=org?org.getId():"")+"("+(null!=org?org.getName():"")+")" + "]";
	}
}
