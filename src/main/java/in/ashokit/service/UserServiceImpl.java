package in.ashokit.service;

import javax.persistence.Entity;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.entity.UserDtlsEntity;
import in.ashokit.repo.UserDtlsRepo;
import in.ashokit.utility.EmailUtils;
import in.ashokit.utility.PwdUtils;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;
	@Override
	public String login(LoginForm form) {
		
	UserDtlsEntity entity=userDtlsRepo.findByEmailAndPwd(form.getEmail(),form.getPwd());
	if(entity == null)
	{
		
		return "Invalid Creentials";
	}
	
	if(entity.getAccStatus().equals("LOCKED"))
	{
		
		return "your account locked";
	}
	
	session.setAttribute("userId", entity.getUserId());
	
	return "success";
	}

	@Override
	public boolean signUp(SignUpForm form) {
		
		
	UserDtlsEntity user=userDtlsRepo.findByEmail(form.getEmail());
	
	if(user!=null)
	{
		return false;
	}
	
	
		//copy data from binding obj to entity obj
		
				UserDtlsEntity entity=new UserDtlsEntity();
				
			     BeanUtils.copyProperties(form, entity);
			
				//Generate random passwords and set to object
				
				String tempPwd=PwdUtils.generateRandomPwd();
				//set account status as locked
				entity.setPwd(tempPwd);
				entity.setAccStatus("LOCKED");
				//insert record
				userDtlsRepo.save(entity);
				String to=form.getEmail();
			   
				String subject="Unlock Your Account | Ashok IT";
				StringBuffer body=new StringBuffer("");
				body.append("<h1>Use below temporary password to unlock your account</h1>");
				body.append("Temporary  pwd: "+tempPwd);
				body.append("<br/>");
				body.append("<a href=\"http://localhost:7777/unlock?email="+to+"\">Click Here To Unlock Your Account</a>");
				emailUtils.sendMail(to, subject,body.toString() );
				//send email to unlock the account
		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {
		
	UserDtlsEntity entity=userDtlsRepo.findByEmail(form.getEmail());
	
	if(entity.getPwd().equals(form.getTempPwd()))
	{
		entity.setPwd(form.getNewPwd());
		entity.setAccStatus("Unlocked");
		userDtlsRepo.save(entity);
		return true;
	}
	else
		return false;
	}

	@Override
	public boolean forgotPwd(String email) {
		
		//check record presece in db with given email
		
		//if record not available send error message
		
		UserDtlsEntity entity=userDtlsRepo.findByEmail(email);
		
		if(entity==null)
		{
			
			return false;
		}
		String Subject="Recover Password";
		String body="Your Pwd :: "+entity.getPwd();
		emailUtils.sendMail(email, Subject, body);
		
		return true;
		
	}

}
