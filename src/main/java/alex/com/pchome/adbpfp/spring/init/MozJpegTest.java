package alex.com.pchome.adbpfp.spring.init;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
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
		    		String filePath = f.getAbsolutePath().substring(0 , f.getAbsolutePath().lastIndexOf("\\")+1);
		    		String fileName = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\")+1, f.getAbsolutePath().lastIndexOf("."));
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
		    	String folderName = f.getPath().toString().substring(f.getPath().toString().lastIndexOf("\\")+1 , f.getPath().toString().length());
		    	if(folderName.equals("temporal") ||  folderName.equals("images") || folderName.equals("backup") || folderName.equals("csv_file") || folderName.equals("js") || folderName.equals("prod_ad_sample_file") || folderName.equals("public") || folderName.equals("video") || folderName.equals("catalog")) {
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
			File file = new File("/home/webuser/akb/_alex/img/");
//			File file = new File("D:\\Users\\alexchen\\Desktop\\AAAAAAAAA\\moz_test");
			System.out.println("@@"+file.exists());
			System.out.println("@@"+file.getAbsolutePath());
			File[] files = file.listFiles();// 獲取目錄下的所有檔案或資料夾 
		    if (files == null) {// 如果目錄為空，直接退出 
		      return; 
		    }
		    for (File f : files) {
		    	if (f.isFile()) {
		    		//1.複製原檔 2.產上新檔
		    		if(f.getAbsolutePath().contains(".jpg") || f.getAbsolutePath().contains(".jpeg")|| f.getAbsolutePath().contains(".png")) {
		    			String fileExtensionName = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(".")+1 , f.getAbsolutePath().length());
			    		String filePath = f.getAbsolutePath().substring(0 , f.getAbsolutePath().lastIndexOf("\\")+1);
			    		String fileName = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\")+1, f.getAbsolutePath().lastIndexOf("."));
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
			    	String folderName = f.getPath().toString().substring(f.getPath().toString().lastIndexOf("\\")+1 , f.getPath().toString().length());
			    	if(folderName.equals("temporal") ||  folderName.equals("images") || folderName.equals("backup") || folderName.equals("csv_file") || folderName.equals("js") || folderName.equals("prod_ad_sample_file") || folderName.equals("public") || folderName.equals("video") || folderName.equals("catalog")) {
			    		continue;
			    	}
			    	alex(f);
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>> MozJpegTest END");
	}

}
