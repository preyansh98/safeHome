package com.pkaushik.safeHome.exceptions;

import java.time.DateTimeException;

public class DateConventionException extends DateTimeException {

    public DateConventionException(){
        super("Please check that you have entered a correct date and/or time");
    }

    public DateConventionException(String message){
        super(message);
    }
}
