package com.polarisdigitech.assessment.dispatch.exceptions.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.polarisdigitech.assessment.dispatch.exceptions.BoxBatteryLowException;
import com.polarisdigitech.assessment.dispatch.exceptions.BoxNotFoundException;
import com.polarisdigitech.assessment.dispatch.exceptions.FullBoxException;
import com.polarisdigitech.assessment.dispatch.responses.ErrorResponse;
import com.polarisdigitech.assessment.dispatch.responses.ResponseHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler({
                BoxBatteryLowException.class
        })
        public ResponseEntity<Map<String, Object>> handleBadRequestException(
                        Exception ex, WebRequest request) {
                                
                ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad request",
                                ex.getMessage());
                return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, error);
        }

        @ExceptionHandler({
                BoxNotFoundException.class
        })
        public ResponseEntity<Map<String,Object>> handleBoxNotFoundException(
                        Exception ex, WebRequest request) {
                ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Box not found",
                                ex.getMessage());
                return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, error);
        }

        @ExceptionHandler({
                MethodArgumentNotValidException.class
        })
        public ResponseEntity<Map<String,Object>> handleValidationException(
                        MethodArgumentNotValidException ex, WebRequest request) {
                        String errorString = "";
                        var fieldErrors = ex.getFieldErrors();
                        for (FieldError fieldError : fieldErrors) {
                                errorString += "%s %s; ".formatted(fieldError.getField(), fieldError.getDefaultMessage());
                        }
                ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad reqest",
                                errorString);
                return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, error);
        }

        @ExceptionHandler({
                FullBoxException.class
        })
        public ResponseEntity<Map<String,Object>> handleFullBoxException(
                        FullBoxException ex, WebRequest request) {
                ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad reqest",
                                ex.getMessage());
                return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, error);
        }

        @ExceptionHandler({
                HttpMessageNotReadableException.class
        })
        public ResponseEntity<Map<String,Object>> handleMessageNotReadableException(
                        HttpMessageNotReadableException ex, WebRequest request) {
                ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad reqest",
                                ex.getMessage());
                return ResponseHandler.generateErrorResponse(HttpStatus.BAD_REQUEST, error);
        }
}