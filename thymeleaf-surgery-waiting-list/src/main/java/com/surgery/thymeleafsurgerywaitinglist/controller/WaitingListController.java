package com.surgery.thymeleafsurgerywaitinglist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.surgery.thymeleafsurgerywaitinglist.repository.WaitingListRepository;

@Controller
public class WaitingListController {

	@Autowired
	private WaitingListRepository waitingListRepo;
	
	@GetMapping("/surgerywaitinglist")
	public ModelAndView getAll() {
		ModelAndView mav = new ModelAndView("list");
		mav.addObject("waitinglist", waitingListRepo.findAll());
		return mav;
	}
}
