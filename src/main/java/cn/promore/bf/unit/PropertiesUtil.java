package cn.promore.bf.unit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;


public class PropertiesUtil {
	static String propertiesFileName = "config.properties";
	private static Properties props = new Properties();
	static {
		try {
			//PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesFileName)
			props.load(PropertiesUtil.class.getResourceAsStream("/"+propertiesFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getKeyValue(String key) {
		return props.getProperty(key).trim();
	}
	
	@SuppressWarnings("rawtypes")
	public static HashMap<String,String> getAllProperties(){
		HashMap<String, String> properties =  new HashMap<String, String>();
		for (Enumeration e = props.keys(); e.hasMoreElements() ;) {
		    String key = (String)e.nextElement();
		    properties.put(key, props.getProperty(key));
		}
		return properties;
	};

	
	public static void updateProperties(String keyname,String keyvalue) {
		try {
			OutputStream fos = new FileOutputStream(URLDecoder.decode(PropertiesUtil.class.getResource("/"+propertiesFileName).getFile(),"utf-8"));
			props.setProperty(keyname, keyvalue);
			props.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("属性文件更新错误");
		}
	}
	
	public static void writeProperties(String keyname, String keyvalue) {
		try {
			OutputStream fos = new FileOutputStream(URLDecoder.decode(PropertiesUtil.class.getResource("/"+propertiesFileName).getFile(),"utf-8"));
			props.setProperty(keyname, keyvalue);
			props.store(fos, "add '" + keyname + "' value");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("属性文件更新错误");
		}
	}
	// 测试代码
	public static void main(String[] args) {
		//	System.out.println(PropertiesUtil.class.getClassLoader().getResourceAsStream(profilepath)); 
		//System.out.println(getKeyValue("EnableUploadType"));
/*		System.out.println(PropertiesUtil.class.getResource("."));
		System.out.println(PropertiesUtil.class.getResource(""));
		System.out.println();*/
		//updateProperties("BackupUploadFile","false");
/*		writeProperties("MAIL_SERVER_INCOMING", "327@qq.com");
		System.out.println("操作完成");*/
	}
}
