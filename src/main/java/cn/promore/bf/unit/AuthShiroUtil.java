package cn.promore.bf.unit;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * @version createTime:2016/02/01
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Shiro 框架辅助类
 * <p>用于辅助Shiro框架做本地化应用</p>
 *
 */
public class AuthShiroUtil {
	
	/**
	 * 权限验证抛出异常后；用于提取不具备的权限名称：例如 user:list 等
	 * @param exception shiro 权限验证异常的堆栈描述字符串
	 * @return 不具备权限标识（例如：user:list）。
	 */
	public static String getPermission(String exception){
		if(StringUtils.isEmpty(exception))return "";
		String permission = "";
		Pattern p = Pattern.compile("Subject does not have permission \\[(.+):(.+)\\]");
		Matcher m = p.matcher(exception);
		Boolean flag = true;
		while (flag&&m.find()) {
			permission = m.group(1)+":"+m.group(2);
			flag = false;
		}
		return permission;
	}
}
