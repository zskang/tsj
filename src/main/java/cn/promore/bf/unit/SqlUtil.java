package cn.promore.bf.unit;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SqlUtil {


	
	public static String generateSQLForHive(List<String> subConditionList, String columns,String tableName,String orderColumns,String groupColumns,Integer startIndex,Integer pageSize) {
		// select row_number() over(order by deal_date desc) as rn ,region_cde from MD_STD_DIF_ADJT_FNC_M
		StringBuffer sql = new StringBuffer("");
		if(StringUtils.isNotEmpty(tableName)&&StringUtils.isNotEmpty(columns)){
			sql.append("select ");
			sql.append(columns);
			sql.append(" from ");
			sql.append(tableName);
			if(null!=subConditionList&&subConditionList.size()>0){
				sql.append(" where ");
				Integer index = 1;
				for(String sub_:subConditionList){
					sql.append(sub_);
					if(index<=subConditionList.size()-1){sql.append(" AND ");}
					index++;
				}
			}
			if(StringUtils.isNotEmpty(groupColumns))sql.append(" group by "+groupColumns);
			if(StringUtils.isNotEmpty(orderColumns))sql.append(" order by "+orderColumns);
			sql.append(" limit "+pageSize + " offset "+startIndex);
			System.out.println("生成的Impala 分页 sql:"+sql.toString());
		}
		return sql.toString();
	}

	public static String generateCountSQLForHive(List<String> subConditionList,String columns,String tableName) {
		StringBuffer sql = new StringBuffer("");
		if(StringUtils.isNotEmpty(tableName)){
			sql.append("select count(*) ");
			sql.append(" from ");
			sql.append(tableName);
			if(null!=subConditionList&&subConditionList.size()>0){
				sql.append(" where ");
				Integer index = 1;
				for(String sub_:subConditionList){
					sql.append(sub_);
					if(index<=subConditionList.size()-1){sql.append(" AND ");}
					index++;
				}
			}
			System.out.println("生成的Impala 统计条数 sql:"+sql.toString());
		}
		return sql.toString();
	}
	
	public static String generateDataSQLForHive(List<String> subConditionList,String columns,String tableName,String orderColumns,String groupColumns) {
		StringBuffer sql = new StringBuffer("");
		if(StringUtils.isNotEmpty(tableName)){
			sql.append("select  ");
			sql.append(columns);
			sql.append(" from ");
			sql.append(tableName);
			if(null!=subConditionList&&subConditionList.size()>0){
				sql.append(" where ");
				Integer index = 1;
				for(String sub_:subConditionList){
					sql.append(sub_);
					if(index<=subConditionList.size()-1){sql.append(" AND ");}
					index++;
				}
			}
			if(StringUtils.isNotEmpty(groupColumns))sql.append(" group by "+groupColumns);
			if(StringUtils.isNotEmpty(orderColumns))sql.append(" order by "+orderColumns);
			System.out.println("生成的Impala 导出 sql:"+sql.toString());
		}
		return sql.toString();
	}
//	public static void main(String[] args) {
//		List<String> subConditionList = new ArrayList<String>();
//		subConditionList.add(" region_cde = 11 ");
//		subConditionList.add(" region_name = '11' ");
//		SqlUtil.generateSQLForHive(subConditionList, "region_cde,region_Name","MD_STD_DIF_ADJT_FNC_M","region_cde asc","", 0, 10);
//		SqlUtil.generateCountSQLForHive(subConditionList, "region_cde,region_Name","MD_STD_DIF_ADJT_FNC_M");
//		SqlUtil.generateDataSQLForHive(subConditionList, "region_cde,region_Name", "MD_STD_DIF_ADJT_FNC_M", "region_cde asc", "");
//	}
}
