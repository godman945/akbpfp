package com.pchome.akbpfp.godutil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.pchome.akbpfp.struts2.BaseCookieAction;

public class CommonUtilModel extends BaseCookieAction{

    private static final long serialVersionUID = 1L;
    //保留小數點格式
    DecimalFormat decimalFormat1 = new DecimalFormat("#,##0.00");
    //不保留小數點格式
    DecimalFormat decimalFormat2 = new DecimalFormat("###,###");
    //正規表示
    Pattern pattern = Pattern.compile("[0-9]+");
    //時間格式取年月日
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");

    	/**
	 * 輸出圖片
	 * */
	public String  writeImg(BufferedImage bufferedImage,String userImgPath,String custimerInfoid,String date,String adSeq) throws Exception{
	    log.info("開始處理圖片:"+adSeq);
	    Date date2 = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
	    ImageIO.write(bufferedImage, "jpg", new File(userImgPath+custimerInfoid+"/"+date+"/original/"+adSeq+".jpg"));
	    ImageIO.write(bufferedImage, "jpg", new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+adSeq+".jpg"));
	    return "img\\"+userImgPath+custimerInfoid+"\\"+date+"\\"+adSeq+".jpg";
	}

	/**
	 * 刪除暫存圖片
	 * */
	public void deleteTemporalImg(String userImgPath,String custimerInfoid,String date,String adSeq) throws Exception{
	    log.info("開始刪除暫存圖片:"+adSeq+".jpg");
	    File folder = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/");
            String[] list = folder.list();
            for(int i = 0; i < list.length; i++){
        	if(!list[i].equals(adSeq+".jpg")){
        	    File file = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+list[i]);
        	    file.delete();
        	}
            }
	}


	/**
	 * 刪除全部暫存圖片
	 * */
	public void deleteAllTemporalImg(String userImgPath,String custimerInfoid,String date) throws Exception{
	    log.info("開始刪除全部暫存圖片");
	    File folder = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/");
            String[] list = folder.list();
            for(int i = 0; i < list.length; i++){
        	File file = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+list[i]);
        	file.delete();
            }
	}


	/**
	 * 1.建立此次上傳圖檔
	 * 2.進行縮圖
	 * */
	public ImageVO createAdImg(String userImgPath,String custimerInfoid,String date,String adSeq) throws Exception{
	    log.info("開始產生廣告圖片:"+adSeq);
	    log.info(">>>>>"+userImgPath+custimerInfoid+"/"+date+"/temporal/");
	    File folder = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/");
	    String[] list = folder.list();
	    String imgUploadPath= "";
	    ImageVO imageVO = new ImageVO();
	    for(int i = 0; i < list.length; i++){
		File file = new File(userImgPath+custimerInfoid+"/"+date+"/temporal/"+list[i]);
		if(adSeq.equals(file.getName().substring(0,file.getName().indexOf(".")))){
		    BufferedImage bufferedImage = ImageIO.read(file);
		    imageVO.setImgWidth(String.valueOf(bufferedImage.getWidth()));
		    imageVO.setImgHeight(String.valueOf(bufferedImage.getHeight()));
		    int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
		    BufferedImage resizedImage = new BufferedImage(90, 90, type);
		    Graphics2D graphics2D = resizedImage.createGraphics();
		    graphics2D.drawImage(bufferedImage, 0, 0, 90, 90, Color.WHITE,null);
		    graphics2D.dispose();
		    ImageIO.write(resizedImage, "jpg", new File(userImgPath+custimerInfoid+"/"+date+"/"+adSeq+".jpg"));
		    imageVO.setImgPath(userImgPath+custimerInfoid+"\\"+date+"\\"+adSeq+".jpg");

		}
	    }
	    return imageVO;
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
