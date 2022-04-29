package com.surgery.thymeleafsurgerywaitinglist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.surgery.thymeleafsurgerywaitinglist.entity.WaitingList;

public interface WaitingListRepository extends JpaRepository<WaitingList, Long>{

	@Query("FROM WaitingList  WHERE waitingListPatientId = ?1") 
	List<WaitingList> findByWaitingListPatientId(Long waitingListPatientId);
}
