package com.surgery.thymeleafsurgerywaitinglist.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="department")
public class Department {
	
	@Id
	@GeneratedValue
	private Long departmentId;
	private String departmentName;
}
