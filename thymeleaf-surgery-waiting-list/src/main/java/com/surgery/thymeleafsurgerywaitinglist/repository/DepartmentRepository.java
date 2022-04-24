package com.surgery.thymeleafsurgerywaitinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.surgery.thymeleafsurgerywaitinglist.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

	@Query("FROM Department  WHERE departmentName = ?1") 
	Department findByDepartmentName(String departmentName);
}
