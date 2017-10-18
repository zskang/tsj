package cn.promore.bf.unit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Range;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;

public class ExcelUtil{
	public static Integer EXCEL03_MAX_RECORES = 65536;
	//private static Integer EXCEL07_MAX_RECORES = 1048576;
	
	/**
	 * 
	 * @param response  
	 * @param templatePath 模板地址，可为空
	 * @param fileName 导出文件名称
	 * @param sheetName sheet页名称
	 * @param title 导出文件标题
	 * @param data 
	 * @throws BiffException
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public static void exportExcel(HttpServletRequest request,HttpServletResponse response,String templatePath,String fileName,String sheetName,String[] title,Object[][] data,int offset) throws BiffException, IOException, RowsExceededException, WriteException{
		Workbook template = null; 
		WritableWorkbook wwb = null;
		WritableSheet wSheet = null;
		if(StringUtils.isNotEmpty(templatePath))template = Workbook.getWorkbook(new File(templatePath));
		String agent = request.getHeader("USER-AGENT");
		String fileExt = ".xls";
		if (null != agent){ 
            if (-1 != agent.indexOf("Firefox")) {//Firefox  
            	fileName = "=?UTF-8?B?" + (new String(Base64.encode(fileName.getBytes("UTF-8"))))+ "?=";
            	fileName = fileName+fileExt;  
            }else if (-1 != agent.indexOf("Chrome")) {//Chrome  
            	fileName = new String(fileName.getBytes(), "ISO8859-1");  
            	fileName = fileName+fileExt;  
            } else {//IE7+  
            	fileName = java.net.URLEncoder.encode(fileName, "UTF-8");  
            	fileName = StringUtils.replace(fileName, "+", "%20");//替换空格  
            	fileName = fileName+fileExt;  
            }  
        } else {  
        	fileName = fileName+fileExt;  
        } 
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");
		response.setBufferSize(4096);
		OutputStream out= response.getOutputStream();
		if(null != template){
			 wwb = Workbook.createWorkbook(out,template); 
			 wSheet = wwb.getSheet(0);
		}else{
			 wwb = Workbook.createWorkbook(out);
			 wSheet = wwb.createSheet(sheetName,0);
		}
		Label label = null;
		if(null==template&&null!=title&&title.length>0){
			int x = 0;
			//写入标题
			for(int i = 0;i < title.length;i++){
				label =  new Label(x,0,title[i]);
				//居中
				WritableCellFormat cellFormat=new WritableCellFormat();
			    cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			    label.setCellFormat(cellFormat);
			    
				wSheet.addCell(label);
				x ++;
			}
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		if(null!=data&&data.length>0){
			int y = 1+offset;
			for(Object[] rec:data){
				int z  = 0 ;
				int index = y/EXCEL03_MAX_RECORES;
				if(index>=wwb.getSheets().length){
					wSheet = wwb.createSheet("数据导出"+index,index);
				}
				for(int i=0;i<rec.length;i++){
					Object cellData = rec[i];
					 if(cellData instanceof BigDecimal){
						 BigDecimal bigDecimal = (BigDecimal)cellData;
						 bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
						 label = new Label(z,y%EXCEL03_MAX_RECORES,bigDecimal.toString());
					 }else if(cellData instanceof String) {
						 label = new Label(z,y%EXCEL03_MAX_RECORES,(String)cellData);
					 }else if(cellData instanceof Integer){
						 label = new Label(z,y%EXCEL03_MAX_RECORES,String.valueOf(cellData)); 
					 }else if(cellData instanceof Timestamp){
						 label = new Label(z,y%EXCEL03_MAX_RECORES,df.format(cellData));
					 }else if(cellData instanceof Double){
						 DecimalFormat decimalFormat = new DecimalFormat("###0.##");
						 decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
						 label = new Label(z,y%EXCEL03_MAX_RECORES,""+decimalFormat.format(cellData).toString());
					 }else{
						 label = new Label(z,y%EXCEL03_MAX_RECORES,""+cellData);
					 }
					 //居中
					 WritableCellFormat cellFormat=new WritableCellFormat(NumberFormats.TEXT);
				     cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
				     label.setCellFormat(cellFormat);
				     
					 wSheet.addCell(label);
					 z ++;
				}
				y++;
			}			
		}

		 out.flush();
		 wwb.write();
		 if(null!=template)template.close();
		 wwb.close();	
	}
	/**
	 * 
	 * @param response  
	 * @param templatePath 模板地址，可为空
	 * @param fileName 导出文件名称
	 * @param sheetName sheet页名称
	 * @param title 导出文件标题
	 * @param data 
	 * @throws BiffException
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public static void exportExcel(HttpServletRequest request,HttpServletResponse response,String templatePath,String fileName,String sheetName,
			String[] title,String[][] data,int offset) throws BiffException, IOException, RowsExceededException, WriteException{
		Workbook template = null; 
		WritableWorkbook wwb = null;
		WritableSheet wSheet = null;
		if(StringUtils.isNotEmpty(templatePath))template = Workbook.getWorkbook(new File(templatePath));
		String agent = request.getHeader("USER-AGENT");
		String fileExt = ".xls";
		if (null != agent){ 
			if (-1 != agent.indexOf("Firefox")) {//Firefox  
				fileName = "=?UTF-8?B?" + (new String(Base64.encode(fileName.getBytes("UTF-8"))))+ "?=";
				fileName = fileName+fileExt;  
			}else if (-1 != agent.indexOf("Chrome")) {//Chrome  
				fileName = new String(fileName.getBytes(), "ISO8859-1");  
				fileName = fileName+fileExt;  
			} else {//IE7+  
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");  
				fileName = StringUtils.replace(fileName, "+", "%20");//替换空格  
				fileName = fileName+fileExt;  
			}  
		} else {  
			fileName = fileName+fileExt;  
		} 
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");
		response.setBufferSize(4096);
		OutputStream out= response.getOutputStream();
		if(null != template){
			wwb = Workbook.createWorkbook(out,template); 
			wSheet = wwb.getSheet(0);
		}else{
			wwb = Workbook.createWorkbook(out);
			wSheet = wwb.createSheet(sheetName,0);
		}
		Label label = null;
		if(null==template&&null!=title&&title.length>0){
			int x = 0;
			//写入标题
			for(int i = 0;i < title.length;i++){
				label =  new Label(x,0,title[i]);
				//居中
				WritableCellFormat cellFormat=new WritableCellFormat(NumberFormats.TEXT);
				cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
				label.setCellFormat(cellFormat);
				wSheet.addCell(label);
				x ++;
			}
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null!=data&&data.length>0){
			int y = 1+offset;
			for(String[] rec:data){
				int z  = 0 ;
				int index = y/EXCEL03_MAX_RECORES;
				if(index>=wwb.getSheets().length){
					wSheet = wwb.createSheet("数据导出"+index,index);
				}
				for(int i=0;i<rec.length;i++){
					Object cellData = rec[i];
					if(cellData instanceof BigDecimal){
						BigDecimal bigDecimal = (BigDecimal)cellData;
						bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
						label = new Label(z,y%EXCEL03_MAX_RECORES,bigDecimal.toString());
					}else if(cellData instanceof String) {
						label = new Label(z,y%EXCEL03_MAX_RECORES,(String)cellData);
					}else if(cellData instanceof Integer){
						label = new Label(z,y%EXCEL03_MAX_RECORES,String.valueOf(cellData)); 
					}else if(cellData instanceof Timestamp){
						label = new Label(z,y%EXCEL03_MAX_RECORES,df.format(cellData));
					}else if(cellData instanceof Double){
						 DecimalFormat decimalFormat = new DecimalFormat("###0.##");
						 decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
						 label = new Label(z,y%EXCEL03_MAX_RECORES,""+decimalFormat.format(cellData).toString());
					}else{
						label = new Label(z,y%EXCEL03_MAX_RECORES,""+cellData);
					}
					//居中
					WritableCellFormat cellFormat=new WritableCellFormat();
					cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
					label.setCellFormat(cellFormat);
					
					wSheet.addCell(label);
					z ++;
				}
				y++;
			}			
		}
		
		out.flush();
		wwb.write();
		if(null!=template)template.close();
		wwb.close();	
	}
	
	public static Map<String,String> dealMergedCell(Range[] mergedRanges,List<String> ignoreCell){
		Map<String,String> dealMergedCellMap = new HashMap<String, String>();
		if(null!=mergedRanges&&mergedRanges.length>0){
			for(Range mergedRange:mergedRanges){
				Integer bottomRightColumn = mergedRange.getBottomRight().getColumn();
				Integer bottomRightRow    = mergedRange.getBottomRight().getRow();
				Integer topLeftColumn     = mergedRange.getTopLeft().getColumn();
				Integer topLeftRow        = mergedRange.getTopLeft().getRow();
				if(topLeftColumn == bottomRightColumn && bottomRightRow > topLeftRow){
					dealMergedCellMap.put(topLeftColumn+"_"+topLeftRow, "rowspan='"+(bottomRightRow-topLeftRow+1)+"'");
					for(int startIndex = topLeftRow +1;startIndex<= bottomRightRow;startIndex++){
						ignoreCell.add(topLeftColumn+"_"+startIndex);
					}
				}else if(topLeftRow == bottomRightRow && bottomRightColumn>topLeftColumn){
					dealMergedCellMap.put(topLeftColumn+"_"+topLeftRow, "colspan='"+(bottomRightColumn - topLeftColumn+1)+"'");
					for(int startIndex =topLeftColumn +1;startIndex<= bottomRightColumn;startIndex++){
						ignoreCell.add(startIndex+"_"+bottomRightRow);
					}
				}
			}
		}
		return dealMergedCellMap;
	}
	
	/**
	 * 导出数据到硬盘
	 * @param templatePath       模板路径
	 * @param data               导出数据
	 * @param savePath           保存路径
	 * @throws IOException 
	 * @throws BiffException 
	 * @throws WriteException 
	 */
	public static void exportExcelToDisk(String templatePath,List<Object[]> data,String savePath) throws BiffException, IOException, WriteException {
		Workbook template = null; 
		WritableWorkbook wwb = null;
		WritableSheet wSheet = null;
		int offset = 0;
		if(StringUtils.isNotEmpty(templatePath))template = Workbook.getWorkbook(new File(templatePath));
		FileOutputStream fileOutputStream = new FileOutputStream(savePath);
		if(null != template){
			 wwb = Workbook.createWorkbook(fileOutputStream,template); 
			 wSheet = wwb.getSheet(0);
			 offset = wSheet.getRows();
		}else{
			 wwb = Workbook.createWorkbook(fileOutputStream);
			 wSheet = wwb.createSheet("数据导出",0);
		}
		Label label = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		if(null!=data&&data.size()>0){
			int y = offset;
			for(Object[] rec:data){
				int z  = 0 ;
				int index = y/EXCEL03_MAX_RECORES;
				if(index>=wwb.getSheets().length){
					wSheet = wwb.createSheet("数据导出"+index,index);
				}
				for(int i=0;i<rec.length;i++){
					Object cellData = rec[i];
					 if(cellData instanceof BigDecimal){
						 BigDecimal bigDecimal = (BigDecimal)cellData;
						 bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
						 label = new Label(z,y%EXCEL03_MAX_RECORES,bigDecimal.toPlainString());
					 }else if(cellData instanceof String) {
						 label = new Label(z,y%EXCEL03_MAX_RECORES,(String)cellData);
					 }else if(cellData instanceof Integer){
						 label = new Label(z,y%EXCEL03_MAX_RECORES,String.valueOf(cellData)); 
					 }else if(cellData instanceof Timestamp){
						 label = new Label(z,y%EXCEL03_MAX_RECORES,df.format(cellData));
					 }else if(cellData instanceof Double){
						 DecimalFormat decimalFormat = new DecimalFormat("###0.##");
						 decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
						 label = new Label(z,y%EXCEL03_MAX_RECORES,""+decimalFormat.format(cellData).toString());
					 }else{
						 label = new Label(z,y%EXCEL03_MAX_RECORES,""+cellData);
					 }
					 //居中
					 WritableCellFormat cellFormat=new WritableCellFormat(NumberFormats.TEXT);
				     cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
				     cellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);  
				     label.setCellFormat(cellFormat);
					 wSheet.addCell(label);
					 z ++;
				}
				y++;
			}			
		}
		 fileOutputStream.flush();
		 wwb.write();
		 if(null!=template)template.close();
		 wwb.close();	
		 fileOutputStream.close();
	}
//	public static boolean isDoubleWithPoint(Object vObject){
//		try {
//			if((double)vObject==0)return false;
//			Double dv = (Double)vObject;
//			if(Math.rint(dv) - dv==0)return false;
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//	public static void main(String[] args) {
//		List<Object[]> data = new ArrayList<Object[]>();
//		//账期	地市名称	科目名称	税率	价税合计	税额	不含税收入
//		for (int i = 0; i < 65538; i++) {
//			Object[] r1 = new Object[]{"201702","合肥","你猜"+i,"15%",2002200001.23313131+i,"1"+i,330220001.2131311+i};
//			data.add(r1);
//		}
//		try {
//			ExcelUtil.exportExcelToDisk(null,data,"/Users/huzd/Desktop/11c.xls");
//		} catch (BiffException e) {
//			e.printStackTrace();
//		} catch (WriteException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
