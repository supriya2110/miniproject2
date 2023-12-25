package in.ashokit.utility;

import javax.mail.internet.MimeMessage;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendMail(String to,String subject,String body)
	{
		
		boolean isSent=false;
		
		try
		{
			System.out.println("to::"+to);
			System.out.println("subject::"+subject);
			System.out.println("body::"+body);
			MimeMessage mimeMessage=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
			
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body.toString(),true);
			mailSender.send(mimeMessage);
			isSent=true;
		}
		catch (Exception e) {
	e.printStackTrace();
		}
		return isSent;
	}

}
