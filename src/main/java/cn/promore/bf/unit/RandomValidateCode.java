package cn.promore.bf.unit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RandomValidateCode {
	public static final String KEY_VALIDATE_CODE = "validate_code";
	public static final int DIGITAL_VALIDATE_CODE = 1;
	public static final int LETTER_VALIDATE_CODE = 2; 
	public static final int MIX_VALIDATE_CODE = 3; 
	
	private static final String PIC_FORMAT = "JPEG";
	private int numberCount = 4;
	private BufferedImage image;
	private int lineSize = 1;
	private int width = 72;
	private int height= 30;
	private String validateCode;
	private	Font font = new Font("Times New Roman",Font.PLAIN,20);
	private int validateCodeType = DIGITAL_VALIDATE_CODE;
	
	public RandomValidateCode() {
		this(DIGITAL_VALIDATE_CODE);
	}
	
	public RandomValidateCode(int validateCodeType) {
		if(validateCodeType == DIGITAL_VALIDATE_CODE 
				|| validateCodeType == MIX_VALIDATE_CODE
				|| validateCodeType == LETTER_VALIDATE_CODE) {
			this.validateCodeType = validateCodeType;
		}
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		generateImage();
	}
	
	/**
	 * @param colorFrom
	 * @param colorEnd
	 * @return
	 */
	private Color generateRandomColor(int colorFrom, int colorEnd) {
		Random random = new Random();
		colorFrom = colorFrom > 255 ? 255 : colorFrom;
		colorEnd  = colorEnd > 255 ? 255 : colorEnd;
		int red = colorFrom + random.nextInt(colorEnd - colorFrom);
		int green = colorFrom + random.nextInt(colorEnd - colorFrom);
		int blue = colorFrom + random.nextInt(colorEnd - colorFrom);
		return new Color(red, green, blue);
	}
	
	private void drawLine(Graphics g){
		Random random = new Random();
		int x= random.nextInt(width);
		int y= random.nextInt(height);
		int xl= random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x,y,x+xl,y+yl);
	}
	
	private void generateImage() {
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setFont(font);
		drawBackground(g, random);
		
		StringBuffer strBuf = new StringBuffer();
		int count = 0;
		while(count < numberCount) {
			int randNum = random.nextInt((int)'z');
			if(this.validateCodeType == MIX_VALIDATE_CODE && ! Character.isLetterOrDigit(randNum)) {
				continue;
			} else if(this.validateCodeType == DIGITAL_VALIDATE_CODE && ! Character.isDigit(randNum)) {
				continue;
			} else if(this.validateCodeType == LETTER_VALIDATE_CODE && ! Character.isLetter(randNum)) {
				continue;
			}
			String randChar = String.valueOf((char)randNum);
			g.setColor(new Color(30+random.nextInt(110), 30+random.nextInt(110), 30+random.nextInt(110)));
			g.drawString(randChar, 16*count+4, 18);
			strBuf.append(randChar);
			count++;
		}
		for(int i=0;i<lineSize;i++){
			drawLine(g);
		}
		validateCode = strBuf.toString();
		g.dispose();
	}

	private void drawBackground(Graphics g, Random random) {
		g.setColor(generateRandomColor(210, 250));
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(255, 255, 255));
		g.drawRect(0, 0, width-1, height-1); 
		
		g.setColor(generateRandomColor(160,200));
		for(int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(16);
			int yl = random.nextInt(16);
			g.drawLine(x,y,x+xl,y+yl);
		}
	}
		
	public void outputImage(OutputStream outputStream) {
		if(outputStream == null)
			return;
		
		try {
			ImageIO.write(image, PIC_FORMAT, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getValidateCode() {
		return this.validateCode;
	}
	
	public void bindingRequest(HttpServletRequest request, HttpServletResponse response) {	
		try {
			request.getSession().setAttribute(KEY_VALIDATE_CODE, validateCode);
			
			//set page to no cache for Web Browser
			response.reset();
			response.setContentType("image/*");
			response.setHeader("Pragma","No-cache");
			response.setHeader("Cache-Control","no-cache");
			response.setDateHeader("Expires", 0);

			//output the image stream into response
			outputImage(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValidateCodeFromSession(HttpSession session) {
		return (String)session.getAttribute(KEY_VALIDATE_CODE);
	}
	
	public static void removeValidateCodeFromSession(HttpSession session) {
		session.removeAttribute(KEY_VALIDATE_CODE);
	}
	
}
