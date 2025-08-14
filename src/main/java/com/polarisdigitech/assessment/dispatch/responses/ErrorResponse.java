package com.polarisdigitech.assessment.dispatch.responses;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    private int status;
    private String error;
    private String message;

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
    
    public Map<String, Object> asMap(){
        Map<String, Object> map =  new HashMap<>();
        map.put("status", status);
        map.put("error", error);
        map.put("message", message);
        return map;
    }
}