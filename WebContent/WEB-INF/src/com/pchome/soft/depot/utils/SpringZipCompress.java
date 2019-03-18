package com.pchome.soft.depot.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class SpringZipCompress {

	private Logger log = Logger.getLogger(this.getClass());

	private static final SpringZipCompress instance = new SpringZipCompress();

	private int borderSize = 5; // border px

	private SpringZipCompress(){
	}

	public static SpringZipCompress getInstance(){
		return instance;
	}
	

	public InputStream getZipStream(Map<String, byte[]> contentMap){
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(fos);

		ByteArrayInputStream in;

		byte[] buffer = new byte[1024];

		int len;

		try{

			for (String fileName : contentMap.keySet()){
				ZipEntry ze = new ZipEntry(fileName);
				try{
					zos.putNextEntry(ze);
				}
				catch (IOException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				in = new ByteArrayInputStream(contentMap.get(fileName));
				len = 0;

				while ((len = in.read(buffer)) > 0){
					zos.write(buffer, 0, len);
				}
				in.close();
				zos.closeEntry();

			}

			// remember close it
			zos.close();

		}
		catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ByteArrayInputStream(fos.toByteArray());

	}
	
	public InputStream getZipStreamByFilePath2(Map<String, String> contentMap){
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(fos);

		ByteArrayInputStream in;
		byte[] buffer = new byte[1024];
		int len;

		try{
			for (String fileName : contentMap.keySet()){
				//File file = new File(contentMap.get(fileName));
				
				File file = FileUtils.toFile(new URL(contentMap.get(fileName)));

				if (file != null){
					ZipEntry ze = new ZipEntry(fileName);
					try{
						zos.putNextEntry(ze);
					}
					catch (IOException e){
						e.printStackTrace();
					}

					in = new ByteArrayInputStream(getBytesFromFile(file));
					len = 0;

					while ((len = in.read(buffer)) > 0){
						zos.write(buffer, 0, len);
					}
					in.close();
					zos.closeEntry();
				}
			}

			// remember close it
			zos.close();
		}
		catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ByteArrayInputStream(fos.toByteArray());
	}

	public InputStream getZipStreamByFilePath(Map<String, String> contentMap){
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(fos);

		ByteArrayInputStream in;
		byte[] buffer = new byte[1024];
		int len;

		try{
			for (String fileName : contentMap.keySet()){
				File file = new File(contentMap.get(fileName));

				if (file != null){
					ZipEntry ze = new ZipEntry(fileName);
					try{
						zos.putNextEntry(ze);
					}
					catch (IOException e){
						e.printStackTrace();
					}

					in = new ByteArrayInputStream(getBytesFromFile(file));
					len = 0;

					while ((len = in.read(buffer)) > 0){
						zos.write(buffer, 0, len);
					}
					in.close();
					zos.closeEntry();
				}
			}

			// remember close it
			zos.close();

		}
		catch (IOException e){
			e.printStackTrace();
		}

		return new ByteArrayInputStream(fos.toByteArray());
	}

	public void openZip(String zipName, File zipFile,String path) throws IOException {
		
		//判斷路徑是否存在,不存在則創建文件路徑
		File pathFile = new File(path);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		
		ZipFile zip = null;
		InputStream in = null;
		OutputStream out = null;
		
		try {
			
			in = new FileInputStream(zipFile);
			out = new FileOutputStream(new File(path + "/" + zipName));
			
			byte[] buf = new byte[1024];        
            int bytesRead;        
            while ((bytesRead = in.read(buf)) > 0) {
            	out.write(buf, 0, bytesRead);
            }
            
            in.close();
            out.flush();
			out.close();
			
			in = null;
			out = null;
			
			zip = new ZipFile(zipFile);
			for(Enumeration entries = zip.entries();entries.hasMoreElements();){
				ZipEntry entry = (ZipEntry)entries.nextElement();
				String zipEntryName = entry.getName();
				in = zip.getInputStream(entry);
				String outPath = (path + "/" + zipEntryName).replaceAll("\\*", "/");
				
				//判斷文件全路徑是否为文件夾,如果是上面已經上傳,不需要解壓
				if(new File(outPath).isDirectory()){
					continue;
				}
				//輸出文件路徑信息
				log.info(outPath);
				
				if(zipEntryName.endsWith("/")){
					//判斷路徑是否存在,不存在則創建文件路徑
					File zipPathFile = new File(outPath);
					if(!zipPathFile.exists()){
						zipPathFile.mkdirs();
					}
				} else {
					File zipPathFile = new File(outPath.substring(0, outPath.lastIndexOf("/")));
					if(!zipPathFile.exists()){
						zipPathFile.mkdirs();
					}
					out = new FileOutputStream(outPath);
					byte[] buf1 = new byte[1024];
					int len;
					while((len=in.read(buf1))>0){
						out.write(buf1,0,len);
					}
					
					out.flush();
					out.close();
				}
				
			}
			
			
		} catch (Exception e) {
			if(out != null){
				out.flush();
				out.close();
			}
			e.printStackTrace();
		} finally {
			if(in != null){
				in.close();
			}
			if(out != null){
				out.flush();
				out.close();
			}
			if(zip != null){
				zip.close();
			}
		}
		log.info("******************解壓完畢********************");
	}
	
	public static byte[] getBytesFromFile(File f){
		if (f == null){
			return null;
		}
		try{
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			byte[] b = new byte[1024];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();
		}
		catch (IOException e){
		}
		return null;
	}
}
