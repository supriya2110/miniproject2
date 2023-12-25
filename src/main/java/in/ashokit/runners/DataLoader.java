package in.ashokit.runners;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.EnqStatusEntity;
import in.ashokit.repo.CourseRepo;
import in.ashokit.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo enqStatusRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		courseRepo.deleteAll();
		CourseEntity c1=new CourseEntity();
		
		c1.setCourseName("JAVA");
		
CourseEntity c2=new CourseEntity();
		
		c2.setCourseName("PYTHON");
		
CourseEntity c3=new CourseEntity();
		
		c3.setCourseName("NET");
		courseRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		enqStatusRepo.deleteAll();
		
		EnqStatusEntity e1=new EnqStatusEntity();
		e1.setStatusName("New");
		

		EnqStatusEntity e2=new EnqStatusEntity();
		e2.setStatusName("Enrooled");
		

		EnqStatusEntity e3=new EnqStatusEntity();
		e3.setStatusName("Lost");
		
		enqStatusRepo.saveAll(Arrays.asList(e1,e2,e3));
		
	}

	
}
