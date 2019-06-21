package com.pkaushik.safeHome.validation.impl;

import java.math.BigInteger;

import com.pkaushik.safeHome.validation.inputValidationIF;
import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_DIGITS_FOR_PHONE;
import static com.pkaushik.safeHome.utils.ValidationConstants.MAX_DIGITS_FOR_ID;

public class inputValidation implements inputValidationIF{
    
    public void validateMcgillID(int mcgillID){
        if(new Integer(mcgillID).toString().trim().length() != MAX_DIGITS_FOR_ID){
			throw new IllegalArgumentException("McGill ID should be 10 digits long!");
        }
        
        char[] idDigits = ("" + mcgillID).toCharArray();
        if (
                (Character.getNumericValue(idDigits[0]) == 2)
                        && (Character.getNumericValue(idDigits[1]) == 6) &&
                        (Character.getNumericValue(idDigits[2]) == 0)
        ) {
            //mcgill ID is okay
        } else {
            throw new IllegalArgumentException("McGill ID should start with '260'");
        }
        return; 
    }

    public void validatePhoneNo(BigInteger phoneNo){
        if(phoneNo.toString().trim().length() != MAX_DIGITS_FOR_PHONE) {
			throw new IllegalArgumentException("Phone Number should be 10 digits long");
        }

        return; 
    }
}

