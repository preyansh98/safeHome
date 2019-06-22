package com.pkaushik.safeHome.exceptions;

import java.time.DateTimeException;

public class ScheduleDateTimeException extends DateTimeException {

    public ScheduleDateTimeException(String message){
        super(message);
    }
}
