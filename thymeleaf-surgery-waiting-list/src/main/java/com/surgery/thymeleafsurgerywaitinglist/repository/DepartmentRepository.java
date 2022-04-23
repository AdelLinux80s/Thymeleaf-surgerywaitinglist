package com.surgery.thymeleafsurgerywaitinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surgery.thymeleafsurgerywaitinglist.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
