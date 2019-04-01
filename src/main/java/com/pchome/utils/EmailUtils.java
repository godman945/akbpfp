package com.pchome.utils;

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

        	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("spool.pchome.com.tw");
            this.mailSender = mailSender;
        	log.info(">>>>>>>>>>1:");
        	try {
        		log.info(">>>>>>>>>>1:"+this.mailSender);
        		log.info(">>>>>>>>>>1:"+this.mailSender.createMimeMessage());
        	}catch(Exception e) {
        		log.error(e);
        	}
        	
        	
        	
            MimeMessage e = this.mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(e, "utf-8");
            messageHelper.setSubject(subject);
            log.info(">>>>>>>>>>2: fromAlias"+fromAlias);
            if(StringUtils.isBlank(fromAlias)) {
                messageHelper.setFrom(from);
            }else{
//                String aliasName = javax.mail.internet.MimeUtility.encodeText(fromAlias);
                messageHelper.setFrom(new InternetAddress(from,fromAlias,"utf-8"));
            }
            log.info(">>>>>>>>>>3:to"+to);
            log.info(">>>>>>>>>>3:bcc"+bcc);
            messageHelper.setTo(to);
            if(bcc != null) {
                messageHelper.setBcc(bcc);
            }
            log.info(">>>>>>>>>>4: html"+html);
            messageHelper.setText(html, true);
            this.mailSender.send(e);
            log.info(">>>>>>>>>>5:");
        } catch (Exception var8) {
            log.error("SendHtmlEmail error", var8);
        }

    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
}
