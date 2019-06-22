package com.pkaushik.safeHome.controller.legacy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pkaushik.safeHome.service.UserAuthServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pkaushik.safeHome.SafeHomeApplication;
import com.pkaushik.safeHome.model.DateTime;
import com.pkaushik.safeHome.model.Location;
import com.pkaushik.safeHome.model.SafeHome;
import com.pkaushik.safeHome.model.Schedule;
import com.pkaushik.safeHome.model.SafeHomeUser;
import com.pkaushik.safeHome.model.Walker;
import com.pkaushik.safeHome.model.enumerations.WalkerStatus;

@RestController
public class QueryController {

	@Autowired
	private UserAuthServiceIF userAuthService;

	//Query Methods
	@GetMapping(value = "/walkerslist")
	public static List<DTOWalker> getAllWalkers() {
		//only students should be able to view the walkers

		//TODO:: 
		//if(SafeHomeApplication.getCurrentUserRole() instanceof Walker) throw new IllegalAccessError("Only students can view the walkers");
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
						.filter((x) -> x.getStatus().equals(WalkerStatus.LOGGED_IN))
						.collect(Collectors.toList());
	}

	//Query
	//Get all registered users
	@GetMapping(value = "/getAllUsers")
	public static List<SafeHomeUser> getAllUsers(){
		
		//from persistence. we're not saving all registered users every instance. 
		return null; 
	}

	//Query
	/**
	 * This method will ping the walker with any requests made to them. 
	 * should return the location and time. 
	 */
	public static Map<Location, DateTime> postRequestMade(){

		return null; 
	}


	//temp method for testing to create walkers with schedules
	@GetMapping(value = "/createandlogin")
	public void testMethodToDelete() {
		SafeHomeApplication.resetAll(); 
		Schedule scheduleForAll = new Schedule(12,12,2018,12,1,2019, 18,00,12,00);
		userAuthService.registerService(new BigInteger("4389247381"), 260790400, true);
		userAuthService.registerService(new BigInteger("4389247381"), 260790401, true);
		userAuthService.registerService(new BigInteger("4389247381"), 260790402, true);
		userAuthService.registerService(new BigInteger("4389247381"), 260790403, true);
		
		
		List<Walker> walkers = SafeHomeApplication.getSafeHome().getWalkers(); 
		int counter = 0; 
		for(Walker walker: walkers) {
			walker.setSchedule(scheduleForAll);
			walker.setRating(counter);
			counter++; 
		}
		for(int i = 0; i<2; i++){
		userAuthService.loginService(260790400+i, true);
		}
	}
}
