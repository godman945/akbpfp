package alex.com.pchome.adbpfp.spring.init;

import java.io.File;

import org.apache.commons.io.IOUtils;

public class MozJpegTest {

	
	public static void alex(File file) {
		StringBuffer stringBuffer = new StringBuffer();
		File[] files = file.listFiles();
		if (files == null) {
			return; 
		}
		for (File f : files) {
			if (f.isFile()) {
				System.out.println("我是檔案>>>"+f.getAbsolutePath());
				stringBuffer.setLength(0);
	    		stringBuffer.append(" cp ").append(f.getAbsolutePath()).append(" ").append(f.getAbsolutePath().replace(f.getName(), "")).append(f.getName().replace(".jpg", "_new.jpg"));
	    		System.out.println(stringBuffer);
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
			StringBuffer stringBuffer = new StringBuffer();
			File file = new File("/home/webuser/akb/_alex/AC2018011500005/");
//			File file = new File("D:\\Users\\alexchen\\Desktop\\AAAAAAAAA\\ALEX\\");
			
			
			File[] files = file.listFiles();// 獲取目錄下的所有檔案或資料夾 
		    if (files == null) {// 如果目錄為空，直接退出 
		      return; 
		    }
		    for (File f : files) {
		    	if (f.isFile()) {
		    		System.out.println("我是檔案>>>"+f.getAbsolutePath());
//		    		System.out.println("我是檔案999999999999>>>"+f.getName());
		    		
		    		
		    		stringBuffer.setLength(0);
		    		stringBuffer.append(" cp ").append(f.getAbsolutePath()).append(" ").append(f.getAbsolutePath().replace(f.getName(), "")).append(f.getName().replace(".jpg", "_new.jpg"));
		    		System.out.println(stringBuffer);
//					process = Runtime.getRuntime().exec(new String[] { "bash", "-c", stringBuffer.toString()  });
//					String result = IOUtils.toString(process.getInputStream(), "UTF-8");
//					System.out.println(">>>>>>:" + result);
//					System.out.println(IOUtils.toString(process.getErrorStream(),"UTF-8"));
//					System.out.println(IOUtils.toString(process.getInputStream(),"UTF-8"));
		    	}
			    if (f.isDirectory()) {
		    		alex(f);
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
