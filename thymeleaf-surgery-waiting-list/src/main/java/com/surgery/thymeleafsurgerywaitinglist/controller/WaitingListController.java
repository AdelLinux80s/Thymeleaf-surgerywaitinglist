package com.surgery.thymeleafsurgerywaitinglist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.surgery.thymeleafsurgerywaitinglist.entity.Department;
import com.surgery.thymeleafsurgerywaitinglist.entity.Patient;
import com.surgery.thymeleafsurgerywaitinglist.entity.Surgeon;
import com.surgery.thymeleafsurgerywaitinglist.entity.WaitingList;
import com.surgery.thymeleafsurgerywaitinglist.repository.DepartmentRepository;
import com.surgery.thymeleafsurgerywaitinglist.repository.PatientRepository;
import com.surgery.thymeleafsurgerywaitinglist.repository.SurgeonRepository;
import com.surgery.thymeleafsurgerywaitinglist.repository.WaitingListRepository;

@Controller
public class WaitingListController {

	@Autowired
	private WaitingListRepository waitingListRepo;
	
	@Autowired
	private PatientRepository paitentRepo;
	
	@Autowired
	private DepartmentRepository depRepo;
	
	@Autowired
	private SurgeonRepository surgeonRepo;

	@GetMapping("/surgerywaitinglist")
	public ModelAndView getAll() {
		ModelAndView mav = new ModelAndView("list");
		mav.addObject("waitinglist", waitingListRepo.findAll());
		return mav;
	}

	@GetMapping("/surgerywaitinglist/patientSearch")
	public ModelAndView patientSearch() {
		ModelAndView mav = new ModelAndView("patientSearch");
		Patient patient = new Patient();
		mav.addObject("patient", patient);
		return mav;
	}

	@GetMapping(value = "/surgerywaitinglist/addProcedure")
	public ModelAndView addProcedure(Patient tempPatient) {

		ModelAndView mav = new ModelAndView("addProcedure");

		Patient patient = paitentRepo.findById(tempPatient.getPatientId()).get();
		Surgeon surgeon = new Surgeon();
		Department department = new Department();
		WaitingList waitinglist = new WaitingList();

		List<Department> departments = depRepo.findAll();

		mav.addObject("patient", patient);
		mav.addObject("departments", departments);
		mav.addObject("waitinglist", waitinglist);
		mav.addObject("surgeon", surgeon);
		mav.addObject("department", department);

		return mav;
	}
	
	@GetMapping(value="/surgerywaitinglist/getSurgeonsByDep")
	@ResponseBody
	public Set<String> getSurgeonSetByDep(@RequestParam String department) {
		
		HashMap<String, Set<String>> SurgeonsByDepartments = getSurgeonsByDepartments();
	
		//System.out.println("in surgerywaitinglist/getSurgensByDep " + SurgeonsByDepartments.get(department));
		return SurgeonsByDepartments.get(department);
		
	}
	
	public HashMap<String, Set<String>> getSurgeonsByDepartments(){
		List<Department> departments = new ArrayList<>();
		departments = depRepo.findAll();
		
		//List<Surgeon> surgeons = new ArrayList<>();
		Set<Surgeon> surgeons;
		HashMap<String, Set<String>> SurgeonsByDepartments = new HashMap<String, Set<String>>();
		
		for(int i =0 ; i<departments.size(); i++) {
			
			surgeons = surgeonRepo.findBySurgeonspeciality(departments.get(i).getDepartmentName());
			Iterator<Surgeon> iteratorItem = surgeons.iterator();
			
		
			
			
			
			Set<String> surgeonLastName = new HashSet<String>();
			  while(iteratorItem.hasNext()){
				// System.out.println("Iteration: " + iteratorItem.next());
				  surgeonLastName.add( iteratorItem.next().getSurgeonLastName());
			  
			  
			  }
			
			
			
			SurgeonsByDepartments.put(departments.get(i).getDepartmentName(), surgeonLastName);
		}
		
		//System.out.println(SurgeonsByDepartments);
	
		return SurgeonsByDepartments;
		
		
	}
}
