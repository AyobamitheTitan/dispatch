package com.polarisdigitech.assessment.dispatch.responses;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler<T> {
    public static <T> ResponseEntity<Object> generateResponse(String message, HttpStatus status, T responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map, status);
    }
    public static <T> ResponseEntity<Map<String,Object>> generateErrorResponse(HttpStatus status, ErrorResponse responseObj) {

        return new ResponseEntity<Map<String, Object>>(responseObj.asMap(), status);
    }
}
