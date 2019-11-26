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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class MozJpegTest {
	
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
			Map<String,String> imgDBInfoMap = new HashMap<String,String>();
			while(resultSet.next()){
				String path = "/home/webuser/akb/pfp/"+resultSet.getString("ad_detail_content");
				String id = "ad_detail<TYPE>"+resultSet.getString("ad_detail_seq");
				imgDBInfoMap.put(id, path);
			}
			
			sql.setLength(0);
			sql.append("SELECT id,ec_img  FROM `pfp_catalog_prod_ec` WHERE (ec_img LIKE '%.png%' or ec_img LIKE '%.jpeg%' or ec_img LIKE '%.jpg%') ");
			ResultSet resultSet2 = statement.executeQuery(sql.toString());
			while(resultSet2.next()){
				String path = "/home/webuser/akb/pfp/"+resultSet2.getString("ec_img");
				String id = "EC<TYPE>"+resultSet2.getString("id");
				imgDBInfoMap.put(id, path);
			}
			
			File wri = new File("/home/webuser/akb/_alex/process_mozjpeg_.txt");
			if(wri.exists()) {
				wri.delete();
			}
			FileWriter fileWriter = new FileWriter("/home/webuser/akb/_alex/process_mozjpeg_.txt",true);
			int i = 0;
			for (Entry<String, String> map : imgDBInfoMap.entrySet()) {
				String key = map.getKey();
				String path = map.getValue();
				File imgPath = new File(path);
    			String fileExtensionName = FilenameUtils.getExtension(path);
	    		String filePath = FilenameUtils.getFullPathNoEndSeparator(path);
	    		String fileName = FilenameUtils.getBaseName(path);
	    		if(StringUtils.isBlank(fileExtensionName) || StringUtils.isBlank(filePath) || StringUtils.isBlank(fileName)) {
	    			continue;
	    		}
	    		if(imgPath.length()/1024 == 0) {
	    			continue;
	    		}
	    		if(fileExtensionName.toUpperCase().equals("GIF")) {
	    			continue;
	    		}
	    		System.out.println("***** START　PROCESS *****");
	    		System.out.println("[file]:"+imgPath.getAbsolutePath());

	    		ImageInputStream stream = new FileImageInputStream(imgPath);
	    		Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
	    		String realFileExtensionName = "";
	    		if (readers.hasNext()) {
		    		ImageReader reader = readers.next();
		    		reader.setInput(stream, true);
		    		realFileExtensionName = reader.getFormatName();
	    		}
	    		long beforeSize = imgPath.length() /1024;
	    		//真實檔格式為png或jpeg 一律重寫圖片
	    		if(realFileExtensionName.toUpperCase().equals("PNG")) {
	    			System.out.println("re new wite img >>>>:"+filePath+"/"+fileName+".jpg");
	    			ByteArrayInputStream imageStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(imgPath));
	    			BufferedImage bufferedImage = ImageIO.read(imageStream);
	    			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
	    			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
	    			ImageIO.write(newBufferedImage, "jpg", new File(filePath+"/"+fileName+".jpg"));
	    			fileExtensionName = "jpg";
	    		}
	    		stringBuffer.setLength(0);
	    		stringBuffer.append(" /opt/mozjpeg/bin/cjpeg  -quality 80 -tune-ms-ssim  -sample 1x1 -quant-table 0 ").append(filePath+"/"+fileName+"."+fileExtensionName).append(" >").append(filePath+"/"+fileName+"[RESIZE].jpg");
	    		System.out.println(">>>>[command]:"+stringBuffer.toString());
	    		process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
				result = IOUtils.toString(process.getInputStream(), "UTF-8");
	    		
	    		stringBuffer.setLength(0);
	    		stringBuffer.append(" mv ").append(filePath+"/"+fileName+"[RESIZE].jpg").append(" ").append(filePath+"/"+fileName+".jpg");
				process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
				result = IOUtils.toString(process.getInputStream(), "UTF-8");
				System.out.println(">>>>>>>>>>[command]:"+stringBuffer.toString());
	    		
				
				i = i + 1;
	    		File newFile = new File(filePath+"/"+fileName+".jpg");
	    		long afterSize = newFile.length() /1024;
	    		
	    		
	    		System.out.println("index[" + i + "] process[" + path + "]["+beforeSize+"kb"+"] to [" + filePath + "/" + fileName + ".jpg"+ "]["+afterSize+"kb"+"]  \r\n");
	    		fileWriter.write("index[" + i + "] process[" + path + "]["+beforeSize+"kb"+"] to [" + filePath + "/" + fileName + ".jpg"+ "]["+afterSize+"kb"+"]  \r\n");
	    		
	    		String table = key.split("<TYPE>")[0].equals("ad_detail") ? "pfp_ad_detail" : "pfp_catalog_prod_ec";
	    		String id= key.split("<TYPE>")[1];
	    		String newImgDBPath = filePath + "/" + fileName + ".jpg";
	    		newImgDBPath = newImgDBPath.substring(newImgDBPath.indexOf("img"), newImgDBPath.length());
	    		String imgOrangelDBPath = path.substring(path.indexOf("img"), path.length());
	    		//1.DB中副檔名不同壓縮過後的檔案副檔名須變更DB資料 
	    		sql.setLength(0);
	    		if( (!FilenameUtils.getExtension(newImgDBPath).toUpperCase().equals(fileExtensionName.toUpperCase())) ||  realFileExtensionName.toUpperCase().equals("PNG")) {
	    			if(table.equals("pfp_ad_detail")) {
		    			sql.append(" UPDATE ").append(table).append(" SET ad_detail_content =").append("'").append(newImgDBPath).append("'").append(" where 1=1 and ad_detail_seq =").append("'").append(id).append("'");
		    			//備份回復用SQL
		    			fileWriter.write(" [BACKUP_SQL] UPDATE "+table+" SET ad_detail_content = '"+imgOrangelDBPath+"' where 1=1 and ad_detail_seq ='"+id+"';    \r\n");
		    		}else {
		    			sql.append(" UPDATE ").append(table).append(" SET ec_img =").append("'").append(newImgDBPath).append("'").append(" where 1=1 and id  =").append("'").append(id).append("'");
		    			//備份回復用SQL
		    			fileWriter.write(" [BACKUP_SQL] UPDATE "+table+" SET ec_img = '"+imgOrangelDBPath+"' where 1=1 and id ='"+id+"';    \r\n");
		    		}
		    		System.out.println(sql.toString());
		    		statement.executeUpdate(sql.toString());
		    		connect.commit();
	    		}
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
