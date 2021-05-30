package com.getir.assignment.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonResponseHandler {
    public Map<String, Object> getFieldErrorResponse(BindingResult result) {

        Map<String, Object> fieldError = new HashMap<>();
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            fieldError.put(error.getField(), error.getDefaultMessage());
        }
        return fieldError;
    }

    public ResponseEntity<Object> errorResponseInvalidDataException(String message, Object fieldError) {
        Map<String, Object> map = new HashMap<>();
        map.put("isSuccess", false);
        map.put("data", null);
        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("message", message);
        map.put("filedError", fieldError);

        return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> errorResponse(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("isSuccess", false);
        map.put("data", null);
        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("message", message);

        return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
    }

}


