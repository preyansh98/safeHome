package com.pkaushik.safeHome.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.Schedule;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.model.Walker.walkerStatus;

@RestController
public class QueryController {

	//Query Methods
	@GetMapping(value = "/walkerslist")
	public static List<DTOWalker> getAllWalkers() {
		//only students should be able to view the walkers
		if(SafeHomeApplication.getCurrentUserRole() instanceof Walker) throw new IllegalAccessError("Only students can view the walkers");
		SafeHome safeHome = SafeHomeApplication.getSafeHome(); 
		testMethodToDelete();
		List<Walker> walkers = safeHome.getWalkers(); 
	
		List<DTOWalker> resultWalkers = new ArrayList<DTOWalker>(); 
		
		for(Walker walker : walkers){
			DTOWalker dtoWalker;
			if(walker.hasSchedule()){
				dtoWalker = new DTOWalker(walker.getRating(), walker.isWalksafe(),
				walker.getSchedule().getStartDate().getDateTime(), 
				walker.getSchedule().getEndDate().getDateTime(), walker.hasSchedule()); 
			}
			else{
				dtoWalker = new DTOWalker(walker.getRating(), walker.isWalksafe(), walker.hasSchedule()); 
			}
			resultWalkers.add(dtoWalker); 
		}
		return resultWalkers; 
	}
	
	//Query
	//TODO: should probably be show all logged in walkers. Can't show logged in student as logged in walker. 
	@GetMapping(value = "/availableWalkers")
	public static List<DTOWalker> getAvailableWalkers(){
		if(SafeHomeApplication.getCurrentUserRole() instanceof Walker) throw new IllegalAccessError("Only students can view available walkers"); 
		SafeHome safeHome = SafeHomeApplication.getSafeHome();
		
		List<Walker> walkers = safeHome.getWalkers(); 
		List<DTOWalker> resultWalkers = new ArrayList<DTOWalker>(); 
		
		for(Walker walker : walkers){
			DTOWalker dtoWalker = null;
			//only want to get walkers that are active. 
			if(walker.getStatus().equals(walkerStatus.LOGGED_IN)) {
			if(walker.hasSchedule()){
				dtoWalker = new DTOWalker(walker.getRating(), walker.isWalksafe(),
				walker.getSchedule().getStartDate().getDateTime(), 
				walker.getSchedule().getEndDate().getDateTime(), walker.hasSchedule()); 
			}
			else{
				dtoWalker = new DTOWalker(walker.getRating(), walker.isWalksafe(), walker.hasSchedule()); 
			}
			resultWalkers.add(dtoWalker); 
			}
		}
		return resultWalkers; 
	}
	
	//temp method for testing to create walkers with schedules
	public static void testMethodToDelete() {
		Schedule scheduleForAll = new Schedule(12,12,2018,12,1,2019, 18,00,12,00);
		SafeHomeController.register(new BigInteger("4389247381"), 260790400, true); 
		SafeHomeController.register(new BigInteger("4389247381"), 260790400, true); 
		SafeHomeController.register(new BigInteger("4389247381"), 260790400, true); 
		SafeHomeController.register(new BigInteger("4389247381"), 260790400, true); 
		
		
		List<Walker> walkers = SafeHomeApplication.getSafeHome().getWalkers(); 
		for(Walker walker: walkers) {
			walker.setSchedule(scheduleForAll);
		}
		SafeHomeController.login(260790400, true);
		for(int i = 0; i<2; i++) {
			walkers.get(i).setStatus(walkerStatus.LOGGED_IN);
		}
	}
}
