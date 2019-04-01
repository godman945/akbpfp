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

    public void sendHtmlEmail(String subject, String from , String fromAlias, String[] to, String[] bcc, String html) throws MessagingException {
        try {
        	
        	log.info(">>>>>>>>>寄信開始");
        	
        	 JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
             Properties props = new Properties();
             props.put("mail.smtp.auth", "true");//Outgoing server requires authentication
             props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
             mailSender.setJavaMailProperties(props);
             mailSender.setHost("spool.pchome.com.tw"); 
             
             SimpleMailMessage smm = new SimpleMailMessage();
             smm.setTo("godman945@yahoo.com.tw");
             smm.setFrom("showadm@msx.pchome.com.tw");
             smm.setSubject("Default subject");
             smm.setText("Default text");
             
             SimpleMailMessage msg = new SimpleMailMessage(smm);
             msg.setTo("godman945@yahoo.com.tw");
             msg.setSubject("test subject");
             msg.setText("spring email integration test");
             mailSender.send(msg);
             
        	log.info(">>>>>>>>>寄信完成");
        } catch (Exception e) {
        	e.printStackTrace();
      }
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
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

    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
}
