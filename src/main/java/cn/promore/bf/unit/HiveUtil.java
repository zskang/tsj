package cn.promore.bf.unit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class HiveUtil {

	public static List<Object[]> findDatasAsArray(String driver,String url,String username,String password,String sql) throws IOException, ClassNotFoundException, SQLException{
		if(StringUtils.isEmpty(sql))return null;
		Class.forName(driver);
		System.out.println("======= load Hive driver log by huzd=======");
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,username,password);
		System.out.println("HiveUtil:to execute hive sql now log by huzd：=============="+sql);
		PreparedStatement ps = con.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		ps.setFetchSize(1000);
		ResultSet rs = ps.executeQuery();
		List<Object[]> result =  new ArrayList<Object[]>();
		ResultSetMetaData resultSetMetaData = rs.getMetaData();
		Integer columnCount = resultSetMetaData.getColumnCount();
		while (rs.next()) {
			Object[] rowData = new Object[columnCount];
			for(int index = 1;index<=columnCount;index++){
				if(null!=rs.getObject(index)){
					if(rs.getObject(index) instanceof BigDecimal){
						 BigDecimal bigDecimal = (BigDecimal)rs.getObject(index);
						 bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
						 rowData[index-1] = bigDecimal.toPlainString();
					}else if(rs.getObject(index) instanceof Double){
						 DecimalFormat decimalFormat = new DecimalFormat("###0.##");
						 decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
						 rowData[index-1] = decimalFormat.format(rs.getObject(index)).toString();
					}else{
						rowData[index-1] = rs.getObject(index);
					}
				}else{
					rowData[index-1] = "";
				}
			}
			result.add(rowData);
		}
		rs.close();
		ps.close();
		con.close();
		return result;
	}

	public static List<Map<String,Object>> findDatasAsMap(String driver,String url,String username,String password,String sql) throws IOException, ClassNotFoundException, SQLException{
		if(StringUtils.isEmpty(sql))return null;
		System.out.println("======= load Hive driver log by huzd=======");
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,username,password);
		System.out.println("HiveUtil:to execute hive sql now log by huzd：=============="+sql);
		PreparedStatement ps = con.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		ps.setFetchSize(1000);
		ResultSet rs = ps.executeQuery();
		List<Map<String,Object>> result =  new ArrayList<Map<String,Object>>();
		ResultSetMetaData resultSetMetaData = rs.getMetaData();
		Integer columnCount = resultSetMetaData.getColumnCount();
		while (rs.next()) {
			Map<String, Object> rowData = new HashMap<String,Object>();
			for(int index = 1;index<=columnCount;index++){
				if(null!=rs.getObject(index)){
					if(rs.getObject(index) instanceof BigDecimal){
						 BigDecimal bigDecimal = (BigDecimal)rs.getObject(index);
						 bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
						 rowData.put(resultSetMetaData.getColumnName(index),bigDecimal.toPlainString());
					}else if(rs.getObject(index) instanceof Double){
						 DecimalFormat decimalFormat = new DecimalFormat("###0.##");
						 decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
						 rowData.put(resultSetMetaData.getColumnName(index),decimalFormat.format(rs.getObject(index)).toString());
					}else{
						rowData.put(resultSetMetaData.getColumnName(index),rs.getObject(index));
					}
				}else{
					rowData.put(resultSetMetaData.getColumnName(index),"");
				}
			}
			result.add(rowData);
		}
		rs.close();
		ps.close();
		con.close();
		return result;
	}	
	
	public static Integer findDatasNo(String sql) throws IOException, ClassNotFoundException, SQLException{
		if(StringUtils.isEmpty(sql))return null;
		Properties properties = new Properties();
		properties.load(HiveUtil.class.getResourceAsStream("/config.properties"));
		
		String driver = properties.getProperty("jdbc.hive.driver");
		String url    = properties.getProperty("jdbc.hive.url");
		String username    = properties.getProperty("jdbc.hive.username");
		String password    = properties.getProperty("jdbc.hive.password");
		
		System.out.println("======= load Hive driver log by huzd=======");
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,username,password);
		System.out.println("HiveUtil:to execute hive sql now log by huzd：=============="+sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		Integer dataNo = null;
		while (rs.next()) {
			dataNo = ((Long)rs.getObject(1)).intValue();
		}
		rs.close();
		ps.close();
		con.close();
		return dataNo;
	}
	
	public static Integer findDatasNo(String driver,String url,String username,String password,String sql) throws IOException, ClassNotFoundException, SQLException{
		if(StringUtils.isEmpty(sql))return null;
		Class.forName(driver);
		System.out.println("=======加载Hive驱动完毕=======");
		Connection con = DriverManager.getConnection(url,username,password);
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		Integer dataNo = null;
		while (rs.next()) {
			dataNo = ((Long)rs.getObject(1)).intValue();
		}
		rs.close();
		ps.close();
		con.close();
		return dataNo;
	}
	
	public static List<Map<String,Object>> findMapData(String driver,String url,String username,String password,String sql) throws IOException, ClassNotFoundException, SQLException{
		if(StringUtils.isEmpty(sql)&&StringUtils.isEmpty(driver)&&StringUtils.isEmpty(url)&&StringUtils.isEmpty(username)&&StringUtils.isEmpty(password))return null;
		System.out.println("======= load Hive driver log by huzd=======");
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,username,password);
		System.out.println("HiveUtil:to execute hive sql now log by huzd：=============="+sql);
		PreparedStatement ps = con.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery();
		List<Map<String,Object>> result =  new ArrayList<Map<String,Object>>();
		ResultSetMetaData resultSetMetaData = rs.getMetaData();
		Integer columnCount = resultSetMetaData.getColumnCount();
		Map<String,Object> subData;
		while (rs.next()) {
			subData = new HashMap<String, Object>();
			for(int index = 1;index<=columnCount;index++){
				if(null!=rs.getObject(index)){
					if(rs.getObject(index) instanceof BigDecimal){
						 BigDecimal bigDecimal = (BigDecimal)rs.getObject(index);
						 bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
						 subData.put(resultSetMetaData.getColumnName(index),bigDecimal.toPlainString());
					}else if(rs.getObject(index) instanceof Double){
						 DecimalFormat decimalFormat = new DecimalFormat("###0.##");
						 decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
						 subData.put(resultSetMetaData.getColumnName(index),decimalFormat.format(rs.getObject(index)).toString());
					}else{
						subData.put(resultSetMetaData.getColumnName(index),rs.getObject(index));
					}
				}else{
					subData.put(resultSetMetaData.getColumnName(index),"");
				}
			}
			result.add(subData);
		}
		rs.close();
		ps.close();
		con.close();
		return result;
	}
//	private static boolean isDoubleWithPoint(Object vObject){
//		try {
//			if((double)vObject==0)return false;
//			Double dv = (Double)vObject;
//			if(Math.rint(dv) - dv==0)return false;
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
	
//	public static void main(String[] args) throws IOException {
//		try {
//			List<Map<String,Object>> result = HiveUtil.findDatasAsMap("select * from MD_STD_DIF_ADJT_FNC_M");
////			Integer result = HiveUtil.findDatasNo("select count(*) from vpnlog");
//			System.out.println(">>>>>>>"+result.size());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
}
