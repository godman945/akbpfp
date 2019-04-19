package com.pchome.soft.depot.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.FileUtils;

import com.pchome.enumerate.cookie.EnumCookieConstants;

public class EncodeUtil {

	private static final EncodeUtil instance = new EncodeUtil();

    private EncodeUtil() {
    }

    public static EncodeUtil getInstance() {
        return instance;
    }

    private Key initKeyForAES(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (null == key || key.length() == 0) {
            throw new NullPointerException("key not is null");
        }
        SecretKeySpec key2 = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes("utf-8"));
            kgen.init(secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key2 = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException();
        }
        return key2;
    }

    public String encryptAES(String content, String key){
        try{
            SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("UTF-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] result = cipher.doFinal(byteContent);
            return asHex(result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String decryptAES(String content, String key){
        try{
            SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] result = cipher.doFinal(asBytes(content));
            return new String(result, "UTF-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String asHex(byte buf[]){
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++){
            if (((int) buf[i] & 0xff) < 0x10)
                strbuf.append("0");
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    public byte[] asBytes(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++){
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
    	String test = "AC201302060000000003,ray0728,1,中文中文中文中文中文,www.pchome.com.tw/index.html,3,中文,1983-07-28,02-22334455,0922334455,100,111111,525";

    	String testEncode = EncodeUtil.getInstance().encryptAES(test, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
    	System.out.println(">>> testEncode = " + testEncode);
    	String testDecode = EncodeUtil.getInstance().decryptAES(testEncode, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
    	System.out.println(">>> testDecode = " + testDecode);
    	System.out.println(">> testDecode[]  = "+testDecode.split(",").length);
    	
    	File file = new File("/tmp/aes.txt");
    	FileUtils.deleteQuietly(file);
    	FileUtils.writeStringToFile(file, test + "\n" + testEncode + "\n" + testDecode, "UTF-8");
    }
}
