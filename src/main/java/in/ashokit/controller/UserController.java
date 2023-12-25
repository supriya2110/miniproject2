package in.ashokit.controller;

import java.awt.Taskbar.State;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.service.UserService;

@Controller
public class UserController 
{
	@Autowired
	private UserService userservice;
	
	  @GetMapping("/signup")
	   	public String SignUpPage(Model model)
	   	{
		  System.out.println("signup page is called");
	    	model.addAttribute("user",new SignUpForm());
	   		return "signup";
	   	}
	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form,Model model)
	{
		System.out.println("signup called***************"+form.getEmail());
		System.out.println("signup called***************"+form.getName());
		System.out.println("signup called***************"+form.getPhno());

		
	boolean status=userservice.signUp(form);
	
	System.out.println("status::"+status);
	if(status)
	{
		model.addAttribute("succMsg","Account Created, Check Your Email");
		
	}
	else
	{
		model.addAttribute("errMsg","Email is duplicated");
	}
	
	return "signup";
	}
	
    
    
  
    
    @GetMapping("/unlock")
   	public String unlockPage(@RequestParam String email,Model model)
   	{
    	//It is taking from the query param and assign to object
    	System.out.println("email::"+email);
    	UnlockForm unlockform=new UnlockForm();
    	unlockform.setEmail(email);
    	
    	model.addAttribute("unlock", unlockform);
   		return "unlock";
   	}
    
   
    
    @PostMapping("/unlock")
    public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock,Model model)
    {
    System.out.println("unlock::"+unlock);
    System.out.println("unlock::"+unlock.getConfirmPwd());
    System.out.println("unlock:::"+unlock.getEmail());
    System.out.println("unlock:::"+unlock.getTempPwd());
    
    
	if (unlock.getNewPwd().equals(unlock.getConfirmPwd()))

	{
         boolean status=userservice.unlockAccount(unlock);
         
         if(status)
         {
        	 model.addAttribute("succMsg","Your account unlockeed successfully");
         }
         else
         {
        	 model.addAttribute("errMsg","Given Temporary password incorrect");
         }
	} else

	{
		model.addAttribute("errMsg", "New Pwd and Confirm pwd should be same");
	}
    	return "unlock";
    }
    
    
    @GetMapping("/login")
	public String loginPage(Model model)
	{
    	System.out.println("login is called");
    model.addAttribute("loginForm",new LoginForm());
    	
		return "login";
	}
    
    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm form,Model model)
    {
    	System.out.println("form::"+form);
    	
    	String  status=userservice.login(form);
    	
    	if(status.contains("success"))
    	{
    		
    		return "redirect:/dashboard";
    	}
    	
    	model.addAttribute("errMsg",status);
    	return "login";
    }
    
    @GetMapping("/forgot")
   	public String forgotPwdPage()
   	{
   		return "forgotPwd";
   	}
    @PostMapping("/forgotPwd")
    public String forgotPwd(@RequestParam("email") String email,Model model)
    {
    	System.out.println("email::"+email);
    	
    	boolean status=userservice.forgotPwd(email);
    	if(status)
    	{
    		//send success
    		model.addAttribute("succMsg","Pwd sent to your email");
    		
    	}
    	else
    		
    	{
    		//send error
    		model.addAttribute("errorMsg","Ivalid email");
    	}
    	
    	return "forgotPwd";
    }
    
    
}
