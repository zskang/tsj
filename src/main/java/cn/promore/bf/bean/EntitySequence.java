package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * 实体序列
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_entity_sequence")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class EntitySequence implements Serializable{
	
	private static final long serialVersionUID = -5161791165497333959L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false)
	private String  name;
	@Column(name="cursor_")
	private Integer cursor;
	@Column(nullable=false)
	private String type;
	@Temporal(TemporalType.TIMESTAMP)
	private Date    lastTime = new Date();
	
	public EntitySequence() {
		super();
	}
	
	public EntitySequence(String name, Integer cursor,String type) {
		super();
		this.name = name;
		this.cursor = cursor;
		this.type = type;
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
	public Integer getCursor() {
		return cursor;
	}
	public void setCursor(Integer cursor) {
		this.cursor = cursor;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "实体序列 [编码=" + id + ", 名称=" + name + ", 游标=" + cursor + ", 类型=" + type + ", 最后时间=" + lastTime + "]";
	}
	
}
