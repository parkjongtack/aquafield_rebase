package com.soft.web.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class Thumbnail {
	
	/**
	 * Thumbnail 생성
	 * boolean isMake = true; //-- 반드시 생성이 필요할 경우
	 * String source = "D:/test.jpg";
	 * String target = "D:/test_thumb.jpg";
	 * int maxWidth = 500; //-- 0이면 줄이지 않는다.
	 * int maxHeight = 500; //-- 0이면 줄이지 않는다.
	 * if(!tn.makeThumbnail(isMake, source, target, maxWidth, maxHeight) ){
	 * 		System.out.println("Thumbnail 에러");
	 * }
	 * @param isMake
	 * @param source
	 * @param target
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static boolean makeThumbnail(boolean isMake, String source,String target, int maxWidth,int maxHeight){
		boolean result = true;
		boolean thumbMake = false;
		try {			
			//-- 확장자
			String exp = "";
			boolean isExp = false;
			if(source.indexOf('.')>=0){
				exp = source.substring(source.lastIndexOf('.')+1,source.length()).toLowerCase();
				Pattern pattern = Pattern.compile("(jpg|png|gif|jpeg)");
		        Matcher matcher = pattern.matcher(exp);
		        if (matcher.matches()){
		        	isExp = true;
		        }
			}
			if(isExp){
				File originalFileNm = new File(source);
				File thumbnailFileNm = new File(target);
				
				// 원본이미지
				BufferedImage originalImg = ImageIO.read(originalFileNm);
				
				//-- Thumbnail 사이즈 구함
				int width = originalImg.getWidth();
				int height = originalImg.getHeight();
			    if(maxWidth > 0 && width > maxWidth) {
			    	height -= Integer.valueOf(String.valueOf(height*(width-maxWidth)/width) );
			    	width = maxWidth;
			    	thumbMake = true;
			    }
			    if(maxHeight > 0 && height > maxHeight) {
			    	width -= Integer.valueOf(String.valueOf(width*(height-maxHeight)/height) );
			    	height = maxHeight;
			    	thumbMake = true;
			    }
				
			    //-- 반드시 Thumbnail을 생성이 필요하거나, 원본이 커서 줄여야할경우 실행
			    if(isMake || thumbMake){
					// 썸네일 이미지 생성
				    BufferedImage thumbnailImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
					// 썸네일 그리기
					Graphics2D g = thumbnailImg.createGraphics();
					g.drawImage(originalImg, 0, 0, width, height, null);
					// 파일생성
					ImageIO.write(thumbnailImg, exp, thumbnailFileNm);
			    }
			}
			else{
				result = false;
			}			

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
}
