package cn.promore.bf.bean;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

@Entity
@Table(name="sys_completion_info")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class CompletionInfo implements Serializable{

	/**
	 * 
	 */
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
	private String status;
	private String path;
	private Integer level;
	private Integer fileId;
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=Project.class,optional=true)
	@JoinColumn(name="projectId")
	private Project project;
	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="p_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	private CompletionInfo parent;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
	@OrderBy(value="displayOrder asc")
	private List<CompletionInfo> child;
	@Formula(value=" (select count(*) from sys_completion_info r where r.type = 1 and r.p_id = id) ")
	private Integer childNo;
	@Temporal(TemporalType.DATE)
	private Date createDate;
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=User.class,optional=true)
	@JoinColumn(name="creater_id")
	private User creater;
	
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getDisplayCode() {
		return displayCode;
	}
	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public CompletionInfo getParent() {
		return parent;
	}
	public void setParent(CompletionInfo parent) {
		this.parent = parent;
	}
	public List<CompletionInfo> getChild() {
		return child;
	}
	public void setChild(List<CompletionInfo> child) {
		this.child = child;
	}
	public Integer getChildNo() {
		return childNo;
	}
	public void setChildNo(Integer childNo) {
		this.childNo = childNo;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
}