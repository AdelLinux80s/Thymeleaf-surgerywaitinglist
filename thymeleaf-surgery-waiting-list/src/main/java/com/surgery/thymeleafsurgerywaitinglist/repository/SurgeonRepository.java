package com.surgery.thymeleafsurgerywaitinglist.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.surgery.thymeleafsurgerywaitinglist.entity.Surgeon;

public interface SurgeonRepository extends JpaRepository<Surgeon, Long>{
	
	@Query("FROM Surgeon  WHERE surgeonSpeciality = ?1")
    Set<Surgeon> findBySurgeonspeciality(String surgeonSpeciality);

	
	@Query("FROM Surgeon  WHERE surgeonLastName = ?1") 
	Surgeon findBySurgeonLastName(String surgeonLastName);
	
	@Query("FROM Surgeon  WHERE departmentIdInSurgery = ?1")
    List<Surgeon> findByDepartmentIdInSurgery(Long departmentIdInSurgery);
}
