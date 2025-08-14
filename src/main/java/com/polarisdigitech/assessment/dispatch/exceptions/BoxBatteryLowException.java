package com.polarisdigitech.assessment.dispatch.exceptions;

public class BoxBatteryLowException extends RuntimeException{
    public BoxBatteryLowException(String message){
        super(message);
    }
}
