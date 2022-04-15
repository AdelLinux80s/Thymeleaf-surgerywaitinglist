package com.surgery.thymeleafsurgerywaitinglist.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="surgeon")
public class Surgeon {

	@Id
	@GeneratedValue
	private Long surgeonId;
	private Long surgeonNationalID;
	private String surgeonFirstName;
	private String surgeonLastName;
	private String surgeonNationality;
	private Date surgeonDateOfBirth;
	private String surgeonGender;
	private String surgeonAddress;
	private String surgeonConact;
	private String surgeonEmailAddress;
	private String surgeonSpeciality;
	
	
	
	private Long departmentIdInSurgery;
}
