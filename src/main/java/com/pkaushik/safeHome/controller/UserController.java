package com.pkaushik.safeHome.controller;

import java.math.BigInteger;

import com.pkaushik.safeHome.service.impl.UserAuthService;
import com.pkaushik.safeHome.validation.impl.InputValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_DIGITS_FOR_ID;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
    private UserAuthService userAuthService;
    
    @Autowired
    private InputValidator inputValidator;
	
    @PostMapping("/register/{phoneNo}/{mcgillID}/{regAsWlkr}")
	public String register(@PathVariable("phoneNo") BigInteger phoneNo, @PathVariable("mcgillID")int mcgillID, @PathVariable("registerAsWalker") boolean registerAsWalker){

        //input validation for mcgill ID and phoneNo
        try{
            inputValidator.validateMcgillID(mcgillID);
            inputValidator.validatePhoneNo(phoneNo);
        }
        catch(IllegalArgumentException e){
            //return error response.  
        }

		if(new Integer(mcgillID).toString().trim().length() != MAX_DIGITS_FOR_ID){
			//return error resp
			return "errorrrr";
		}

		//delegate state valid to service
		try {
			userAuthService.registerService(phoneNo, mcgillID, registerAsWalker);
		}
		catch(Exception e){
			//catch exceptions from service layer
			//return these resps formatted well.
			return e.getMessage();
		}

		//generate resp
		return "works"; 
	}

	public String login(int mcgillID, boolean loginAsWalker){

		//check input validation
		try{
			inputValidator.validateMcgillID(mcgillID);
		}
		catch(Exception e){
			//return error in validating
		}

		//call service to log in
		try{
			userAuthService.loginService(mcgillID, loginAsWalker);
		}
		catch(Exception e){
			//return error resp
			return "nope";
			//generate resp
		}
		//final resp to send.
		return "loginworks"; 
	}

	public String logout(int mcgillID){

		//check if mcgillID is ok
		try{
			inputValidator.validateMcgillID(mcgillID);
		}
		catch(Exception e){
			//return error resp
		}

		//call service
		try{
			userAuthService.logoutService(mcgillID);
		}
		catch(Exception e){
			//return error resp
			return "err"; 
			//generate reps
		}
		//final resp to send
		return "loggedout"; 
	}


public String switchRole(int mcgillID){

	//call validation
	try{
		inputValidator.validateMcgillID(mcgillID);
	}
	catch(Exception e){
		//handle error resp
	}

	try{
		userAuthService.switchRoleService(mcgillID);
	}
	catch(Exception e){
		//handle errors
	}

	return "all good";
}
}