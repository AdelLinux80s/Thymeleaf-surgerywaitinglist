package com.surgery.thymeleafsurgerywaitinglist.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.surgery.thymeleafsurgerywaitinglist.entity.Surgeon;

public interface SurgeonRepository extends JpaRepository<Surgeon, Long>{
	
	@Query("FROM Surgeon  WHERE surgeonSpeciality = ?1")
    Set<Surgeon> findBySurgeonspeciality(String surgeonSpeciality);

}
