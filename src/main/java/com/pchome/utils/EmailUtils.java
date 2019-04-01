package com.pchome.utils;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;


public class EmailUtils {
	private static final Logger log = LogManager.getRootLogger();
    private static EmailUtils instance = new EmailUtils();
    private JavaMailSender mailSender;
    private SimpleMailMessage templateMessage;

    private EmailUtils() {
    }

    public static synchronized EmailUtils getInstance() {
        return instance;
    }

    public void setHost(String host) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        this.mailSender = mailSender;
    }

    public void sendEmail(String Message) {
        this.templateMessage.setText(Message);
        this.mailSender.send(this.templateMessage);
    }

    public void sendHtmlEmail(String subject, String from , String fromAlias, String[] to, String[] bcc, String html)  {
//        try {
    	 try {
        	log.info(">>>>>>>>>寄信開始");
        	 JavaMailSenderImpl sender = new JavaMailSenderImpl();

             sender.setHost("spool.pchome.com.tw");
             sender.setPort(465);
//             sender.setUsername("*********@qq.com");
//             sender.setPassword("xqbhcaptnzurzbhef"); // 这里要用邀请码，不是你登录邮箱的密码

             Properties pro = System.getProperties(); // 下面各项缺一不可
             pro.put("mail.smtp.auth", "true");
             pro.put("mail.smtp.ssl.enable", "true");
             pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

             sender.setJavaMailProperties(pro);

             MimeMessage message = sender.createMimeMessage();
            
                 MimeMessageHelper helper = new MimeMessageHelper(message, true);
                 helper.setFrom("showadm@msx.pchome.com.tw"); // 发送人 
                 helper.setTo("godman945@yahoo.com.tw"); // 收件人  
                 helper.setSubject("Title"); // 标题
                 helper.setText("Content"); // 内容
                 sender.send(message);
                 System.out.println("发送完毕！");
             } catch (MessagingException e) {
            	 log.info(e.getMessage());
                 e.printStackTrace();
             } catch (Exception e) {
            	 log.info(e.getMessage());
            	 e.printStackTrace();
             }
         }
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
//        	
//        	 JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//             Properties props = new Properties();
//             mailSender.setJavaMailProperties(props);
//             mailSender.setHost("spool.pchome.com.tw"); 
//             
//             SimpleMailMessage smm = new SimpleMailMessage();
//             smm.setTo("godman945@yahoo.com.tw");
//             smm.setFrom("showadm@msx.pchome.com.tw");
//             smm.setSubject("Default subject");
//             smm.setText("Default text");
//             
//             SimpleMailMessage msg = new SimpleMailMessage(smm);
//             msg.setTo("godman945@yahoo.com.tw");
//             msg.setSubject("test subject");
//             msg.setText("spring email integration test");
//             mailSender.send(msg);
//             
//        	log.info(">>>>>>>>>寄信完成");
//        } catch (MessagingException e) {
//        	e.printStackTrace();
//        } 
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
//        	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//            mailSender.setHost("spool.pchome.com.tw");
//        	log.info(">>>>>>>>>>1:");
//        	try {
//        		log.info(">>>>>>>>>>1:"+mailSender.createMimeMessage());
//        	}catch(Exception e) {
//        		log.error(e);
//        	}
//        	
//        	
//        	
//            MimeMessage e = this.mailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(e, "utf-8");
//            messageHelper.setSubject(subject);
//            log.info(">>>>>>>>>>2: fromAlias"+fromAlias);
//            if(StringUtils.isBlank(fromAlias)) {
//                messageHelper.setFrom(from);
//            }else{
////                String aliasName = javax.mail.internet.MimeUtility.encodeText(fromAlias);
//                messageHelper.setFrom(new InternetAddress(from,fromAlias,"utf-8"));
//            }
//            log.info(">>>>>>>>>>3:to"+to);
//            log.info(">>>>>>>>>>3:bcc"+bcc);
//            messageHelper.setTo(to);
//            if(bcc != null) {
//                messageHelper.setBcc(bcc);
//            }
//            log.info(">>>>>>>>>>4: html"+html);
//            messageHelper.setText(html, true);
//            this.mailSender.send(e);
//            log.info(">>>>>>>>>>5:");
//        } catch (Exception var8) {
////            log.error("SendHtmlEmail error", var8);
//        }

//    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
}
