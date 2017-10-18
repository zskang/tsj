package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 *  组织机构
 */
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_org")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class Organization implements Serializable{
	
	private static final long serialVersionUID = -5161791165497333959L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(unique=true)
	private String name;
	@Column(name="description")
	private String desc;
	private String code;
	private Integer level;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="p_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	private Organization parent;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent",cascade=CascadeType.REMOVE,orphanRemoval=true)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	private List<Organization> child;
	
	public List<Organization> getChild() {
		return child;
	}
	public void setChild(List<Organization> child) {
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Organization getParent() {
		return parent;
	}
	public void setParent(Organization parent) {
		this.parent = parent;
	}
	@Override
	public String toString() {
		return "组织机构 [编码=" + id + ", 名称=" + name + ", 描述=" + desc + ", 内置编码=" + code + ", 层级=" + level + ", 父节点=" + parent.getId()+ "]";
	}
	
}
