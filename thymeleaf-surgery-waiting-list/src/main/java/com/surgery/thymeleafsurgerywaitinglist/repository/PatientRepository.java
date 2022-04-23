package com.surgery.thymeleafsurgerywaitinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surgery.thymeleafsurgerywaitinglist.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

}
