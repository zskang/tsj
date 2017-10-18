package cn.promore.bf.unit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
*@author huzd@si-tech.com.cn or ahhzd@vip.qq
*@version createrTime:2017年7月20日 上午11:11:25
**/
public class ZipUtil {
	
	public static void zip(String srcPath,String distPath) throws Exception{
		 //创建zip输出流
        ZipOutputStream out = new ZipOutputStream( new FileOutputStream(distPath));
        //创建缓冲输出流
        BufferedOutputStream bos = new BufferedOutputStream(out);
        File sourceFile = new File(srcPath);
        //调用函数
        compress(out,bos,sourceFile,sourceFile.getName());
        bos.close();
        out.close();
	}
	
	public static void zips(List<File> sourceFile,String distPath) throws Exception{
		 //创建zip输出流
       ZipOutputStream out = new ZipOutputStream( new FileOutputStream(distPath));
       //创建缓冲输出流
       BufferedOutputStream bos = new BufferedOutputStream(out);
       //调用函数
       compressFiles(out,bos,sourceFile);
       bos.close();
       out.close();
	}
	
	
	public static void compressFiles(ZipOutputStream out, BufferedOutputStream bos,List<File> sourceFile) throws Exception {
		for(File file:sourceFile){
			if (file.exists()) {
				out.putNextEntry(new ZipEntry(file.getName()));
				FileInputStream fos = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fos);
				int tag;
				// 将源文件写入到zip文件中
				while ((tag = bis.read()) != -1) {
					bos.write(tag);
				}
				bis.close();
				fos.close();
			}
		}
	}
	
	public static void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) throws Exception {
		// 如果路径为目录（文件夹）
		if (sourceFile.isDirectory()) {
			// 取出文件夹中的文件（或子文件夹）
			File[] flist = sourceFile.listFiles();
			// 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
			if (flist.length == 0){
				//System.out.println(base + "/");
				out.putNextEntry(new ZipEntry(base + "/"));
			}else{// 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
				for (int i = 0; i < flist.length; i++) {
					compress(out, bos, flist[i], base + "/" + flist[i].getName());
				}
			}
		} else{// 如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
			out.putNextEntry(new ZipEntry(base));
			FileInputStream fos = new FileInputStream(sourceFile);
			BufferedInputStream bis = new BufferedInputStream(fos);
			int tag;
			System.out.println(base);
			// 将源文件写入到zip文件中
			while ((tag = bis.read()) != -1) {
				bos.write(tag);
			}
			bis.close();
			fos.close();
		}
	}
	/**
	public static void main(String[] args) {
		
		File file = new File("/Users/wangjg/Documents/工作文档/我的文档/中铁集团项目文档管理系统/测试问题反馈@170812.docx");
		File file1 = new File("/Users/wangjg/Documents/工作文档/我的文档/中铁集团项目文档管理系统/角色的副本.xls");
		List<File> list = new ArrayList<File>();
		list.add(file1);
		list.add(file);
		try {
			ZipUtil.zip(list,"/Users/wangjg/Documents/工作文档/我的文档/中铁集团项目文档管理系统/juese.zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	**/
}
