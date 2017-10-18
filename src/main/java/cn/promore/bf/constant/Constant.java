package cn.promore.bf.constant;

/**
 * 常量类 Created by Administrator on 2017/6/2/002.
 */
public class Constant {
	/**
	 * 流程状态
	 */
	public interface STATE {
		static String DTJ = "0";// 待提交
		static String DSH = "1";// 待审核
		static String DFH = "2";// 待复核
		static String YBH = "3";// 已驳回
		static String DGJ = "4";// 待归卷
		static String DJCTB = "5";// 待检查通报
		static String DBZJDJH = "6";// 待编制交底计划
		static String DSP = "7";// 待审批
		static String DBZ = "8";// 待编制
		static String DHZ = "9";// 待汇总
		static String DZL = "10";// 待整理
		static String DHZJZ = "11";// 待绘制矩阵
		static String DQZQR = "12";// 待签字确认
		static String DWJJY = "13";// 待文件借阅
		static String DWJHG = "14";// 待文件核稿
		static String DJYDJ = "15";// 待借阅登记
		static String OVER = "99";// 流程完毕
		static String DFFDJ = "16";// 发放登记
		static String DZGYZ = "17";// 待整改验证
		static String DZDJH = "18";// 待制定计划
		static String DZDJSGL = "19";// 待制定技术管理
		static String DFG = "20";// 待分工
		static String DWJYC = "21";// 待文件阅处
		static String DWJZX = "22";// 待文件执行
		static String DWJGH = "23";// 待文件归还
		static String DGH = "24";// 待归还
		static String DBZGX = "25";// 待标准更新
		static String DBZCXJL = "26";// 待编制查新记录
		static String DTZGX = "27";// 待台账更新
		static String DFHHZ = "28";// 待复核汇总
		static String DHTND = "29";// 待合同拟定
		static String DHTJS = "30";// 待合同结算
		static String DWZZKJH = "31";// 待物资总控计划
		static String DHYJYXC = "32";// 待会议纪要形成
		static String DXCJDHYJL = "33";// 待形成交底会议记录
		static String DGJGXJD = "34";// 待关键工序界定
		static String DBZQD = "35";// 待编制清单
		static String DTSGCJD = "36";// 待特殊过程界定
		static String DDJYQGLTZ = "37";// 待登记仪器管理台账
		static String DBZKCJL = "38";// 待编制勘查记录
		static String DBZJJZJL = "39";// 待编制交接桩记录
		static String DJSBZ = "40";// 待计算编制
		static String DBJJSJDFA = "41";// 待编辑技术交底方案
		static String DJDXF = "42";// 待交底下发
		static String DBZJDJHS = "43";// 待编制交底计划书
		static String DSGFYJL = "44";// 待施工放样记录
		static String DGCCLJL = "45";// 待高程测量记录
		static String DFYTJJL = "46";// 待放样图解记录
		static String DXFWFCJL = "47";// 待下发网复测记录
		static String DKZWFCJLJS = "48";// 待控制网复测记录接收
		static String DYC = "49";// 待阅处
		static String DJGCL = "50";// 待竣工测量
		static String DHZSH = "51";// 待汇总审核
		static String DHF = "52";// 待回复 
	}

	/**
	 * 业务类型
	 */
	public interface TYPE {
		static String INDEX = "INDEX";
		static String DO = "DO";
		static String MAIN = "MAIN";
		static String BOOK = "BOOK";// 台账
	}

	/**
	 * 节点数量
	 */
	public interface NODECONSTANT {
		public static int NODE_JSJDJH = 4;
		public static int NODE_JSJDMX = 6;
		public static int NODE_JSJDJC = 4;
	}

	/**
	 * 流程key
	 */
	public interface WFKEY {
		public static String 工序技术交底_计划 = "gxjsjd_36_index";
		public static String 工序技术交底_执行 = "gxjsjd_36_do";
		public static String 工序技术交底_检查 = "gxjsjd_36_check";
		public static String 安全技术交底_计划 = "aqjsjd_36_index";
		public static String 安全技术交底_执行 = "aqjsjd_36_do";
		public static String 安全技术交底_检查 = "aqjsjd_36_check";
		public static String 施组_计划 = "szgl_18_index";
		public static String 施组_编制 = "szgl_18_do";
		public static String 特级_I_II = "tjsgfa_20_index";
		public static String III = "IIIjsgfa_21_index";
		public static String 施工方案编制计划 = "sgfabzjh_19_do";
	}

	public static String getWfNameByFileName(String fileName) {
		if ("gxjsjd_36_index.zip".equals(fileName)) {
			return "工序技术交底【计划】流程";
		}
		if ("gxjsjd_36_do.zip".equals(fileName)) {
			return "工序技术交底【执行】流程";
		}
		if ("gxjsjd_36_check.zip".equals(fileName)) {
			return "工序技术交底【检查】流程";
		}
		if ("aqjsjd_36_index.zip".equals(fileName)) {
			return "安全技术交底【计划】流程";
		}
		if ("aqjsjd_36_do.zip".equals(fileName)) {
			return "安全技术交底【执行】流程";
		}
		if ("aqjsjd_36_check.zip".equals(fileName)) {
			return "安全技术交底【检查】流程";
		}

		if ("zyzds_24_index.zip".equals(fileName)) {
			return "作业指导书【编制】流程";
		}

		if ("kgbg_04_main.zip".equals(fileName)) {
			return "开工报告【编制】流程";
		}
		if ("fgbg_04_main.zip".equals(fileName)) {
			return "复工报告【编制】流程";
		}
		if ("tgbg_04_main.zip".equals(fileName)) {
			return "停工报告【编制】流程";
		}

		if ("gf_14_gx.zip".equals(fileName)) {
			return "规范【查新】流程";
		}
		if ("gf_14_sj.zip".equals(fileName)) {
			return "规范【收集】流程";
		}

		if ("tj_14_gx.zip".equals(fileName)) {
			return "图集【查新】流程";
		}
		if ("tj_14_sj.zip".equals(fileName)) {
			return "图集【收集】流程";
		}

		if ("gfxzdgcsgfazxqkybb_22_do.zip".equals(fileName)) {
			return "高风险重点工程施工方案【执行情况】【月报】流程";
		}

		if ("gjgx_23_main.zip".equals(fileName)) {
			return "关键工序【管理】流程";
		}
		if ("tsgc_23_main.zip".equals(fileName)) {
			return "特殊过程【管理】流程";
		}

		if ("gqgl_05_index.zip".equals(fileName)) {
			return "工期管理【计划】流程";
		}
		if ("gqgl_05_jd.zip".equals(fileName)) {
			return "工期管理【季度计划编制】流程";
		}
		if ("gqgl_05_nd.zip".equals(fileName)) {
			return "工期管理【年度计划编制】流程";
		}
		if ("gqgl_05_yd.zip".equals(fileName)) {
			return "工期管理【月度计划编制】流程";
		}
		if ("gqgl_05_zjh.zip".equals(fileName)) {
			return "工期管理【周计划编制】流程";
		}

		if ("IIIjsgfa_21_index.zip".equals(fileName)) {
			return "III级施工方案【编制】流程";
		}

		if ("IIIjsgfa_21_check.zip".equals(fileName)) {
			return "III级施工方案【检查】流程";
		}

		if ("jsfwht_15_index.zip".equals(fileName)) {
			return "技术服务【合同拟定】流程";
		}

		if ("jsfwht_15_do.zip".equals(fileName)) {
			return "技术服务【合同结算】流程";
		}

		if ("jsgljd_02.zip".equals(fileName)) {
			return "技术管理【交底】流程";
		}

		if ("jsglzd_06_do.zip".equals(fileName)) {
			return "技术管理【制度制定】流程";
		}

		if ("jsgzjj_09_index.zip".equals(fileName)) {
			return "技术工作【交接】流程";
		}

		if ("jspx_08_index.zip".equals(fileName)) {
			return "技术培训【计划】流程";
		}

		if ("jspx_08_do.zip".equals(fileName)) {
			return "技术培训【执行】流程";
		}
		if ("jsglzd_06_index.zip".equals(fileName)) {
			return "技术管理【制度计划】流程";
		}

		if ("jsryxxgwzzjfg_07_main.zip".equals(fileName)) {
			return "技术队伍体系【工作任务管理】流程";
		}

		if ("jxsbxqjh_17_do.zip".equals(fileName)) {
			return "机械设备【需求计划】流程";
		}

		if ("nbwjgl_11_index.zip".equals(fileName)) {
			return "内部文件【管理】流程";
		}

		if ("sgdc_01_index.zip".equals(fileName)) {
			return "施工调查【计划】流程";
		}

		if ("sgdc_01_do.zip".equals(fileName)) {
			return "施工调查【执行】流程";
		}
		if ("sgfabzjh_19_do.zip".equals(fileName)) {
			return "施工方案【编制计划】流程";
		}

		if ("szgl_18_index.zip".equals(fileName)) {
			return "制定施组【计划】流程";
		}
		if ("szgl_18_do.zip".equals(fileName)) {
			return "施组【编制】流程";
		}
		if ("szgl_18_jc.zip".equals(fileName)) {
			return "施组实施情况【检查】流程";
		}

		if ("tjsgfa_20_index.zip".equals(fileName)) {
			return "特级（I级、II级）施工方案【编制】流程";
		}

		if ("tjsgfa_20_do.zip".equals(fileName)) {
			return "特级（I级、II级）施工方案【检查】流程";
		}

		if ("wjffjl_13_index.zip".equals(fileName)) {
			return "文件发放【记录】流程";
		}

		if ("wjjyjl_12_index.zip".equals(fileName)) {
			return "文件借阅【记录】流程";
		}
		if ("wlwjgl_10_index.zip".equals(fileName)) {
			return "外来文件【管理】流程";
		}
		if ("wzzkjhtz_16_index.zip".equals(fileName)) {
			return "物资总控计划台账【计划制定】流程";
		}

		if ("wzzkjhtz_16_do_jd.zip".equals(fileName)) {
			return "物资总控计划台账【季度需求计划制定】流程";
		}

		if ("wzzkjhtz_16_do_yd.zip".equals(fileName)) {
			return "物资总控计划台账【月度需求计划制定】流程";
		}

		if ("wzzkjhtz_16_do_nd.zip".equals(fileName)) {
			return "物资总控计划台账【年度需求计划制定】流程";
		}

		if ("wzzkjhtz_16_do_rwz.zip".equals(fileName)) {
			return "物资总控计划台账【日物资需求计划制定】流程";
		}

		if ("xmglchs_03_do.zip".equals(fileName)) {
			return "项目管理策划书【汇总编制】流程";
		}

		if ("xmglchs_03_main.zip".equals(fileName)) {
			return "项目管理策划书【提纲制定】流程";
		}

		if ("clyqgl_25_process.zip".equals(fileName)) {
			return "测量仪器管理流程";
		}

		if ("gjgxgl_23_process.zip".equals(fileName)) {
			return "关键工序管理流程";
		}
		
		if ("tsgcgl_23_process.zip".equals(fileName)) {
			return "特殊过程管理流程";
		}
		

		if ("sgfy_28_process.zip".equals(fileName)) {
			return "施工放样管理流程";
		}

		if ("gccl_28_process.zip".equals(fileName)) {
			return "高程测量管理流程";
		}

		if ("fytj_29_process.zip".equals(fileName)) {
			return "放样图解管理流程";
		}

		if ("cljs_27_process.zip".equals(fileName)) {
			return "测量计算成果书";
		}

		if ("kzwfcgl_30_process.zip".equals(fileName)) {
			return "控制网复测";
		}
		if ("jkcl_31_process.zip".equals(fileName)) {
			return "监控测量";
		}
		if ("jgcl_32_process.zip".equals(fileName)) {
			return "竣工测量";
		}
		if ("swtz_33_process.zip".equals(fileName)) {
			return "设计文件收文台账";
		}

		if ("fftz_33_process.zip".equals(fileName)) {
			return "设计文件发放台账";
		}
		if ("sjwjsh_34_process.zip".equals(fileName)) {
			return "设计文件审核";
		}
		if ("sjbg_35_process.zip".equals(fileName)) {
			return "设计变更";
		}
		if ("sjbgqr_35_process.zip".equals(fileName)) {
			return "设计变更签认";
		}
		if ("sggx_37_process.zip".equals(fileName)) {
			return "施工工序台账管理";
		}
		if ("jstzd_38_process.zip".equals(fileName)) {
			return "技术通知单及整改记录管理";
		}
		if ("sgpz_39_process.zip".equals(fileName)) {
			return "施工旁站管理";
		}
		if ("lxzd_40_process.zip".equals(fileName)) {
			return "六项制度执行情况检查";
		}
		if ("wxygl_41_process.zip".equals(fileName)) {
			return "危险源管理资料归集";
		}
		if ("syzlgj_42_process.zip".equals(fileName)) {
			return "试验资料归集";
		}
		if ("jyp_43_process.zip".equals(fileName)) {
			return "检验批工作任务";
		}
		if ("shgcsl_45_process.zip".equals(fileName)) {
			return "实耗工程数量管理";
		}
		if ("sgyx_47_process.zip".equals(fileName)) {
			return "施工影像管理";
		}
		if ("sgjszj_48_process.zip".equals(fileName)) {
			return "施工技术总结管理";
		}
		if ("gxjszj_48_process.zip".equals(fileName)) {
			return "工序技术总结管理";
		}
		if ("kyjgf_49_process.zip".equals(fileName)) {
			return "科研及工法立项管理";
		}
		if ("kyjgfgczl_49_process.zip".equals(fileName)) {
			return "科研及工法过程资料管理";
		}
		if ("zl_50process.zip".equals(fileName)) {
			return "专利工作任务";
		}
		if ("qcxz_51_process.zip".equals(fileName)) {
			return "QC小组活动管理";
		}
		if ("qchdss_51_process.zip".equals(fileName)) {
			return "QC活动实施及总结";
		}
		if ("sgrz_52_process.zip".equals(fileName)) {
			return "施工日志任务";
		}
		if ("sgrzjc_52_process.zip".equals(fileName)) {
			return "施工日志定期检查";
		}
		if ("gkjc_53_process.zip".equals(fileName)) {
			return "管控检查";
		}
		if ("jgzlbz_54_process.zip".equals(fileName)) {
			return "竣工资料编制计划";
		}
		if ("jgzlwj_54_process.zip".equals(fileName)) {
			return "竣工资料文件";
		}
		if ("gcslzg_44_process.zip".equals(fileName)) {
			return "工程数量总控台账";
		}
		if ("sfgl_46_process.zip".equals(fileName)) {
			return "收方管理";
		}
		if ("jsyzlmb_process.zip".equals(fileName)) {
			return "技术员资料模板任务";
		}
		if ("jszgzlmb_process.zip".equals(fileName)) {
			return "技术主管资料模板任务";
		}
		if ("gcbzzlmb_process.zip".equals(fileName)) {
			return "工程部长资料模板任务";
		}
		if ("xmzgzlmb_process.zip".equals(fileName)) {
			return "项目总工资料模板任务";
		}
		if ("fbfx_process.zip".equals(fileName)) {
			return "分部分项工程划分";
		}
		if ("gzrwfb_process.zip".equals(fileName)) {
			return "工作任务分布";
		}
		if ("zyzd_24_process.zip".equals(fileName)) {
			return "作业指导书";
		}
		if ("gcjjz_26_process.zip".equals(fileName)) {
			return "工程交接桩";
		}
		
		if ("sjgcjszlgl_55_process.zip".equals(fileName)) {
			return "首件工程技术资料管理";
		}
		
		if ("sjwjshjh_34_process.zip".equals(fileName)) {
			return "设计文件审核计划";
		}
		
		if ("kyjgfjt_49_process.zip".equals(fileName)) {
			return "科研及功法结题";
		}
		
		return null;
	}

}
