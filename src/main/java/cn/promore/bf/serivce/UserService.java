package cn.promore.bf.serivce;

import java.util.List;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.ShUserDto;
import cn.promore.bf.bean.User;

public interface UserService extends BaseService<User,Integer>{
    public User findByUsername(String username);
	public Integer findUserNo(User user);
	public List<User> findUsers(Page page,User user);
	public Long getDataNo(String entityName);
	public String findPasswordById(Integer id);
	public String findLxfsById(Integer id);
	public List<User> findDatasByChinesename(String chinesename,String roleShortName);
	public List<ShUserDto> findAllByParams(String params);
	public List<User> findUserByRoleShortName(String dataIds);
}
