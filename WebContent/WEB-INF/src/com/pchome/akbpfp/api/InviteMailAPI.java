package com.pchome.akbpfp.api;

import java.io.File;
import java.io.IOException;

import javax.mail.internet.InternetAddress;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pchome.soft.util.SpringEmailUtil;

public class InviteMailAPI {

	protected final static String CHARSET = "UTF-8";
	protected final static String Subject = "PChome 廣告刊登 - 帳戶邀請信件";

    protected Log log = LogFactory.getLog(getClass().getName());
    
    private String mailService;
    private String mailFrom;    
    private String mailDir;
    
    public void sendInviteMail(String[] emails, String mailContent){
    	
    	SpringEmailUtil.getInstance().setHost(mailService);
    	
    	try {
    		SpringEmailUtil.getInstance().sendHtmlEmail(Subject, new InternetAddress(mailFrom,"PChome廣告刊登-關鍵字廣告服務").getAddress(), emails, null, mailContent);
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
