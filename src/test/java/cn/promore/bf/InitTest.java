package cn.promore.bf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq
 * @version createrTime:Apr 24, 2017 9:54:57 PM
 **/
public class InitTest {
	@Test
	public void testA() {

		System.out.println(Float.MAX_VALUE);
		// Pattern pattern =
		// Pattern.compile(RoleWildCardPermission.ROLE_EXTEND_WILDCARD_PATTERN);
		// String permission = "role>*>*";
		// Matcher matcher = pattern.matcher(permission);
		// if(matcher.matches()){
		// System.out.println("---->"+matcher.groupCount());
		// }

		// List<Integer> ins = new ArrayList<Integer>();
		// ins.add(11111);
		// ins.add(2);
		// ins.add(22);
		// ins.add(21);
		// ins.add(42);
		// ins.add(11);
		// ins.add(523);
		// ins.add(123);
		// ins.add(4);
		//
		// Object[] objs = ins.toArray();
		// Arrays.sort(objs);
		// System.out.println(Integer.valueOf(String.valueOf(objs[0])));

		// String address = "安徽省亳州市谯城区立德镇铁庄";
		// aaaaaa(address);
		String address = "安徽省亳州市谯城区赵桥乡双楼村";
		aaaaaa(address);
	}

	public String[] aaaaaa(String address) {
		String[] aa = new String[5];
		String a1 = address.substring(0, address.indexOf("省") + 1);
		String a2 = address.substring(address.indexOf("省") + 1, address.indexOf("市") + 1);
		String a3 = address.substring(address.indexOf("市") + 1, address.indexOf("区") + 1);
		System.out.println(address.indexOf("乡"));
		System.out.println(address.indexOf("镇"));
		String a4 = address.substring(address.indexOf("区") + 1,
				address.indexOf("镇") == -1 ? address.indexOf("乡") + 1 : address.indexOf("镇") + 1);
		String a5 = address.substring(address.indexOf("镇") == -1 ? address.indexOf("乡") + 1 : address.indexOf("镇") + 1,
				address.length());
		aa[0] = a1;
		aa[1] = a2;
		aa[2] = a3;
		aa[3] = a4;
		aa[4] = a5;
		System.out.println(aa[0]);
		System.out.println(aa[1]);
		System.out.println(aa[2]);
		System.out.println(aa[3]);
		System.out.println(aa[4]);
		return aa;
	}

}
