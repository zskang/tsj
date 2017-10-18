
package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 *  角色类
 */
import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_role")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class Role implements Serializable{
	private static final long serialVersionUID = -8136993944967274490L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(unique=true)
	private String name;
	@Column(name="description")
	private String desc;
	private Boolean canInherit; //是否可继承
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="sys_role_resource",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="res_id")})
	@OrderBy(value="order")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	private Set<Resource> resources;
	private String region;
	@Column(unique=true)
	private String shortName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Set<Resource> getResources() {
		return resources;
	}
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	public Boolean getCanInherit() {
		return canInherit;
	}
	public void setCanInherit(Boolean canInherit) {
		this.canInherit = canInherit;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Override
	public String toString() {
		return "角色 [编码=" + id + ", 名称=" + name + ", 描述=" + desc + ", 是否可继承=" + canInherit +"，权限范围="+region+"，英文缩写="+shortName+"]";
	}
	
	
}
