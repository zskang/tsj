package cn.promore.bf.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.User;
import cn.promore.bf.dao.StatisticsDao;
@Repository
public class StatisticsDaoImpl extends BaseDaoImpl<User,Integer> implements StatisticsDao {
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page-1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	public List<Map<String, Object>> queryUserRankingList(Map<String, Object> paramMap, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer(); 
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("select userId,username,whetherFinish,finishRank,whetherDelay,delayRank,@curRank\\:=@curRank + 1 as rank,   ");
		sql.append("       whether1Sum,whether2Sum,whether3Sum,whether4Sum from ( ");
		sql.append("select a.userId,b.username,	 ");	
		sql.append("       count(*) taskCount, ");
		sql.append("  	   sum(case when a.whetherDelay = 0 and a.whetherFinish = 1 then 1 else 0 end) whether1Sum,");
		sql.append("       sum(case when a.whetherDelay = 1 and a.whetherFinish = 1 then 1 else 0 end) whether2Sum,");
		sql.append("  	   sum(case when a.whetherDelay = 0 and a.whetherFinish = 0 then 1 else 0 end) whether3Sum,");
		sql.append("       sum(case when a.whetherDelay = 1 and a.whetherFinish = 0 then 1 else 0 end) whether4Sum,");
		sql.append("       sum(case when a.whetherFinish = 1 then 1 else 0 end)/count(*) whetherFinish,");
		sql.append("       concat(format((sum(case when a.whetherFinish = 1 then 1 else 0 end)/count(*))*100,2),'%') finishRank, ");
		sql.append("       sum(case when a.whetherDelay = 1 then 1 else 0 end)/count(*) whetherDelay, ");
		sql.append("       concat(format((sum(case when a.whetherDelay = 1 then 1 else 0 end)/count(*))*100,2),'%') delayRank ");
		sql.append("  from sys_project_process_detail a,sys_user b  ");
		sql.append(" where a.userId = b.id and a.type = '1'  ");
		sql.append("   and ((date_format(a.startDate, '%Y-%m-%d')<=:startDate and date_format(a.endDate,'%Y-%m-%d') >= :startDate) or (date_format(a.startDate, '%Y-%m-%d') <= :endDate and date_format(a.endDate,'%Y-%m-%d') >= :endDate) or (date_format(a.startDate,'%Y-%m-%d') > :startDate and date_format(a.endDate,'%Y-%m-%d') < :endDate))");
		params.put("startDate", paramMap.get("startDate"));
		params.put("endDate", paramMap.get("endDate"));
		if(paramMap.get("projectId") != null && paramMap.get("projectId") != ""){
			sql.append("and a.projectId = :projectId    ");
			params.put("projectId", paramMap.get("projectId"));
		}
		sql.append("  group by a.userId,b.username) r,(select @curRank\\:=0) t   ");
		if(paramMap.get("ordercolumn").equals("1")){
			sql.append(" order by whetherFinish desc ");
		}else{
			sql.append(" order by whetherDelay desc ");
		}
		return findBySql(sql.toString(),params,pageNo,pageSize);
	}

	public Integer queryUserRankingCount(Map<String, Object> paramMap) {
		StringBuffer sql = new StringBuffer(); 
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("select count(*) from (select a.userId,b.username  ");
		sql.append("  from sys_project_process_detail a,sys_user b  ");
		sql.append(" where a.userId = b.id and a.type = '1'  ");
		sql.append("   and ((date_format(a.startDate, '%Y-%m-%d')<=:startDate and date_format(a.endDate,'%Y-%m-%d') >= :startDate) or (date_format(a.startDate, '%Y-%m-%d') <= :endDate and date_format(a.endDate,'%Y-%m-%d') >= :endDate) or (date_format(a.startDate,'%Y-%m-%d') > :startDate and date_format(a.endDate,'%Y-%m-%d') < :endDate))");
		params.put("startDate", paramMap.get("startDate"));
		params.put("endDate", paramMap.get("endDate"));
		if(paramMap.get("projectId") != null && paramMap.get("projectId") != ""){
			sql.append("and a.projectId = :projectId    ");
			params.put("projectId", paramMap.get("projectId"));
		}
		sql.append("  group by a.userId,b.username)  a ");
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		query.setProperties(params);
		Object count   = query.uniqueResult();
		return count == null ? 0 : Integer.parseInt(count + "");
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryUserRankingLists(Map<String, Object> paramMap) {
		StringBuffer sql = new StringBuffer(); 
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("select userId,username,whetherFinish,finishRank,whetherDelay,delayRank,@curRank\\:=@curRank + 1 as rank  from ( ");
		sql.append("select a.userId,b.username,	 ");	
		sql.append("       sum(case when a.whetherFinish = 1 then 1 else 0 end)/count(*) whetherFinish,");
		sql.append("       concat(format((sum(case when a.whetherFinish = 1 then 1 else 0 end)/count(*))*100,2),'%') finishRank, ");
		sql.append("       sum(case when a.whetherDelay = 1 then 1 else 0 end)/count(*) whetherDelay, ");
		sql.append("       concat(format((sum(case when a.whetherDelay = 1 then 1 else 0 end)/count(*))*100,2),'%') delayRank  ");
		sql.append("  from sys_project_process_detail a,sys_user b  ");
		sql.append(" where a.userId = b.id and a.type = '1'  ");
		sql.append("   and ((date_format(a.startDate, '%Y-%m-%d')<=:startDate and date_format(a.endDate,'%Y-%m-%d') >= :startDate) or (date_format(a.startDate, '%Y-%m-%d') <= :endDate and date_format(a.endDate,'%Y-%m-%d') >= :endDate) or (date_format(a.startDate,'%Y-%m-%d') > :startDate and date_format(a.endDate,'%Y-%m-%d') < :endDate))");
		params.put("startDate", paramMap.get("startDate"));
		params.put("endDate", paramMap.get("endDate"));
		if(paramMap.get("projectId") != null && paramMap.get("projectId") != ""){
			sql.append("and a.projectId = :projectId    ");
			params.put("projectId", paramMap.get("projectId"));
		}
		sql.append("  group by a.userId,b.username) r,(select @curRank\\:=0) t   ");
		if(paramMap.get("ordercolumn").equals("1")){
			sql.append(" order by whetherFinish desc ");
		}else{
			sql.append(" order by whetherDelay desc ");
		}
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	public List<Map<String, Object>> queryProjectRankingList(Map<String, Object> paramMap, int pageNo, int pageSize) {
		StringBuffer sql = new StringBuffer(); 
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("select projectId,name,whetherFinish,finishRank,whetherDelay,delayRank,@curRank\\:=@curRank + 1 as rank,   ");
		sql.append("       whether1Sum,whether2Sum,whether3Sum,whether4Sum from ( ");
		sql.append("select a.projectId,b.name,	 ");	
		sql.append("       count(*) taskCount, ");
		sql.append("  	   sum(case when a.whetherDelay = 0 and a.whetherFinish = 1 then 1 else 0 end) whether1Sum,");
		sql.append("       sum(case when a.whetherDelay = 1 and a.whetherFinish = 1 then 1 else 0 end) whether2Sum,");
		sql.append("  	   sum(case when a.whetherDelay = 0 and a.whetherFinish = 0 then 1 else 0 end) whether3Sum,");
		sql.append("       sum(case when a.whetherDelay = 1 and a.whetherFinish = 0 then 1 else 0 end) whether4Sum,");
		sql.append("       sum(case when a.whetherFinish = 1 then 1 else 0 end)/count(*) whetherFinish,");
		sql.append("       concat(format((sum(case when a.whetherFinish = 1 then 1 else 0 end)/count(*))*100,2),'%') finishRank, ");
		sql.append("       sum(case when a.whetherDelay = 1 then 1 else 0 end)/count(*) whetherDelay, ");
		sql.append("       concat(format((sum(case when a.whetherDelay = 1 then 1 else 0 end)/count(*))*100,2),'%') delayRank  ");
		sql.append("  from sys_project_process_detail a,sys_project b  ");
		sql.append(" where a.projectId = b.id and a.type = '2'  ");
		sql.append("   and ((date_format(a.startDate, '%Y-%m-%d')<=:startDate and date_format(a.endDate,'%Y-%m-%d') >= :startDate) or (date_format(a.startDate, '%Y-%m-%d') <= :endDate and date_format(a.endDate,'%Y-%m-%d') >= :endDate) or (date_format(a.startDate,'%Y-%m-%d') > :startDate and date_format(a.endDate,'%Y-%m-%d') < :endDate))");
		params.put("startDate", paramMap.get("startDate"));
		params.put("endDate", paramMap.get("endDate"));
		if(paramMap.get("projectId") != null && paramMap.get("projectId") != ""){
			sql.append("and a.projectId = :projectId    ");
			params.put("projectId", paramMap.get("projectId"));
		}
		sql.append("  group by a.projectId,b.name) r,(select @curRank\\:=0) t   ");
		if(paramMap.get("ordercolumn").equals("1")){
			sql.append(" order by whetherFinish desc ");
		}else{
			sql.append(" order by whetherDelay desc ");
		}
		return findBySql(sql.toString(),params,pageNo,pageSize);
	}

	public Integer queryProjectRankingCount(Map<String, Object> paramMap) {
		StringBuffer sql = new StringBuffer(); 
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("select count(*) from (select a.projectId,b.name  ");
		sql.append("  from sys_project_process_detail a,sys_project b  ");
		sql.append(" where a.projectId = b.id and a.type = '2'  ");
		sql.append("   and ((date_format(a.startDate, '%Y-%m-%d')<=:startDate and date_format(a.endDate,'%Y-%m-%d') >= :startDate) or (date_format(a.startDate, '%Y-%m-%d') <= :endDate and date_format(a.endDate,'%Y-%m-%d') >= :endDate) or (date_format(a.startDate,'%Y-%m-%d') > :startDate and date_format(a.endDate,'%Y-%m-%d') < :endDate))");
		params.put("startDate", paramMap.get("startDate"));
		params.put("endDate", paramMap.get("endDate"));
		if(paramMap.get("projectId") != null && paramMap.get("projectId") != ""){
			sql.append("and a.projectId = :projectId    ");
			params.put("projectId", paramMap.get("projectId"));
		}
		sql.append("  group by a.projectId,b.name)  a ");
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		query.setProperties(params);
		Object count   = query.uniqueResult();
		return count == null ? 0 : Integer.parseInt(count + "");
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryProjectRankingLists(Map<String, Object> paramMap) {
		StringBuffer sql = new StringBuffer(); 
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("select projectId,name,whetherFinish,finishRank,whetherDelay,delayRank,@curRank\\:=@curRank + 1 as rank  from ( ");
		sql.append("select a.projectId,b.name,	 ");	
		sql.append("       sum(case when a.whetherFinish = 1 then 1 else 0 end)/count(*) whetherFinish,");
		sql.append("       concat(format((sum(case when a.whetherFinish = 1 then 1 else 0 end)/count(*))*100,2),'%') finishRank, ");
		sql.append("       sum(case when a.whetherDelay = 1 then 1 else 0 end)/count(*) whetherDelay, ");
		sql.append("       concat(format((sum(case when a.whetherDelay = 1 then 1 else 0 end)/count(*))*100,2),'%') delayRank ");
		sql.append("  from sys_project_process_detail a,sys_project b  ");
		sql.append(" where a.userId = b.id and a.type = '2'  ");
		sql.append("   and ((date_format(a.startDate, '%Y-%m-%d')<=:startDate and date_format(a.endDate,'%Y-%m-%d') >= :startDate) or (date_format(a.startDate, '%Y-%m-%d') <= :endDate and date_format(a.endDate,'%Y-%m-%d') >= :endDate) or (date_format(a.startDate,'%Y-%m-%d') > :startDate and date_format(a.endDate,'%Y-%m-%d') < :endDate))");
		params.put("startDate", paramMap.get("startDate"));
		params.put("endDate", paramMap.get("endDate"));
		if(paramMap.get("projectId") != null && paramMap.get("projectId") != ""){
			sql.append("and a.projectId = :projectId    ");
			params.put("projectId", paramMap.get("projectId"));
		}
		sql.append("  group by a.projectId,b.name) r,(select @curRank\\:=0) t   ");
		if(paramMap.get("ordercolumn").equals("1")){
			sql.append(" order by whetherFinish desc ");
		}else{
			sql.append(" order by whetherDelay desc ");
		}
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryModuleTaskList(Map<String, String> paramMap) {
		StringBuffer sql = new StringBuffer(); 
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("    select e.id,e.icon,e.name, ");
		sql.append("           sum(case when f.moduleId is not null and date_add(now(), interval 12 hour) <= f.DUE_DATE_ then 1 else 0 end)  exec1,	 ");	
		sql.append("           sum(case when f.moduleId is not null and date_add(now(), interval 12 hour) > f.DUE_DATE_ and f.DUE_DATE_>now() then 1 else 0 end) exec2,  ");
		sql.append("           sum(case when f.moduleId is not null and now() >= f.DUE_DATE_ then 1 else 0 end) exec3   ");
		sql.append("      from sys_module_detail a ");
		sql.append("inner join sys_module e on(a.parentId = e.id)     ");
		sql.append(" left join (select c.DUE_DATE_,d.moduleId from act_ru_task c,bus_base_info d where c.PROC_INST_ID_ = d.processInstanceId  ");
		if(paramMap.get("projectId") != null && paramMap.get("projectId") != ""){
			sql.append(" and d.projectId = :projectId    ");
			params.put("projectId", paramMap.get("projectId"));
		}
		if(paramMap.get("userId") != null && paramMap.get("userId") != ""){
			sql.append(" and c.ASSIGNEE_  = :userId    ");
			params.put("userId", paramMap.get("userId"));
		}
		sql.append(") f on(a.id = f.moduleId) ");
		sql.append("     where a.level = 1  ");
		sql.append("  group by e.id,e.icon,e.name  ");
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryModuleIdByTaskId(String taskId) {
		StringBuffer sql = new StringBuffer(); 
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("SELECT d.parentid moduleId FROM act_ru_task a, act_re_procdef b, sys_act_module c, sys_module_detail d WHERE a.proc_def_id_ = b.id_ AND b.key_=c.developKey AND c.moduleId = d.id AND d.level = 1 AND a.id_ = '"+taskId+"'");
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> showModuleFileList(Map<String, String> paramMap) {
		StringBuffer sql = new StringBuffer(); 
		Map<String, Object> params = new HashMap<String, Object>();
		sql.append("select a.id,c.name moduleName,a.createTime,a.docName,a.docType,concat(a.docPath,a.docName) docPath ");
		sql.append("  from bus_doc_info a,bus_base_info b,sys_module c ");
		sql.append(" where a.busId = b.id    ");
		sql.append("  and b.moduleId = c.id  ");
		if(paramMap.get("projectId") != null && paramMap.get("projectId") != ""){
			sql.append(" and b.projectId = :projectId    ");
			params.put("projectId", paramMap.get("projectId"));
		}
		if(paramMap.get("moduleId") != null && paramMap.get("moduleId") != ""){
			sql.append(" and (exists(select 1 from sys_module_detail d where b.moduleId=d.id and d.parentId =:moduleId) or b.moduleId =:moduleId)   ");
			params.put("moduleId", paramMap.get("moduleId"));
		}
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryActHiComments(String commId) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT IFNULL(user.chinesename, act.user_id_) username, user.id userid, act.proc_inst_id_ procInstId  FROM act_hi_comment act LEFT JOIN sys_user USER ON act.user_id_= user.id WHERE act.id_ = '"+commId+"' AND act.proc_inst_id_ IS NOT NULL");
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		List<Map<String, Object>> list = q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(list != null && list.size() > 0){
			return (Map<String, Object>)list.get(0);
		}
		return null;
	}


}
