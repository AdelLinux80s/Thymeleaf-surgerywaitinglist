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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.surgery.thymeleafsurgerywaitinglist.dto.WaitingListDTO;
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

	@GetMapping(value="/surgerywaitinglist/patientSurgerySearch")
	public ModelAndView patientSurgerySearch() {
		ModelAndView mav = new ModelAndView("patientSurgerySearch");
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
	
	@GetMapping(value="/surgerywaitinglist/update")
	public ModelAndView update(@RequestParam Long waitingListId) {
		ModelAndView mav = new ModelAndView("update");
		WaitingList waitinglist = waitingListRepo.findById(waitingListId).get();
		System.out.println("update waitinglist: " + waitinglist);
		Patient patient = paitentRepo.findById(waitinglist.getWaitingListPatientId()).get();
		HashMap<String, Set<String>> SurgeonsByDepartments = getSurgeonsByDepartments();
		Surgeon surgeon = surgeonRepo.findById(waitinglist.getWaitingListSurgeonId()).get();
		Department department = depRepo.findById(waitinglist.getWaitingListDepartmentId()).get();
		List<Surgeon> surgeons = surgeonRepo.findByDepartmentIdInSurgery(waitinglist.getWaitingListDepartmentId()); 
		List<Department> departments = depRepo.findAll();
		mav.addObject("waitinglist", waitinglist);
		System.out.println("watinglist: " + waitinglist);
		mav.addObject("patient", patient);		
		mav.addObject("departments", departments);
		mav.addObject("SurgeonsByDepartments", SurgeonsByDepartments);
		mav.addObject("surgeon", surgeon);
		mav.addObject("surgeons", surgeons);
		mav.addObject("department", department);
		
		return mav;
	}
	
	@GetMapping(value="surgerywaitinglist/remove")
	public String removeFromWaitingList(@RequestParam Long waitingListId) {
		waitingListRepo.deleteById(waitingListId);
		return "redirect:/surgerywaitinglist";
	}
	
	@GetMapping(value="/surgerywaitinglist/patientSurgeries")
	public ModelAndView patientSurgeries(Patient tempPatient) {
		ModelAndView mav = new ModelAndView("patientSurgeries");
		List<WaitingList> WaitingList = waitingListRepo.findByWaitingListPatientId(tempPatient.getPatientId());
		System.out.println("In GetMapping(surgerywaitinglist/patientSurgeries" + WaitingList);
		List<WaitingListDTO> waitinglistDTO = convertWaitingToWaitingListDTO(WaitingList);
		Patient patient = paitentRepo.findById(tempPatient.getPatientId()).get();
		mav.addObject("patient", patient);
		mav.addObject("waitinglistDTO", waitinglistDTO);
		return mav;
	}
	
	@PostMapping(value="/surgerywaitinglist/saveToWaitinList")
	public String saveToWaitinList(@ModelAttribute WaitingList waitinglist, @ModelAttribute Surgeon surgeon, @ModelAttribute Department department ,Long patientId) {
		if(waitinglist.getWaitingListId()==null) {
			System.out.println("saveToWaitinglist waitinglist: " + waitinglist);
			Department tempDepartment = depRepo.findByDepartmentName(department.getDepartmentName());
			Surgeon temSurgeon = surgeonRepo.findBySurgeonLastName(surgeon.getSurgeonLastName());
			System.out.println("saveToWaitinglist tempSurgeon: " + temSurgeon);
			System.out.println("saveToWaitinglist tempDepartment: " + tempDepartment);
			
			waitinglist.setWaitingListSurgeonId(temSurgeon.getSurgeonId());
			waitinglist.setWaitingListDepartmentId(tempDepartment.getDepartmentId());
			waitinglist.setWaitingListPatientId(patientId);
			
			waitingListRepo.save(waitinglist);
		}
		
		else {
			System.out.println("saveToWaitinglist waitinglist else: " + waitinglist);
			Department tempDepartment = depRepo.findByDepartmentName(department.getDepartmentName());
			Surgeon temSurgeon = surgeonRepo.findBySurgeonLastName(surgeon.getSurgeonLastName());
			waitinglist.setWaitingListActualBookingDate(waitinglist.getWaitingListActualBookingDate());
			waitinglist.setWaitingListAdditionDate(waitinglist.getWaitingListAdditionDate());
			waitinglist.setWaitingListProcedure(waitinglist.getWaitingListProcedure());
			waitinglist.setWaitingListDiagnosis(waitinglist.getWaitingListDiagnosis());
			waitinglist.setWaitingListSurgeonId(temSurgeon.getSurgeonId());
			waitinglist.setWaitingListDepartmentId(tempDepartment.getDepartmentId());
			waitinglist.setWaitingListPatientId(patientId);
			waitingListRepo.save(waitinglist);
		}
		return "redirect:/surgerywaitinglist";
		
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
	
	private List<WaitingListDTO> convertWaitingToWaitingListDTO(List<WaitingList> waitingList){
		List<WaitingListDTO> listDTO = new ArrayList<>();
		
		Department department = new Department();
		Surgeon surgeon = new Surgeon();
		//for(int i = 0 ; i<waitingList.size(); i++) {
		for(WaitingList waitinlistTemp: waitingList) {
			WaitingListDTO waitingListDTO = new WaitingListDTO();
			//waitingListDTO.setWaitingListId(waitingList.get(i).getWaitingListId());
			
			waitingListDTO.setWaitingListId(waitinlistTemp.getWaitingListId());
			//System.out.println("waitingListDTO waitinglistId: " + waitingListDTO.getWaitingListId());
		
			
			
			
			  waitingListDTO.setWaitingListProcedure(waitinlistTemp.getWaitingListProcedure());
			  waitingListDTO.setWaitingListDiagnosis(waitinlistTemp.getWaitingListDiagnosis());
			  waitingListDTO.setWaitingListPatientId(waitinlistTemp.getWaitingListPatientId());
			  waitingListDTO.setWaitingListSurgeonId(waitinlistTemp. getWaitingListSurgeonId());
			  waitingListDTO.setWaitingListDepartmentId(waitinlistTemp.getWaitingListDepartmentId());
			  
			  department = depRepo.findById(waitingListDTO.getWaitingListDepartmentId()).get();
			  //System.out.print("department in convert:  " + department);
			  waitingListDTO.setWaitingListDepartmentName(department.getDepartmentName());
			  
			  surgeon = surgeonRepo.findById(waitingListDTO.getWaitingListSurgeonId()).get();
			  waitingListDTO.setWaitingListSurgeonLastName(surgeon.getSurgeonLastName());
			  waitingListDTO.setWaitingListAdditionDate(waitinlistTemp.getWaitingListAdditionDate());
			  waitingListDTO.setWaitingListActualBookingDate(waitinlistTemp.getWaitingListActualBookingDate());
			 

			listDTO.add(waitingListDTO);
			//System.out.println("in convertWaitingToWaitingListDTO listDTO: "+ listDTO);
			


		}
		return listDTO;
	}
}
