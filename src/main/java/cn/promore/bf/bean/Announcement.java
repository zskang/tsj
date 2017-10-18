package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * 系统公告
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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_announcement")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class Announcement implements Serializable{
	
	private static final long serialVersionUID = -4437410136348719986L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Integer id;
	@Column(unique=true)
	private String title;
	@Column(length=5000,nullable=false)
	private String content;
	private Date createDate;
	private Date expireData;
	private String author;
	private Integer hit;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getExpireData() {
		return expireData;
	}
	public void setExpireData(Date expireData) {
		this.expireData = expireData;
	}
	public Integer getHit() {
		return hit;
	}
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		return "系统公告 [编码=" + id + ", 标题=" + title + ", 正文=" + content + ", 创建时间=" + createDate + ", 过期时间=" + expireData + ", 作者=" + author + ", 点击次数=" + hit + "]";
	}
	
	
}
