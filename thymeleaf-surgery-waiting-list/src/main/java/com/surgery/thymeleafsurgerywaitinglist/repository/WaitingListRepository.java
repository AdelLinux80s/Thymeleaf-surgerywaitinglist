package com.surgery.thymeleafsurgerywaitinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surgery.thymeleafsurgerywaitinglist.entity.WaitingList;

public interface WaitingListRepository extends JpaRepository<WaitingList, Long>{

}
