package com.pchome.akbpfp.api;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.pchome.utils.EmailUtils;



public class InviteMailAPI {

	protected final static String CHARSET = "UTF-8";
	protected final static String Subject = "PChome 廣告刊登 - 帳戶邀請信件";
	protected final static String MailUserName = "PChome廣告刊登-關鍵字廣告服務";
	

    protected Logger log = LogManager.getRootLogger();
    
    private String mailService;
    private String mailFrom;    
    private String mailDir;
    
    public void sendInviteMail(String[] emails, String mailContent){
    	
    	EmailUtils.getInstance().setHost(mailService);
    	log.info(">>>>>>>>>>>>>>>>"+mailService);
    	try {
    		EmailUtils.getInstance().sendHtmlEmail(Subject, mailFrom, MailUserName, emails, null, mailContent);
    	} catch (Exception e) {
            log.error(" send mail error : "+e);
        }
    }
    
    public String inviteMailContent(){
    	
    	String mailContent = "";
    	File mailFile = new File(mailDir + "invite.html");
    	log.info(" mail file = "+mailFile.getPath());
    	if (mailFile.exists()) {
            try {
                mailContent = FileUtils.readFileToString(mailFile, CHARSET);
            } catch (IOException e) {
                log.error(mailFile.getPath(), e);
            }
        }
    	
    	return mailContent;
    }
    
    
    
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	
	public void setMailService(String mailService) {
		this.mailService = mailService;
	}
	
	public void setMailDir(String mailDir) {
		this.mailDir = mailDir;
	}
    
    
    
    
}
