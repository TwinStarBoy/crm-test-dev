package com.crm.test.util;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.map.HashedMap;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class EmailUtil {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Value("${spring.mail.username}")
	private String sendMailAccount;
	
	public void sendTemplateMail(String acceptorAccount,String emailContent,String emailTitle) throws Exception {

		System.out.println(sendMailAccount);
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(sendMailAccount);
		helper.setTo(acceptorAccount);
		helper.setSubject("提醒邮件");

		Map<String, Object> model = new HashedMap();
		model.put("emailContent", emailContent);
		model.put("emailTitle", emailTitle);
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, "template.vm", "UTF-8", model);
		helper.setText(text, true);

		mailSender.send(mimeMessage);
	}
}
