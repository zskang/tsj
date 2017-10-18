package cn.promore.bf.unit;

import java.util.HashMap;
import java.util.Map;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * @version createTime:2016/02/01
 */


import org.apache.commons.lang3.StringUtils;

/**
 * 实体编码辅助类
 *
 */
public class EntityCodeHelper {
	
	private static String[] LEVEL_WORD = new String[]{"-1","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	/**
	 * 生成实体层次编码 
	 * 
	 * 在oracle数据库中有树形结构查询语句；在Mysql数据库中并没有；为了平台的通用性，系统自定义一套编码；
	 * 一方面可以用于排序；一方面可以用数据权限约束。
	 * A001
	 * A001B001
	 * A001B001C001
	 * 最大支持26个层级；每个层级最大支持999个子节点
	 * @param level			实体层级
	 * @param parentCode	父节点编码
	 * @param nextNo		当前实体序号
	 * @return  实体编码
	 */
	public static String generateCode(Integer level,String parentCode,String nextNo){
		String startWord = LEVEL_WORD[level];
		return ((StringUtils.isNotEmpty(parentCode)&&!"-1".equals(parentCode)&&!"null".equals(parentCode))?parentCode:"")+startWord+nextNo;
	}
	
	/**
	 * 根据组织机构层级获取前缀字母
	 * 
	 * @param level 组织机构层级
	 * @return 层级首字母编码
	 */
	public static String getOrgPrefix(Integer level){
		return LEVEL_WORD[level];
	}
	
	
	/**
	 * 根据组织机构编码获取各个层次编码
	 * 
	 * @param code 组织机构编码
	 * @return 各个层次编码Map
	 */
	public static Map<Integer,String> getOrgLevelWithCode(String code){
		Map<Integer,String> orgLevel = new HashMap<Integer, String>();
		if(StringUtils.isNotEmpty(code)){
			if("-1".equals(code))return null;
			if(code.length()%4==0){
				Integer level = code.length() / 4;
				for (int i = 1; i <= level; i++) {
					//orgLevel.put(i,code.substring((i-1)*4,i*4));
					orgLevel.put(i,code.substring(0,i*4));
				}
				return orgLevel;
			}else{
				throw new IllegalArgumentException();
			}
		}
		throw new NullPointerException();
	}
}
