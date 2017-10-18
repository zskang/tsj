package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * 通知类
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_notify")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class Notify implements Serializable{
	
	private static final long serialVersionUID = -8894911141527624524L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;									//编码
	@Column(nullable=false)
	private String title;
	private String poster;
	@Column(name="content_",length=5000)
	private String content;
	
	@ManyToOne(fetch=FetchType.LAZY,targetEntity=User.class,cascade=CascadeType.REMOVE)
	@JoinColumn(name="receiver_id")
	private User   receiver;
	@Temporal(TemporalType.TIMESTAMP)
	private Date   createTime;
	private Boolean isReaded;
	
	
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
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Boolean getIsReaded() {
		return isReaded;
	}
	public void setIsReaded(Boolean isReaded) {
		this.isReaded = isReaded;
	}
	@Override
	public String toString() {
		return "系统通知 [编码=" + id + ", 标题=" + title + ", 发送人=" + poster + ", 内容=" + content + ", 接收人=" + receiver.getChinesename() + ", 创建时间=" + createTime + ", 是否已读=" + isReaded + "]";
	}
	
}
