package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.UserDtlsEntity;

public interface CourseRepo extends JpaRepository<CourseEntity,Integer>{

}
