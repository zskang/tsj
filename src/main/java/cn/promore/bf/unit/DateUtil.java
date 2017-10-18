package cn.promore.bf.unit;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 * @version createTime:2016/02/01
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期格式化辅助类
 */
public class DateUtil {
	
	public static String getCurrentDate(String format){
		if(null==format||"".equals(format))return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	public static String formatDateYYYY_MM_DD_HH_MM_SS(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static String formatDate(String format,Date date){
		format = StringUtils.isNotEmpty(format)?format:"yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static String getCurrentDate() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        String smonth = "" + month;
        String sday = "" + day;
        if (month < 10) {
            smonth = "0" + month;
        }
        if (day < 10) {
            sday = "0" + day;
        }
        String date =year+"年"+smonth+"月"+sday+"日 ";
        return date;
    }
	
/*    public static Date formateStringToDate(String format,String date){
    	    if(StringUtil.stringIsNull(date)||StringUtil.stringIsNull(format)) return null;
    	    SimpleDateFormat sdf = new SimpleDateFormat(format);
    	    try {
		return sdf.parse(date);
	    } catch (ParseException e) {
		e.printStackTrace();
		return null;
	    }
    }
	public static String formatDateYYYY_MM_DD(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	

	public static String formatDateYYYYMMDDHHMMSS(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}
	
	public static String formatDateYYYYMM(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(date);
	}	
	public static String getNowDate(String pattern){
		pattern = (null==pattern||"".equals(pattern))?"yyyyMMddHHmmss":pattern;
		return new SimpleDateFormat(pattern).format(new Date());
	}
	public static String formatMsToDay_HH_MM_SS(long ms){
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
		return strDay+"天"+strHour+"时"+strMinute+"分"+strSecond+"秒"+strMilliSecond+"毫秒";
	}
	
	public static String getAheadMonth(String dealDate){
		String year = dealDate.substring(0,4);
		String month = dealDate.substring(4,6);
		//int yearVal = Integer.valueOf(year);
		if (Integer.valueOf(month) - 1 >0) {
			month = String.valueOf(Integer.valueOf(month) - 1) ;
			if (month.length()==1) {
				month = "0"+month;
			}
		}else {
			month = "12";
			year = String.valueOf(Integer.valueOf(year)-1);
		}
		return year+""+month;
		
	}
	*/
	/**
	 * 获得两个日期（yyyy-MM）之间的月份
	 * @throws ParseException 
	 * */
	public static List<String> getBetweenMonth(String format,String startDate,String endDate){
		List<String> result = new ArrayList<String>();
		SimpleDateFormat  sdf = new SimpleDateFormat(format);
		GregorianCalendar gc1=new GregorianCalendar(),gc2=new GregorianCalendar();      
		try {
			gc1.setTime(sdf.parse(startDate));
			gc2.setTime(sdf.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}      
		do{      
		  GregorianCalendar gc3=(GregorianCalendar)gc1.clone();      
		  result.add(sdf.format(gc3.getTime()));      
		  gc1.add(Calendar.MONTH, 1);                   
		}while(!gc1.after(gc2));      
		return result;
	}
}
