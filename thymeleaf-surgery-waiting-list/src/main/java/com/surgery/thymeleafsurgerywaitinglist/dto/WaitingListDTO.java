package com.surgery.thymeleafsurgerywaitinglist.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class WaitingListDTO {

	private Long waitingListId;
	private String waitingListProcedure;
	private String waitingListDiagnosis;
	private Long waitingListPatientId;
	private Long waitingListSurgeonId;
	private String waitingListSurgeonLastName;
	private Long waitingListDepartmentId;
	private String waitingListDepartmentName;
	@DateTimeFormat(iso=ISO.DATE)
	private Date waitingListAdditionDate;
	@DateTimeFormat(iso=ISO.DATE)
	private Date waitingListActualBookingDate;
}
