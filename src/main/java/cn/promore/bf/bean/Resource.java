package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * 系统资源
 */
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

@Entity
@Table(name="sys_resource")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class Resource implements Serializable{
	
	private static final long serialVersionUID = -5161791165497333959L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(unique=true)
	private String name;
	@Column(name="description")
	private String desc;
	private Integer type;
	@Column(name="displayOrder")
	private Integer order;
	private String displayCode;
	private String path;
	private String icon;
	private Integer level;
	private String status;
	
	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="p_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	private Resource parent;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	@OrderBy(value="displayOrder asc")
	private List<Resource> child;
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="resources")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	private Set<Role> roles;
	
	@Formula(value=" (select count(*) from sys_resource r where r.type = 1 and r.status = 'N' and r.p_id = id) ")
	private Integer childNo;
	
	
	public List<Resource> getChild() {
		return child;
	}
	public void setChild(List<Resource> child) {
		this.child = child;
	}
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
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Resource getParent() {
		return parent;
	}
	public void setParent(Resource parent) {
		this.parent = parent;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDisplayCode() {
		return displayCode;
	}
	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}
	public Integer getChildNo() {
		return childNo;
	}
	public void setChildNo(Integer childNo) {
		this.childNo = childNo;
	}
	@Override
	public String toString() {
		return "资源 [编码=" + id + ", 名称=" + name + ", 描述=" + desc + ", 类型=" + type + ", 排序=" + order + ", 内置编码=" + displayCode + ", 路径=" + path + ", 图标=" + icon + ", 层级=" + level + ", 状态=" + status + ", 父节点=" + (null!=parent?parent.getId():-1) + "]";
	}
}
