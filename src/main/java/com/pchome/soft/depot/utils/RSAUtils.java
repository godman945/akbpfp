package com.pchome.soft.depot.utils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

public class RSAUtils {

	private static final Logger log = LogManager.getRootLogger();

	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgAkCremk6gUuWfnA/pphMNTWChmfm8oBZn8CoPYCjilRvpHCdcCGOWJM7faTWs1qYfuNmMtB5VfYsL+CjCubVt+2cEbUaWZ5wiu30Kmwl+BqcV7a9IRJK7k7pEVcuOB0CZbF/gM57qyCumffnSl9+p7eEi9HxNPaj762286673QIDAQAB";

	private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKACQKt6aTqBS5Z+cD+mmEw1NYKGZ+bygFmfwKg9gKOKVG+kcJ1wIY5Ykzt9pNazWph+42Yy0HlV9iwv4KMK5tW37ZwRtRpZnnCK7fQqbCX4GpxXtr0hEkruTukRVy44HQJlsX+AznurIK6Z9+dKX36nt4SL0fE09qPvrbbzrrvdAgMBAAECgYBwouVPUO5+ZsIFAKCXzfhrvREb/u1pX9AGRzThudmyRhBGPuWfjm2wvJ14RWwiT0M5EimVqbOjlmWimVlyrJvtPWMsop+erH7bYTqFuAkyXhfvwoqj1e+tlmT9rYUIUNwCd6y70TfgN0CAXi0isi35h6ohHBvGBdnsTQ/fR4nPKQJBAND1OnRJUhHugRGDKfTT/cprg1yTUlkVkSMwIbtERtzTYpjwxLutF5ed8CmfsO5RlTD+scQ38rqZaQjuX8USNa8CQQDEB/aCG2+bA7GL37QCkkAh0YN8o9HyLH6hCHLo/JNEUA3C+AA+lCYCZLnDRbqp9ZYm++Vg+PLfF0RLxcOz3xYzAkBaYnjSbBSv5Pa3WIEBgeE5eZ/sn1zzY7zP97Xfhv0P7++qMBspAwo9bve/SXAC5g8ejkzej0TTKiCg2Ftcpy/JAkEArelamub4RpAqTatzKezSFK6pdkRoF++9j+PM/kJF7I8RBm262cnZRrpRy5nrFqmYQpGrWDLEVYNwxcB39PXv3QJAEkangCuzjQGGZzmQIn1jbsNLcBq7lIY0/KH4HJ8sJRdq+tdNYKrk8Nq41QuhgX6jpCf3t1l57JmEUQd9G4K6pA==";

	
	public static final String PUBLIC_KEY_2048 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAssih/RnMgundQh706TJSW4PsABvXeW4EutZ0ClIlu+iSNHz1JMpqYjfVJ1QwD2NZXnmOVwUEcnFRCEOFIZahuNGwXKz8lGvsDu+NRakQJKxrZrJMr5q0fbk7sopJopVpNx6gJ4H/ygoHToaqLiOuQt+sXRsIGv26+HnAthEVYJtB5kCHygigLiLKGXtFJjLl1VYgyOp4KpHqhZ6/RpsH5f2DWdDaLa7mU4s6LJjx742TihtO/FmTstPHcm0mo6dD+3vtrIcj9tSlIqp/gQ0bcKwc6nPUuhMKvpgNusWk2u3HxT6RzDkAAn30U5w/1R3qmzWavQOgRgUNzNkP9qWk/wIDAQAB";
	public static final String PRIVATE_KEY_2048 = "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCyyKH9GcyC6d1CHvTpMlJbg+wAG9d5bgS61nQKUiW76JI0fPUkympiN9UnVDAPY1leeY5XBQRycVEIQ4UhlqG40bBcrPyUa+wO741FqRAkrGtmskyvmrR9uTuyikmilWk3HqAngf/KCgdOhqouI65C36xdGwga/br4ecC2ERVgm0HmQIfKCKAuIsoZe0UmMuXVViDI6ngqkeqFnr9Gmwfl/YNZ0NotruZTizosmPHvjZOKG078WZOy08dybSajp0P7e+2shyP21KUiqn+BDRtwrBzqc9S6Ewq+mA26xaTa7cfFPpHMOQACffRTnD/VHeqbNZq9A6BGBQ3M2Q/2paT/AgMBAAECggEAOLWTTGlGibAM0tBm9yFTJlwuG8q5CndvYKRtomh0N6qJ1cRdLhJWtFjpb9QwenEVA/aiw89/nZ18CpcAP6s8GRfKTVtBswKYmhQ6R4Jc8IUQfD7KPFliLw72dKeQu9WQzj8UpfOCY1/S+YGVsZT3zZaNYesVIxqgl/hmvFmm4kFUMFqwO5aL+9HPLHhHrr2mT+Sn/0dWlMYnMWuKyQFgkJlZtEqrE3Z7avJccu6MJ23A1PMIvbsLPgIw2Lwotd8ePzy85UNpcek2diYP+g7LOmNdoghRPbvK4f7yjZyZIjF/F6PB1BaWUJuQSF3pWuxpk+Z4mRDJ2SPU5+Qc0PaMwQKBgQD0pPfYcyrkpCbCSnDfmU1m5fjLcHCqCl9dAkJ1cep7c7/JzKjxUJlnloLVnTZU7zFkYcTrvOs4fAgklJKjt7uEy0AJPmNkkH45NXNtWxI5GLXo6UOBCaF+gK6i8/VRnINZVXdAYgavsIt8+OpgWJxPUGFY3eV0XkDUXTwZVuKeKQKBgQC7FRAgzfDNGGuENZrfO42av1l+FXE2D6/FIcsrFhqVlYlPLjqWLjHVOE5+/xFyBeckI7Vv5eFp7fkEnC293BxdO22kd5f3Suqv6YOki5FOTiCQ10thD/5fS6HvbLqqpP2A5RQxi8lDeS7+q40nWGClYvctCmZM4p9zqexo5GM+5wKBgQCn4Hxi/8SzWSFvuf1yspQ2xCDSGbp2NN/zxxUr2pv5UxiMtfBIh/J87v+g7lzWM71FEQI8cktHW3WLBJkL14zKN18d+L3jyHOVdmRa6l+65oYN6eP0rFMxp8Qu9hGliy1nJArNF3dXIGLXw0eyDZxVoObsQHiwPe1mzQPsr36a4QJ/JgAhRoiOYRqUvEYnLzDpkoVLqFCyrkiBa4lxu07CF6BIQ80gGWFIvgax6xnkcUTBrpWSRShsoRe1fTo8EFSlbkqj+sRQGesdwJ2uH745xa4Y71LL/OtCwBiczAVJ5V+eHpBpiabbOtlislgmU9efyfhQPNXf8b4WpWfenW/GuQKBgHwIme9eeHeuZGPdElg4dj/1Y1E0MshkVSGfyuE/nAZXAI+1fRT68U/RhBGlTxRUcWkNLfed+0Xv6iQuHsexP1bYa1Bjzv25vyIzi+H5ygLZ1R+LHyICiuy3af0P3eaxN9CaSfq9OAwYqUDB/5wwSeyBiyIwdVVMHYWTXauLawWt";
	
	
	public static PublicKey getPublicKey(String key) throws Exception {
          byte[] keyBytes = Base64.decodeBase64(key.getBytes());
          X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
          PublicKey publicKey = keyFactory.generatePublic(keySpec);
          return publicKey;
    }

	public static PrivateKey getPrivateKey(String key) throws Exception {
          byte[] keyBytes = Base64.decodeBase64(key.getBytes());
          PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
          PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
          return privateKey;
    }

    /**
     * 加密
     */
    public static String encode(String str) {
    	String result = null;

    	try {

    		PublicKey pubKey = getPublicKey(PUBLIC_KEY);

    		//分段加密 -> 1024 編碼的 RSA key 一次最多只能壓 118 bytes 的長度, 所以長度超過要分段加密, 解密亦然
    		Cipher cipher = Cipher.getInstance("RSA");
        	cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        	byte[] bytes = str.getBytes("UTF-8");
        	byte[] encodedByteArray = new byte[] {};
        	for (int i = 0; i < bytes.length; i += 100) {
        		byte[] subarray = ArrayUtils.subarray(bytes, i, i + 100);
        		byte[] doFinal = cipher.doFinal(subarray);
        		encodedByteArray = ArrayUtils.addAll(encodedByteArray, doFinal);
        	}

        	//一定要強制 utf-8 編碼, 則中文亂碼, 解碼亦然
        	result = new String(Base64.encodeBase64(encodedByteArray), "UTF-8");

    	} catch (Exception e) {
    		log.error(e.getMessage(), e);
    		e.printStackTrace();
    	}

    	return result;
    }

    /**
     * 解密
     */
    public static String decode(String strEncode) {
    	String result = null;

    	try {

    		PrivateKey priKey = getPrivateKey(PRIVATE_KEY);

    		//分段解密
    		Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] decodeByteArray = Base64.decodeBase64(strEncode.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<decodeByteArray.length; i += 128) {
            	byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(decodeByteArray, i, i + 128));
            	sb.append(new String(doFinal, "UTF-8"));
            }

            result = sb.toString();

    	} catch (Exception e) {
    		log.error(e.getMessage(), e);
    		e.printStackTrace();
    	}

    	return result;
    }

    

    /**
     * 加密
     * @param publicKey
     * @param srcBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encrypt(RSAPublicKey publicKey,byte[] srcBytes)
        throws NoSuchAlgorithmException, NoSuchPaddingException,
               InvalidKeyException, IllegalBlockSizeException,
               BadPaddingException
    {
        if(publicKey == null){ return null; }

        // Cipher 負責完成加密或解密工作，基於 RSA
        Cipher cipher = Cipher.getInstance("RSA");
        // 根據公鑰，對 Cipher 物件進行初始化
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] resultBytes = cipher.doFinal(srcBytes);
        return resultBytes;
    }

    /**
     * 解密
     * @param privateKey
     * @param srcBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decrypt(RSAPrivateKey privateKey,byte[] srcBytes)
        throws NoSuchAlgorithmException, NoSuchPaddingException,
               InvalidKeyException, IllegalBlockSizeException,
               BadPaddingException
    {
        if(privateKey == null){ return null; }

        // Cipher負責完成加密或解密工作，基於RSA
        Cipher cipher = Cipher.getInstance("RSA");
        // 根據公鑰，對 Cipher 物件進行初始化
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] resultBytes = cipher.doFinal(srcBytes);
        return resultBytes;
    }
    
    
    public static void main(String[] args) throws Exception {
    	String buKey = "mBfKi8Wc5AjAcMaGLXL-SOgMJ5CFf_CnrG5v2rH0ERcdj7trHs0EEGwGFeLuCK5JdDh8cLQqF2jOOprAYlHmY9Bf2E8cRSvvQsPyxjy4_j6JYPzD3sIGsFApCj7LdGMw7bqV5zHtu036djFjCtfYW8eu_lrXosbWnVRQuFcopbaGVnqNZv2sEZwpXZtZrIOJ2QOtAtYnvVvSOLKsp-3HgD06hTm0m1fBokOSUoskIhaONFpJ8BptXlhc7W0PePlMx_NfgGNuUKVOVTnlC_WV0ylVSIa2h8CDjSpZ5RqbroUI1r96EYgQsxdHdALn8qugaxu8Dx5tXW6LhgOtSqL2iQ";
    	
    	RSAPrivateKey privateKey = (RSAPrivateKey)RSAUtils.getPrivateKey(RSAUtils.PRIVATE_KEY_2048);
		byte[] decBytes = RSAUtils.decrypt(privateKey, Base64.decodeBase64(buKey));
		JSONObject buInfoJson = new JSONObject(new String(decBytes));
		System.out.println(buInfoJson);
    	
    	
//		{"pfdc":"PFDU20150630001","buId":"V000001","buName":"pcstore","url":"http://www.mypcstore.com.tw/sammi/"}
    	
    	
    	
    	//String strTest = "<member><id>richard0120</id><pwd>nissan370z</pwd></member>";
    	//String strTest = "<member><email>test9965@pchome.com.tw</email></member>";
//    	String strTest = "{\"id\":\"barthes\",\"user_name\":\"趴趴\",\"sexuality\":\"F\",\"birthday\":\"2012-12-21\",\"phone\":\"02-22668855\",\"cellphone\":\"0922555666\",\"zipcode\":\"106\",\"address\":\"中華民國萬萬歲17號\"}";
//       	System.out.println(">>> strTest = " + strTest);
//
//    	//test encode
//    	String strEncode = encode(strTest);
//    	System.out.println(">>> strEncode = " + strEncode);
//    	//System.out.println(">>> strEncode length = " + strEncode.getBytes().length);
//
//    	//test decode
//    	//strEncode = "RhEBhGjDKmSkwpNLt2m4uC1AvM9Zu6W738lMiZM2L+oUpZVOF/cF3FT1IRolSb5wDhHKFpSDvUe55ABJSVVa6yEJaJTII7hRhdTrZJkkkAibZ0RgbwRGCOilvcWalIhYVR054aV1YZAHEbcAX/2NjF1pBnaiJrcz92LIN6I1qN4Iyx5IQ07Cigjx3/GAWnUEjyl/E+yX/yY6EQlHck5tRjpKaADJXNcbT80PL4S/xu8+n7lTcAt+sZ5fBovcGgDhZXDIeZGofyrWQ+y6I96+WMgn3Nu2/wV50WdVkAdf50ANGreRpTDnR2leG5/c3JESbF+B8RvJuLZaQBb5vNgkKA==";
//    	//System.out.println(">>> strEncode 2 length = " + strEncode.getBytes().length);
//
//        String strDecode = decode(strEncode);
//        System.out.println(">>> strDecode = " + strDecode);
        
    }
}
