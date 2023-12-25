package in.ashokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;
import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.EnqStatusEntity;
import in.ashokit.entity.StudentEnqEntity;
import in.ashokit.entity.UserDtlsEntity;
import in.ashokit.repo.CourseRepo;
import in.ashokit.repo.EnqStatusRepo;
import in.ashokit.repo.StudentEnqRepo;
import in.ashokit.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Autowired
	private StudentEnqRepo studentEnqRepo;
	
	@Autowired
	private HttpSession sesssion;
	 
	

	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		 DashboardResponse response=new DashboardResponse();
		Optional<UserDtlsEntity> findById=userDtlsRepo.findById(userId);
		
		if(findById.isPresent())
		{
			UserDtlsEntity userEntity=findById.get();
			List<StudentEnqEntity> enquiries=userEntity.getEnquiries();
		Integer totalEnquiries=enquiries.size();
		System.out.println("total Enquireies***"+totalEnquiries);
		Integer enrolledCnt=enquiries.stream().filter( e -> e.getEnqStatus().equals("Enrooled")).collect(Collectors.toList()).size();
		System.out.println("enrolledCount***"+enrolledCnt);
	    Integer lostCnt=enquiries.stream().filter( e -> e.getEnqStatus().equals("Lost")).collect(Collectors.toList()).size();
	    System.out.println("lostCnt***"+lostCnt);
	    response.setEnrolledCnt(enrolledCnt);
	    response.setLostCnt(lostCnt);
	    response.setTotalEnquriesCnt(totalEnquiries);
	    
		}
		return response;
	}


	@Override
	public List<EnquiryForm> getEnquries(Integer userId, EnquirySearchCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnquiryForm getEnquiry(Integer enqId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getCourses()
	{
		List<CourseEntity>  findAll=courseRepo.findAll();
		List<String> names=new ArrayList<>();
		for(CourseEntity entity:findAll)
		{
			names.add(entity.getCourseName());
		}
		return names;
		
	}


	@Override
	public List<String> getEnqStatus() {
		List<EnqStatusEntity>  findAll=  statusRepo.findAll();
		List<String> status=new ArrayList<>();
		for(EnqStatusEntity entity:findAll)
		{
			status.add(entity.getStatusName());
		}

		return status;
	}


	@Override
	public boolean saveEnquiry(EnquiryForm form) {
		
	Integer userId=(Integer)sesssion.getAttribute("userId");
	
		StudentEnqEntity enqEntity=new StudentEnqEntity();
		
		BeanUtils.copyProperties(form, enqEntity);
		
		UserDtlsEntity userEntity=userDtlsRepo.findById(userId).get();
		
		enqEntity.setUser(userEntity);
		
		studentEnqRepo.save(enqEntity);
		
		
		return true;
	}

}
