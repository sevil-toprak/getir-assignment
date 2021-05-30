package com.getir.assignment.controller.exception;

import com.getir.assignment.controller.handler.CommonResponseHandler;
import com.getir.assignment.controller.handler.ExceptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestResponseExceptionHandler.class);

    private CommonResponseHandler commonResponseHandler;

    public RestResponseExceptionHandler(CommonResponseHandler commonResponseHandler) {
        this.commonResponseHandler = commonResponseHandler;
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<?> invalidDataException(InvalidDataException ex, WebRequest request) {
        List<FieldError> errors = ex.getResult().getFieldErrors();
        for (FieldError error : errors) {
            logger.error("Filed Name ::: " + error.getField() + "Error Message :::" + error.getDefaultMessage());
        }
        return commonResponseHandler.errorResponseInvalidDataException("Error", commonResponseHandler.getFieldErrorResponse(ex.getResult()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> alreadyExistException(AlreadyExistsException ex) {
        return commonResponseHandler.errorResponse(String.format(ExceptionConstants.ALREADY_EXIST_EXCEPTION, ex.getEx()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException ex) {
        return commonResponseHandler.errorResponse(String.format(ExceptionConstants.NOT_FOUND_EXCEPTION, ex.getEx()));
    }

    @ExceptionHandler(BookStockException.class)
    public ResponseEntity<?> bookStockException(BookStockException ex) {
        return commonResponseHandler.errorResponse(String.format(ExceptionConstants.BOOK_STOCK_EXCEPTION, ex.getEx()));
    }

}