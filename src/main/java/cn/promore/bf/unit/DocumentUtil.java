package cn.promore.bf.unit;

import org.apache.commons.lang3.StringUtils;

public class DocumentUtil {
	
	public static String getdisabilityType(String data){
		if(StringUtils.isBlank(data))return "";
		StringBuffer sb = new StringBuffer();
		for(String temp:data.trim().split(",")){
			if("肢体".equals(temp))sb.append("1,");
			if("智力".equals(temp))sb.append("2,");
			if("精神".equals(temp))sb.append("3,");
			if("听力".equals(temp))sb.append("4,");
			if("视力".equals(temp))sb.append("5,");
			if("语言".equals(temp))sb.append("6,");
			if("多重".equals(temp))sb.append("7,");
		}
		String result = sb.toString().substring(0,sb.toString().lastIndexOf(","));
		return result;
	}
	
	public static String getLivehood(String data) {
		if(StringUtils.isBlank(data))return "";
		StringBuffer sb = new StringBuffer();
		for(String temp:data.trim().split(",")){
			if("低保".equals(temp))sb.append("1,");
			if("医疗救助".equals(temp))sb.append("2,");
			if("临时救助".equals(temp))sb.append("3,");
			if("重度残疾人生活特别救助".equals(temp))sb.append("4,");
			if("贫困精神残疾人药费补助".equals(temp))sb.append("5,");
			if("贫困白内障患者免费复明手术".equals(temp))sb.append("6,");
			if("80周岁以上老人高龄津贴".equals(temp))sb.append("7,");
			if("百岁老人长寿保健费".equals(temp))sb.append("8,");
			if("失地农民养老保障".equals(temp))sb.append("9,");
			if("重性精神病人住院补助".equals(temp))sb.append("10,");
			if("优抚对象".equals(temp))sb.append("11,");
			if("农村籍60周岁以上退役士兵".equals(temp))sb.append("12,");
			if("政府购买居家养老服务".equals(temp))sb.append("13,");
			if("其它".equals(temp))sb.append("14,");
		}
		String result = sb.toString().substring(0,sb.toString().lastIndexOf(","));
		return result;
	}	
	
	public static Integer getWeddingType(String wedding){
		if("未婚".equals(wedding))return 1;
		if("已婚".equals(wedding))return 2;
		if("丧偶".equals(wedding))return 3;
		if("离异".equals(wedding))return 4;
		else return null;
	}
	
	public static Integer getPolitical(String political){
		if("党员".equals(political))return 1;
		if("团员".equals(political))return 2;
		if("民主党派".equals(political))return 3;
		if("群众".equals(political))return 4;
		else return null;		
	}
	
	public static Integer getEducation(String data){
		if("文盲及半文盲".equals(data))return 1;
		if("小学".equals(data))return 2;
		if("初中".equals(data))return 3;
		if("高中".equals(data))return 4;
		if("技工学校".equals(data))return 5;
		if("中专和中技".equals(data))return 6;
		if("大专".equals(data))return 7;
		if("本科".equals(data))return 8;
		if("硕士".equals(data))return 9;
		if("博士".equals(data))return 10;
		else return null;		
	}

	public static Integer getJob(String data) {
		if("无".equals(data))return 0;
		if("国家机关/党群组织/企事业单位负责人".equals(data))return 1;
		if("专业技术人员".equals(data))return 2;
		if("办事人员和有关人员".equals(data))return 3;
		if("商业/服务业人员".equals(data))return 4;
		if("农/林/牧/渔/水利业生产人员".equals(data))return 5;
		if("生产/运输设备操作人员及有关人员".equals(data))return 6;
		if("军人".equals(data))return 7;
		if("不便分类的其他从业人员".equals(data))return 8;		
		else return null;	
		
		
		
		
		/*
		if("计算机软件/硬件".equals(data))return 1;
		if("计算机系统/维修".equals(data))return 2;
		if("通信(设备/运营/服务)".equals(data))return 3;
		if("互联网/电子商务".equals(data))return 4;
		if("网络游戏".equals(data))return 5;
		if("电子/半导体/集成电路".equals(data))return 6;
		if("仪器仪表/工业自动化".equals(data))return 7;
		if("会计/审计".equals(data))return 8;
		if("金融(投资/证券".equals(data))return 9;
		if("金融(银行/保险)".equals(data))return 10;
		if("贸易/进出口".equals(data))return 11;
		if("批发/零售".equals(data))return 12;
		if("消费品(食/饮/烟酒)".equals(data))return 13;
		if("服装/纺织/皮革".equals(data))return 14;
		if("家具/家电/工艺品/玩具".equals(data))return 15;
		if("办公用品及设备".equals(data))return 16;
		if("机械/设备/重工".equals(data))return 17;
		if("汽车/摩托车/零配件".equals(data))return 18;
		if("制药/生物工程".equals(data))return 19;
		if("医疗/美容/保健/卫生".equals(data))return 20;
		if("医疗设备/器械".equals(data))return 21;
		if("广告/市场推广".equals(data))return 22;
		if("会展/博览".equals(data))return 23;
		if("影视/媒体/艺术/出版".equals(data))return 24;
		if("印刷/包装/造纸".equals(data))return 25;
		if("房地产开发".equals(data))return 26;
		if("建筑与工程".equals(data))return 27;
		if("家居/室内设计/装潢".equals(data))return 28;
		if("物业管理/商业中心".equals(data))return 29;
		if("中介服务/家政服务".equals(data))return 30;
		if("专业服务/财会/法律".equals(data))return 31;
		if("检测/认证".equals(data))return 32;
		if("教育/培训".equals(data))return 33;
		if("学术/科研".equals(data))return 34;
		if("餐饮/娱乐/休闲".equals(data))return 35;
		if("酒店/旅游".equals(data))return 36;
		if("交通/运输/物流".equals(data))return 37;
		if("航天/航空".equals(data))return 38;
		if("能源(石油/化工/矿产)".equals(data))return 39;
		if("能源(采掘/冶炼/原材料)".equals(data))return 40;
		if("电力/水利/新能源".equals(data))return 41;
		if("政府部门/事业单位".equals(data))return 42;
		if("非盈利机构/行业协会".equals(data))return 43;
		if("农业/渔业/林业/牧业".equals(data))return 44;
		if("其他行业".equals(data))return 45;*/
	}

	public static String getdisabilityLevel(String data) {
		if("无".equals(data))return "0";
		if("一级伤残".equals(data))return "1";
		if("二级伤残".equals(data))return "2";
		if("三级伤残".equals(data))return "3";
		if("四级伤残".equals(data))return "4";
		return null;
	}

	public static String getRelationship(String data) {
		if("本人".equals(data))return "0";
		if("配偶".equals(data))return "1";
		if("父亲".equals(data))return "2";
		if("母亲".equals(data))return "3";
		if("岳父".equals(data))return "4";
		if("岳母".equals(data))return "5";
		if("公公".equals(data))return "6";
		if("婆婆".equals(data))return "7";
		if("儿媳".equals(data))return "8";
		if("女婿".equals(data))return "9";
		if("子".equals(data))return "10";
		if("女".equals(data))return "11";
		if("孙子（女）".equals(data))return "12";
		if("外孙".equals(data))return "13";
		if("其他亲属".equals(data))return "14";
		if("非亲属".equals(data))return "15";
		return null;
	}
}
