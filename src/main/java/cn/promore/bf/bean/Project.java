package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * 用户类
 */
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;


@Entity
@Table(name="sys_project")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class Project implements Serializable{
	
	private static final long serialVersionUID = 2764858766687094684L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(unique=true)
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=User.class,optional=true)
	@JoinColumn(name="master_id")
	private User master;
	
	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="type_id")
	private Option type;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@Temporal(TemporalType.DATE)
	private Date finishDate;
	private Integer price;
	
	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name="p_id")
	private Project parent;
	
	@OneToMany(mappedBy="project",targetEntity=ProjectUsers.class,fetch=FetchType.LAZY,cascade=CascadeType.REMOVE)
	private List<ProjectUsers> projectUsers;
	
	@Formula(value=" ( select count(case i.state when 99 then 1 end)/count(*) from bus_base_info i where i.projectId = id ) ")
	private Float complete;
	
	private String downloadPath;
	
	public Project(){super();}

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

	public User getMaster() {
		return master;
	}

	public void setMaster(User master) {
		this.master = master;
	}

	public Option getType() {
		return type;
	}

	public void setType(Option type) {
		this.type = type;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@JSON(format="yyyy-MM-dd")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Project getParent() {
		return parent;
	}

	public void setParent(Project parent) {
		this.parent = parent;
	}
	public List<ProjectUsers> getProjectUsers() {
		return projectUsers;
	}

	public void setProjectUsers(List<ProjectUsers> projectUsers) {
		this.projectUsers = projectUsers;
	}
//	public List<User> getDocumenter() {
//		return documenter;
//	}
//
//	public void setDocumenter(List<User> documenter) {
//		this.documenter = documenter;
//	}
//
//	public List<User> getTechnician() {
//		return technician;
//	}
//
//	public void setTechnician(List<User> technician) {
//		this.technician = technician;
//	}
//
//	public List<User> getTechofficor() {
//		return techofficor;
//	}
//
//	public void setTechofficor(List<User> techofficor) {
//		this.techofficor = techofficor;
//	}
//
//	public List<User> getWorkMinister() {
//		return workMinister;
//	}
//
//	public void setWorkMinister(List<User> workMinister) {
//		this.workMinister = workMinister;
//	}
//
//	public List<User> getProjectEngineer() {
//		return projectEngineer;
//	}
//
//	public void setProjectEngineer(List<User> projectEngineer) {
//		this.projectEngineer = projectEngineer;
//	}



	public Float getComplete() {
		return complete;
	}

	public void setComplete(Float complete) {
		this.complete = complete;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	@Override
	public String toString() {
//		StringBuffer s1 = new StringBuffer();
//		if(null!=documenter&&documenter.size()>0){
//			for(User u:documenter){
//				if(null!=u)s1.append("资料员[ID:"+u.getId()+",中文名:"+u.getChinesename()+",工号"+u.getUsername()+"],");
//			}
//		}
//		StringBuffer s2 = new StringBuffer();
//		if(null!=technician&&technician.size()>0){
//			for(User u:technician){
//				if(null!=u)s2.append("资料员[ID:"+u.getId()+",中文名:"+u.getChinesename()+",工号"+u.getUsername()+"],");
//			}
//		}
//		StringBuffer s3 = new StringBuffer();
//		if(null!=techofficor&&techofficor.size()>0){
//			for(User u:techofficor){
//				if(null!=u)s3.append("资料员[ID:"+u.getId()+",中文名:"+u.getChinesename()+",工号"+u.getUsername()+"],");
//			}
//		}
//		StringBuffer s4 = new StringBuffer();
//		if(null!=techofficor&&techofficor.size()>0){
//			for(User u:techofficor){
//				if(null!=u)s4.append("资料员[ID:"+u.getId()+",中文名:"+u.getChinesename()+",工号"+u.getUsername()+"],");
//			}
//		}
//		StringBuffer s5 = new StringBuffer();
//		if(null!=projectEngineer&&projectEngineer.size()>0){
//			for(User u:projectEngineer){
//				if(null!=u)s5.append("资料员[ID:"+u.getId()+",中文名:"+u.getChinesename()+",工号"+u.getUsername()+"],");
//			}
//		}
//		@ManyToMany(fetch=FetchType.LAZY)
//		@JoinTable(name="sys_project_documenter",joinColumns={@JoinColumn(name="project_id")},inverseJoinColumns={@JoinColumn(name="documenter_id")})
//		@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
//		private List<User> documenter; //资料员
	//	
//		@ManyToMany(fetch=FetchType.LAZY)
//		@JoinTable(name="sys_project_technician",joinColumns={@JoinColumn(name="project_id")},inverseJoinColumns={@JoinColumn(name="technician_id")})
//		@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
//		private List<User> technician; //技术员
	//	
//		@ManyToMany(fetch=FetchType.LAZY)
//		@JoinTable(name="sys_project_techofficor",joinColumns={@JoinColumn(name="project_id")},inverseJoinColumns={@JoinColumn(name="techofficor_id")})
//		@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
//		private List<User> techofficor; //技术主管
	//	
//		@ManyToMany(fetch=FetchType.LAZY)
//		@JoinTable(name="sys_project_workMinister",joinColumns={@JoinColumn(name="project_id")},inverseJoinColumns={@JoinColumn(name="workMinister_id")})
//		@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
//		private List<User> workMinister; //工程部长
	//	
//		@ManyToMany(fetch=FetchType.LAZY)
//		@JoinTable(name="sys_project_projectEngineer",joinColumns={@JoinColumn(name="project_id")},inverseJoinColumns={@JoinColumn(name="projectEngineer_id")})
//		@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
//		private List<User> projectEngineer; //项目总工		
		return "工程信息 [编码=" + id + ", 名称=" + name + ", 负责人=" + (null!=master?master.getChinesename():"") + 
			   ", 类型=" + type + ", 开始时间=" + startDate + ", 结束时间=" + endDate + ", 计划结束时间=" + finishDate + ", 工程造价=" + price + 
			   ", 所属代局指=" + (null!=parent?parent.getName():"") + 
//			   ", documenter=" + s1.toString() + 
//			   ", technician=" + s2.toString() + 
//			   ", techofficor=" + s3.toString() + 
//			   ", workMinister=" + s4.toString() + 
//			  ", projectEngineer=" + s5.toString()  +
			    "]";
	}
}
