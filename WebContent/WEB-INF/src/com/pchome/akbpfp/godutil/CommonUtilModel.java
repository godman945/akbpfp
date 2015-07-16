package com.pchome.akbpfp.godutil;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class CommonUtilModel {

    //保留小數點格式
    DecimalFormat decimalFormat1 = new DecimalFormat("#,##0.00");
    //不保留小數點格式
    DecimalFormat decimalFormat2 = new DecimalFormat("###,###");
    //正規表示
    Pattern pattern = Pattern.compile("[0-9]+");
    //時間格式取年月日
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
    //時間格式取時分秒
    SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss");
    
    
    
    
    
    	/**
	 * 輸出圖片
	 * */
	public void writeImg(BufferedImage bufferedImage,String userImgPath,String user,String date) throws Exception{
	    System.out.println("開始處理圖片");
	    Date date2 = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
	    ImageIO.write(bufferedImage, "jpg", new File(userImgPath+user+"\\"+date+"\\original\\"+user+"_"+date+sdf.format(date2)+".jpg"));
	    int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
	    BufferedImage resizedImage = new BufferedImage(90, 90, type);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(bufferedImage, 0, 0, 90, 90, null);
	    g.dispose();
	    ImageIO.write(resizedImage, "jpg", new File(userImgPath+user+"\\"+date+"\\"+user+"_"+date+sdf.format(date2)+".jpg"));
	}
    
    
    
    
    
    
    
    
    
    
    public String getDecimalFormat1(String data) throws Exception{
//	for (EnumCommon enumData : EnumCommon.values()) {
//	    if(data.getClass().getName().equals(enumData.getStatusDesc())){
//		System.out.println(enumData.name());
//		String methodName = "get"+enumData.name();
//		CommonDataModel commonDataModel = new CommonDataModel();
//		commonDataModel.getClass().getMethod(methodName).invoke(commonDataModel);
////		CommonDataModel commonDataModel = new CommonDataModel();
////		commonDataModel.getClass().getMethod(a);
////		Method  method = c.getDeclaredMethod ("method name", parameterTypes)
////		method.invoke (objectToInvokeOn, params)
//	    }
//	}
	if(!pattern.matcher(data).matches()){
	    return null;
	}
	return null;
    }
    
    
    
}
