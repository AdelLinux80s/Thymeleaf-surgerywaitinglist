package com.surgery.thymeleafsurgerywaitinglist.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
@Entity
@Table(name="waitingList")
public class WaitingList {

	@Id
	@GeneratedValue
	private Long waitingListId;
	private String waitingListProcedure;
	private String waitingListDiagnosis;
	private Long waitingListPatientId;
	private Long waitingListSurgeonId;
	private Long waitingListDepartmentId;
	@DateTimeFormat(iso=ISO.DATE)
	private Date waitingListAdditionDate;
	@DateTimeFormat(iso=ISO.DATE)
	private Date waitingListActualBookingDate;
	
}
