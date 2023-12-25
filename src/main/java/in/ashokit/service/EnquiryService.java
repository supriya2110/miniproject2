package in.ashokit.service;

import java.util.List;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;

public interface EnquiryService {
	
	public List<String> getCourses();
	
	public List<String> getEnqStatus();
	
	public DashboardResponse getDashboardData(Integer userId);
	
	public boolean saveEnquiry(EnquiryForm form);
	
	public List<EnquiryForm> getEnquries(Integer userId,EnquirySearchCriteria criteria);
	
	public EnquiryForm getEnquiry(Integer enqId);
	
	
	

}
