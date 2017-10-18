package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.SiteInfo;
import cn.promore.bf.unit.RandomValidateCode;

@Controller
@Action(value="validAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="index",location="/index.jsp",type="redirect")
		})
public class ValidateAction extends BaseAction{
	public static Logger LOG = LoggerFactory.getLogger(ValidateAction.class);
	private static final long serialVersionUID = -8613055080615406396L;
	
	public String execute() throws Exception {
		SiteInfo siteInfo = (SiteInfo)ServletActionContext.getServletContext().getAttribute("siteinfo");
		RandomValidateCode validateCode = new RandomValidateCode(null!=siteInfo.getValidateCodeType()?siteInfo.getValidateCodeType():RandomValidateCode.DIGITAL_VALIDATE_CODE);
		validateCode.bindingRequest(request, response);
		return null;
	}

}
