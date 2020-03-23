package com.pkaushik.safeHome.controller;

import java.math.BigInteger;

import com.pkaushik.safeHome.service.impl.UserAuthService;
import com.pkaushik.safeHome.validation.impl.InputValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pkaushik.safeHome.utils.JsonResponseConstants.*;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
    private UserAuthService userAuthService;
    
    @Autowired
    private InputValidator inputValidator;
	
    @RequestMapping(value = "/register/{mcgillID}/{phoneNo}/{registerAsWalker}", method = RequestMethod.POST)
	public ResponseEntity<String> register(@PathVariable("phoneNo") BigInteger phoneNo, @PathVariable("mcgillID") int mcgillID, @PathVariable("registerAsWalker") boolean registerAsWalker){

        //input validation for mcgill ID and phoneNo
        try{
            inputValidator.validateMcgillID(mcgillID);
            inputValidator.validatePhoneNo(phoneNo);
        }
        catch(IllegalArgumentException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

		//delegate state valid to service
		try {
			userAuthService.registerService(phoneNo, mcgillID, registerAsWalker);
		}
		catch(Exception e){
			//catch exceptions from service layer
			//return these resps formatted well.
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		//generate resp
		return new ResponseEntity<>(JSON_SUCCESS_MESSAGE, HttpStatus.OK);
	}

	@RequestMapping(value = "/login/{mcgillID}/{loginAsWalker}", method=RequestMethod.POST)
	public ResponseEntity<String> login(@PathVariable("mcgillID") int mcgillID, @PathVariable("loginAsWalker") boolean loginAsWalker){

		//check input validation
		try{
			inputValidator.validateMcgillID(mcgillID);
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}

		//call service to log in
		try{
			userAuthService.loginService(mcgillID, loginAsWalker);
		}
		catch(Exception e){
			//return error resp
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			//generate resp
		}
		//final resp to send.
		return new ResponseEntity<>(JSON_SUCCESS_MESSAGE,HttpStatus.OK);
	}

	@RequestMapping(value="/logout/{mcgillID}", method = RequestMethod.POST)
	public ResponseEntity<String> logout(@PathVariable(name="mcgillID") int mcgillID){

		//check if mcgillID is ok
		try{
			inputValidator.validateMcgillID(mcgillID);
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}

		//call service
		try{
			userAuthService.logoutService(mcgillID);
		}
		catch(Exception e){
			//return error resp
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			//generate reps
		}
		//final resp to send
		return new ResponseEntity<>(JSON_SUCCESS_MESSAGE,HttpStatus.OK);
	}

	@RequestMapping(value="/switchrole/{mcgillID}",method = RequestMethod.POST)
	public ResponseEntity<String> switchRole(@PathVariable(name="mcgillID") int mcgillID){

	//call validation
	try{
		inputValidator.validateMcgillID(mcgillID);
	}
	catch(Exception e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}

	try{
		userAuthService.switchRoleService(mcgillID);
	}
	catch(Exception e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	return new ResponseEntity<>(JSON_SUCCESS_MESSAGE,HttpStatus.OK);
}
}