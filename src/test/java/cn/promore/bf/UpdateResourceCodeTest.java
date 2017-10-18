package cn.promore.bf;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.promore.bf.bean.Resource;
import cn.promore.bf.serivce.EntitySequenceService;
import cn.promore.bf.serivce.ResourceService;
import cn.promore.bf.unit.EntityCodeHelper;

/**
 *@author huzd@si-tech.com.cn or ahhzd@vip.qq
 *@version createrTime:2017年4月4日 下午1:54:10
 **/
//@RunWith(SpringJUnit4ClassRunner.class)  
//@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})  
//@Transactional
//@Rollback(false) 
public class UpdateResourceCodeTest {
	@Autowired
	ResourceService resourceService;
	@Autowired
	EntitySequenceService entitySequenceService;
	
	
	private void un(Integer pid){
//		if(null!=pid&&0!=pid){
//			List<Resource> childResources = resourceService.findChildByPid(pid);
//			for(Resource temp:childResources){
//				String resourceSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(temp.getLevel()),"RESOURCE");
//				String resourceDisplayCode = EntityCodeHelper.generateCode(temp.getLevel(),temp.getParent().getDisplayCode(),resourceSequence);
//				temp.setDisplayCode(resourceDisplayCode);
//				resourceService.saveOrUpdate(temp);
//				System.out.println("=============="+temp.getId()+",name:"+temp.getName()+",level:"+temp.getLevel()+",code:"+temp.getDisplayCode()+",order:"+temp.getOrder());
//				un(temp.getId());
//			}
//		}
	}
	
	@Test
	public void testBatchUpdate(){
		un(1);
	}
}
