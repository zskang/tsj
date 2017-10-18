package cn.promore.bf.unit;

import java.util.Arrays;
import java.util.List;

import cn.promore.bf.bean.Role;

/**
 *@author huzd@si-tech.com.cn or ahhzd@vip.qq
 *@version createrTime:Apr 25, 2017 5:18:26 PM
 **/

public class RoleUtils {

	public static String getMaxRegion(List<Role> roles) {
		if(null!=roles&&roles.size()>0){
			String[] regionArray = new String[roles.size()];
			int index = 0;
			for(Role r:roles){
				regionArray[index] = r.getRegion();
				index ++;
			}
			Arrays.sort(regionArray);
			return regionArray[0];
		}
		return null;
	}

}
