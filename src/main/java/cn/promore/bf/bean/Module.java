package cn.promore.bf.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

@Entity
@Table(name="sys_module")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class Module implements Serializable{
	
	private static final long serialVersionUID = 1L;
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
	private String docType;
	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="p_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	private Module parent;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	@OrderBy(value="displayOrder asc")
	private List<Module> child;
	@Formula(value=" (select count(*) from sys_module r where r.type = 1 and r.status = 'N' and r.p_id = id) ")
	private Integer childNo;
	
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
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
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
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Module getParent() {
		return parent;
	}
	public void setParent(Module parent) {
		this.parent = parent;
	}
	public String getDisplayCode() {
		return displayCode;
	}
	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<Module> getChild() {
		return child;
	}
	public void setChild(List<Module> child) {
		this.child = child;
	}
	public Integer getChildNo() {
		return childNo;
	}
	public void setChildNo(Integer childNo) {
		this.childNo = childNo;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public boolean equals(Object obj){
        if(obj instanceof Module){
        	Module m=(Module)obj;
        	return (id.equals(m.getId()));
        }
        return super.equals(obj);
    }
    public int hashCode(){
        return id.hashCode();
    }
    
	public String toString() {
		return "模块 [编码=" + id + ", 名称=" + name + ", 描述=" + desc + ", 排序=" + order + ", 内置编码=" + displayCode + ", 路径=" + path + ", 图标=" + icon + ", 层级=" + level + ", 状态=" + status+ ", 文件类型=" + docType + ", 父节点=" + (null!=parent?parent.getId():-1) + "]";
	}
	
}
