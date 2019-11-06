package alex.com.pchome.adbpfp.spring.init;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class MozJpegTest {

	
	public static void alex(File file) throws Exception {
		Process process = null;
		String result = "";
		StringBuffer stringBuffer = new StringBuffer();
		File[] files = file.listFiles();
		if (files == null) {
			return; 
		}
		for (File f : files) {
			if (f.isFile()) {
				//1.複製原檔 2.產上新檔
	    		if(!f.getAbsolutePath().contains(".jpg")) {
	    			continue;
	    		}
	    		if(f.getAbsolutePath().contains("_bk.jpg")) {
	    			continue;
	    		}
	    		if(new File(f.getAbsolutePath().replace(f.getName(), "")+f.getName().replace(".jpg", "_bk.jpg")).exists()) {
	    			continue;
	    		}
	    		System.out.println("我是檔案>>>"+f.getAbsolutePath());
	    		stringBuffer.setLength(0);
	    		stringBuffer.append(" cp ").append(f.getAbsolutePath()).append(" ").append(f.getAbsolutePath().replace(f.getName(), "")).append(f.getName().replace(".jpg", "_bk.jpg"));
	    		process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
	    		result = IOUtils.toString(process.getInputStream(), "UTF-8");
	    		
	    		stringBuffer.setLength(0);
				stringBuffer.append(" /opt/mozjpeg/bin/cjpeg  -quality 75 -tune-ms-ssim   -quant-table 0      -progressive      ").append(f.getAbsolutePath()).append(" >").append(f.getAbsolutePath()).append(" ").append(f.getAbsolutePath().replace(f.getName(), "")).append(f.getName().replace(".jpg", "_resize.jpg"));
				process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
				result = IOUtils.toString(process.getInputStream(), "UTF-8");
				
				
				stringBuffer.setLength(0);
				stringBuffer.append(" cp ").append(f.getAbsolutePath()).append(" ").append(f.getAbsolutePath().replace(f.getName(), "")).append(f.getName().replace("_resize.jpg", ".jpg"));
				process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
				result = IOUtils.toString(process.getInputStream(), "UTF-8");
	    	}
		    if (f.isDirectory()) {
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
			File file = new File("/home/webuser/akb/_alex/AC2018011500005/");
//			File file = new File("D:\\Users\\alexchen\\Desktop\\AAAAAAAAA\\moz_test");
//			List<File> fileList
			
			File[] files = file.listFiles();// 獲取目錄下的所有檔案或資料夾 
		    if (files == null) {// 如果目錄為空，直接退出 
		      return; 
		    }
		    for (File f : files) {
		    	if (f.isFile()) {
		    		//1.複製原檔 2.產上新檔
		    		if(!f.getAbsolutePath().contains(".jpg")) {
		    			continue;
		    		}
		    		if(f.getAbsolutePath().contains("_bk.jpg")) {
		    			continue;
		    		}
		    		if(new File(f.getAbsolutePath().replace(f.getName(), "")+f.getName().replace(".jpg", "_bk.jpg")).exists()) {
		    			continue;
		    		}
		    		System.out.println("我是檔案>>>"+f.getAbsolutePath());
		    		stringBuffer.setLength(0);
		    		stringBuffer.append(" cp ").append(f.getAbsolutePath()).append(" ").append(f.getAbsolutePath().replace(f.getName(), "")).append(f.getName().replace(".jpg", "_bk.jpg"));
		    		process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
		    		result = IOUtils.toString(process.getInputStream(), "UTF-8");
		    		
		    		stringBuffer.setLength(0);
					stringBuffer.append(" /opt/mozjpeg/bin/cjpeg  -quality 75 -tune-ms-ssim   -quant-table 0      -progressive      ").append(f.getAbsolutePath()).append(" >").append(f.getAbsolutePath()).append(" ").append(f.getAbsolutePath().replace(f.getName(), "")).append(f.getName().replace(".jpg", "_resize.jpg"));
					process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
					result = IOUtils.toString(process.getInputStream(), "UTF-8");
					
					
					stringBuffer.setLength(0);
					stringBuffer.append(" cp ").append(f.getAbsolutePath()).append(" ").append(f.getAbsolutePath().replace(f.getName(), "")).append(f.getName().replace("_resize.jpg", ".jpg"));
					process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
					result = IOUtils.toString(process.getInputStream(), "UTF-8");
		    	}
			    if (f.isDirectory()) {
		    		alex(f);
		    	}
		    }
//		    process.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
