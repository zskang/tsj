package cn.promore.bf.unit;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class UploadHelper {
	/***
	 * 产生一个UUID的字符串
	 * 
	 * @return UUID字符串
	 */
	public static String generateFileName() {
		return DateUtil.getCurrentDate("yyyyMMddHHmmssSSS") + "_" + RandomStringUtils.randomAlphanumeric(6);
	}

	public static String getFileSuffix(String fileName) {
		if (!StringUtils.isNotEmpty(fileName))
			return null;
		if (fileName.indexOf(".") == -1)
			return null;
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public static String parseFileSize(long fileSize) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileSize < 1024) {
			fileSizeString = df.format((double) fileSize) + "B";
		} else if (fileSize < 1048576) {
			fileSizeString = df.format((double) fileSize / 1024) + "K";
		} else if (fileSize < 1073741824) {
			fileSizeString = df.format((double) fileSize / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileSize / 1073741824) + "G";
		}
		return fileSizeString;
	}
	
	
    /**
     * @param distDirPath 目标文件夹保存路径
     * @param fileName    目标文件名 如果为空会自动生成一个UUID名称
     * @param srcFile     用户上传原文件
     * @return 文件保存路径名
     * @throws IOException
     */
    public static String saveFile(String distDirPath, String fileName, File srcFile) throws IOException {
        if (StringUtils.isEmpty(distDirPath))
            return null;
        if (null == srcFile)
            return null;
        fileName = StringUtils.isNotEmpty(fileName) ? fileName : srcFile.getName();
        // fileName = FilenameUtils.getExtension(fileName);
        if (StringUtils.isEmpty(fileName))
            return null;
        File dis_dir = new File(distDirPath);
        if (!dis_dir.exists())
            dis_dir.mkdirs();
        FileUtils.copyFile(srcFile, new File(distDirPath + fileName));
        return distDirPath + fileName;
    }


	public static String secToTime(long time) {
		String timeStr = null;
		long hour = 0;
		long minute = 0;
		long second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(long i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Long.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	// add by zhougang 20170609
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 10; // 5MB

	public static Boolean checkFileCanUpload(File file, String contentType, List<String> allowTypes) {
		if (null == file || StringUtils.isEmpty(contentType))
			return false;
		if (file.length() == 0)
			return false;
		if (!allowTypes.contains(contentType))
			return false;
		if (file.length() > MAX_FILE_SIZE)
			return false;
		return true;
	}

}
