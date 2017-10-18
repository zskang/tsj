package cn.promore.bf.mydao;

import java.util.List;
import java.util.Map;

public interface ExecutionEntityMapper {

	int updateExecution(Map<String, String> map);
	
	List<Map<String, Object>> queryUserIdByRoleId(Map<String, String> map);

}
