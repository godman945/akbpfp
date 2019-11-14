package alex.com.pchome.adbpfp.spring.init;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class MozJpegTest {

	
	public static void alex(File file) throws Exception {
		Process process = null;
		String result = "";
		StringBuffer stringBuffer = new StringBuffer();
		File[] files = file.listFiles();// 獲取目錄下的所有檔案或資料夾 
	    if (files == null) {// 如果目錄為空，直接退出 
	      return; 
	    }
	    
		
	    
	    for (File f : files) {
	    	if (f.isFile()) {
	    		//1.複製原檔 2.產上新檔
	    		if(f.getAbsolutePath().contains(".jpg") || f.getAbsolutePath().contains(".jpeg")|| f.getAbsolutePath().contains(".png")) {
	    			String fileExtensionName = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(".")+1 , f.getAbsolutePath().length());
		    		String filePath = f.getAbsolutePath().substring(0 , f.getAbsolutePath().lastIndexOf("/")+1);
		    		String fileName = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("/")+1, f.getAbsolutePath().lastIndexOf("."));
		    		if(f.length()/1024 == 0) {
		    			continue;
		    		}
		    		if(StringUtils.isBlank(fileExtensionName) || StringUtils.isBlank(filePath) || StringUtils.isBlank(fileName)) {
		    			continue;
		    		}
		    		System.out.println("*****START　PROCESS*****");
		    		System.out.println("[file]:"+f.getAbsolutePath());
		    		System.out.println("[file path]:"+filePath);
		    		System.out.println("[file name]:"+fileName);
		    		System.out.println("[fileExtensionName]:"+fileExtensionName);
		    		System.out.println("Before Size:"+f.length()/1024+"kb");
		    		
		    		if(fileExtensionName.toUpperCase().equals("PNG") || fileExtensionName.toUpperCase().equals("JPEG")) {
		    			ByteArrayInputStream imageStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(f));
		    			BufferedImage bufferedImage = ImageIO.read(imageStream);
		    			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		    			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
		    			ImageIO.write(newBufferedImage, "jpg", new File(filePath+fileName+".jpg"));
		    			f.delete();
		    		}
		    		
		    		stringBuffer.setLength(0);
					stringBuffer.append(" /opt/mozjpeg/bin/cjpeg  -quality 75 -tune-ms-ssim   -quant-table 0      -progressive      ").append(filePath+fileName+".jpg").append(" >").append(filePath+fileName+"[RESIZE].jpg");
					process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
					result = IOUtils.toString(process.getInputStream(), "UTF-8");
					System.out.println(">>>>>>>>>>command:"+stringBuffer.toString());
					
					stringBuffer.setLength(0);
					stringBuffer.append(" mv ").append(filePath+fileName+"[RESIZE].jpg").append(" ").append(filePath+fileName+".jpg");
					process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
					result = IOUtils.toString(process.getInputStream(), "UTF-8");
					System.out.println(">>>>>>>>>>command:"+stringBuffer.toString());
					File fileNew = new File(filePath+fileName+".jpg");
					System.out.println("After Size:"+fileNew.length()/1024+"kb");
					System.out.println("*****END　PROCESS*****");
	    		}
	    	}
		    if (f.isDirectory()) {
		    	String folderName = f.getPath().toString().substring(f.getPath().toString().lastIndexOf("/")+1 , f.getPath().toString().length());
		    	if(folderName.equals("tmp") || folderName.equals("temporal") ||  folderName.equals("images") || folderName.equals("backup") || folderName.equals("csv_file") || folderName.equals("js") || folderName.equals("prod_ad_sample_file") || folderName.equals("public") || folderName.equals("video") || folderName.equals("catalog")) {
		    		continue;
		    	}
		    	alex(f);
	    	}
	    }
	}
	
	
	
	
	public static void main(String[] args) {
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>> MozJpegTest START");
			Process process = null;
			String result = "";
			StringBuffer stringBuffer = new StringBuffer();
			
			
			
			
			String url = "jdbc:mysql://kddbdev.mypchome.com.tw:3306/akb_video";
			String jdbcDriver = "com.mysql.jdbc.Driver";
			String user = "keyword";
			String password =  "K1y0nLine";
			Connection connect = DriverManager.getConnection(url, user, password);
			connect.setAutoCommit(false); // 设置手动提交 
			Statement statement = connect.createStatement();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ad_detail_seq,ad_detail_content  FROM `pfp_ad_detail` WHERE (ad_detail_content LIKE '%.png%' or ad_detail_content LIKE '%.jpeg%' or ad_detail_content LIKE '%.jpg%') and  `ad_detail_content` not like '%catalog%'   ");
			ResultSet resultSet = statement.executeQuery(sql.toString());
			Map<String,String> imgInfoMap = new HashMap<String,String>();
			while(resultSet.next()){
				String path = "/home/webuser/akb/pfp/"+resultSet.getString("ad_detail_content");
				String id = "ad_detail<TYPE>"+resultSet.getString("ad_detail_seq");
				imgInfoMap.put(id, path);
			}
			
			sql.setLength(0);
			sql.append("SELECT id,ec_img  FROM `pfp_catalog_prod_ec` WHERE (ec_img LIKE '%.png%' or ec_img LIKE '%.jpeg%' or ec_img LIKE '%.jpg%') ");
			ResultSet resultSet2 = statement.executeQuery(sql.toString());
			while(resultSet2.next()){
				String path = "/home/webuser/akb/pfp/"+resultSet2.getString("ec_img");
				String id = "EC<TYPE>"+resultSet2.getString("id");
				imgInfoMap.put(id, path);
			}
			
			
			
			File wri = new File("/home/webuser/akb/_alex/process_mozjpeg_.txt");
			if(wri.exists()) {
				wri.delete();
			}
			FileWriter fileWriter = new FileWriter("/home/webuser/akb/_alex/process_mozjpeg_.txt",true);
			int i = 0;
			for (Entry<String, String> map : imgInfoMap.entrySet()) {
				String key = map.getKey();
				String path = map.getValue();
				File f = new File(path);
    			String fileExtensionName = FilenameUtils.getExtension(path);
	    		String filePath = FilenameUtils.getFullPathNoEndSeparator(path);
	    		String fileName = FilenameUtils.getBaseName(path);
	    		if(StringUtils.isBlank(fileExtensionName) || StringUtils.isBlank(filePath) || StringUtils.isBlank(fileName)) {
	    			continue;
	    		}
	    		if(f.length()/1024 == 0) {
	    			continue;
	    		}
	    		if(!fileExtensionName.toUpperCase().equals("PNG")) {
	    			continue;
	    		}
	    		System.out.println("***** START　PROCESS *****");
	    		System.out.println("[file]:"+f.getAbsolutePath());
	    		if(fileExtensionName.toUpperCase().equals("PNG") || fileExtensionName.toUpperCase().equals("JPEG")) {
	    			ByteArrayInputStream imageStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(f));
	    			BufferedImage bufferedImage = ImageIO.read(imageStream);
	    			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
	    			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
	    			ImageIO.write(newBufferedImage, "jpg", new File(filePath+"/"+fileName+".jpg"));
	    		}
	    		stringBuffer.setLength(0);
	    		stringBuffer.append(" /opt/mozjpeg/bin/cjpeg  -quality 75 -tune-ms-ssim   -quant-table 0      -progressive      ").append(filePath+"/"+fileName+".jpg").append(" >").append(filePath+"/"+fileName+"[RESIZE].jpg");
//	    		System.out.println(">>>>command:"+stringBuffer.toString());
//	    		process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
//				result = IOUtils.toString(process.getInputStream(), "UTF-8");
	    		
	    		stringBuffer.setLength(0);
	    		stringBuffer.append(" mv ").append(filePath+"/"+fileName+"[RESIZE].jpg").append(" ").append(filePath+"/"+fileName+".jpg");
//				process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
//				result = IOUtils.toString(process.getInputStream(), "UTF-8");
//				System.out.println(">>>>>>>>>>command:"+stringBuffer.toString());
	    		i = i + 1;
	    		fileWriter.write("index["+i+"] process["+path+"] to ["+filePath+"/"+fileName+".jpg"+"]  \r\n");
//	    		f.delete();
	    		
	    		System.out.println("index[" + i + "] process[" + path + "]["+f.length()/1024+"kb"+"] to [" + filePath + "/" + fileName + ".jpg"+ "]["+f.length()/1024+"kb"+"]  \r\n");
	    		//開始更新DB資料
	    		sql.setLength(0);
	    		String table = key.split("<TYPE>")[0].equals("ad_detail") ? "pfp_ad_detail" : "pfp_catalog_prod_ec";
	    		String id= key.split("<TYPE>")[1];
	    		String imgDBPath = filePath + "/" + fileName + ".jpg";
	    		imgDBPath = imgDBPath.substring(imgDBPath.indexOf("img"), imgDBPath.length());
	    		if(table.equals("pfp_ad_detail")) {
	    			sql.append(" UPDATE ").append(table).append(" SET ad_detail_content =").append("'").append(imgDBPath).append("'").append(" where 1=1 and ad_detail_seq =").append("'").append(id).append("'");
	    		}else {
	    			sql.append(" UPDATE ").append(table).append(" SET ec_img =").append("'").append(imgDBPath).append("'").append(" where 1=1 and id  =").append("'").append(id).append("'");
	    		}
	    		System.out.println(sql.toString());
//	    		statement.executeUpdate(sql.toString());
	    		System.out.println("***** END　PROCESS *****");
	    		break;
			}
			
			connect.close();
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>> MozJpegTest END");
	}

}
