package com.pkaushik.safeHome.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
		
		List<Walker> walkers = safeHome.getWalkers(); 
	
		List<DTOWalker> resultWalkers = new ArrayList<DTOWalker>(); 
		
		for(Walker walker : walkers){
			DTOWalker dtoWalker;
			if(walker.hasSchedule()){
				dtoWalker = new DTOWalker(walker.getRating(), walker.isWalksafe(),
				walker.getSchedule().getStartDate().getDateTime(), 
				walker.getSchedule().getEndDate().getDateTime(), walker.hasSchedule(), walker.getStatus());
			}
			else{
				dtoWalker = new DTOWalker(walker.getRating(), walker.isWalksafe(), walker.hasSchedule()); 
			}
			resultWalkers.add(dtoWalker); 
		}
		return resultWalkers; 
	}
	
	//Query
	//Get logged in walkers
	@GetMapping(value = "/availableWalkers")
	public static List<DTOWalker> getAvailableWalkers(){
		
		return 	getAllWalkers().stream()
						.filter((x) -> x.getStatus().equals(walkerStatus.LOGGED_IN))
						.collect(Collectors.toList());
	}

	//Query
	//TODO: get all registered and all loggedinusers


	//temp method for testing to create walkers with schedules
	@GetMapping(value = "/createandlogin")
	public static void testMethodToDelete() {
		SafeHomeApplication.resetAll(); 
		Schedule scheduleForAll = new Schedule(12,12,2018,12,1,2019, 18,00,12,00);
		UserController.register(new BigInteger("4389247381"), 260790400, true); 
		UserController.register(new BigInteger("4389247381"), 260790401, true); 
		UserController.register(new BigInteger("4389247381"), 260790402, true); 
		UserController.register(new BigInteger("4389247381"), 260790403, true); 
		
		
		List<Walker> walkers = SafeHomeApplication.getSafeHome().getWalkers(); 
		int counter = 0; 
		for(Walker walker: walkers) {
			walker.setSchedule(scheduleForAll);
			walker.setRating(counter);
			counter++; 
		}
		for(int i = 0; i<2; i++){
		UserController.login(260790400+i, true);
		}
	}
}
