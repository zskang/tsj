package cn.promore.bf;

import java.lang.reflect.Field;

import org.junit.Test;

import cn.promore.bf.bean.Project;
import cn.promore.bf.bean.Task;

public class JsonHelper {
	@Test
	public void testPropertyToExString(){
		try {
			Class<?> object =  Class.forName(Project.class.getName());
			Field[] fields = object.getDeclaredFields();
			StringBuffer json = new StringBuffer();
			if(null!=fields&&fields.length>0){
				for (Field f:fields) {
					json.append(f.getName()+"|");
				}
			}
			System.out.println(json.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
