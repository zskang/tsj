package cn.promore.bf.dao;

import java.util.List;

import cn.promore.bf.bean.User;


public interface UserDao extends BaseDao<User,Integer>{
	public User findByUsername(String username);
	public Integer findUserNo(User user);
	public List<User> findUsers(User user,Integer startIndex,Integer pageSize);
	public Long getDataNo(String entityName);
	public String findPasswordById(Integer id);
	public String findLxfsById(Integer id);
	public List<User> findDatasByChinesename(String chinesename,String roleShortName);
}
