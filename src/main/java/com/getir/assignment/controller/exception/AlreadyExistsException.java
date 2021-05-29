package com.getir.assignment.controller.exception;

public class AlreadyExistsException extends RuntimeException {

    private String ex;

    public AlreadyExistsException(String ex) {
        super(ex);
        this.ex = ex;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }
}
