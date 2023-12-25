package in.ashokit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.service.EnquiryService;

@Controller
public class EnquiryController
{
    @Autowired
	private HttpSession session;
    
   @Autowired
    private EnquiryService enqService;
	
	@GetMapping("/logout")
	public String logout()
	{
		session.invalidate();
		return "index";
	}
	@GetMapping("/dashboard")
	public String dashboardPage(Model model)
	{
		Integer userId=(Integer)session.getAttribute("userId");
		DashboardResponse dashboardData=enqService.getDashboardData(userId);
		model.addAttribute("dashboardData",dashboardData);
		return "dashboard";
	}
	
//	@GetMapping("/enquiry")
//	public String enquiryPage()
//	{
//		return "add-enquiry";
//	}
	@GetMapping("/enquires")
	public String viewEnquiriesPage()
	{
		return "view-enquiries";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model)
	{
		
	List<String> courses=enqService.getCourses();
	List<String> status=enqService.getEnqStatus();
	
	EnquiryForm formObj=new EnquiryForm();
	
	for(String courses1:courses)
	{
		System.out.println("courses1********"+courses1);
	}
	
	for(String status1:status)
	{
		System.out.println("status*******"+status1);
	}
	
	model.addAttribute("courseNames",courses);
	model.addAttribute("enqStatusNames",status);
	model.addAttribute("formObj",formObj);	
		return "add-enquiry";
	}
	
	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm form,Model model)
	{
		System.out.println("enquiry form object***"+form);
		
		boolean status=enqService.saveEnquiry(form);
		
		if(status)
		{
		model.addAttribute("succMsg","Enquiry added");
		}
		else
		{
			model.addAttribute("errMsg","Enquiry not added");
		}
		
		
		return "add-enquiry";
	}
}
