package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 *  系统选项
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="sys_option")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="applicationBeanCache")
public class Option implements Serializable{
	private static final long serialVersionUID = -8894911141527624524L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;									//编码
	@Column(nullable=false)
	private String category;							//分类
	@Column(nullable=false)
	private String categoryname;
	@Column(nullable=false)
	private String name;								//码表名称
	@Column(name="value_")
	private String value;								//码表值
	@Column(name="displayOrder")
	private Integer order;								//排序
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategoryname() {
		return categoryname;
	}
	
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "系统选项 [编码=" + id + ", 分类=" + category + ", 分类中文名称=" + categoryname + ", 名称=" + name + ", 排序=" + order + ", 值=" + value + "]";
	}
}
