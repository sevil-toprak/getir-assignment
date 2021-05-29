package com.getir.assignment.controller.exception;

public class NotFoundException extends RuntimeException {

    private String ex;

    public NotFoundException(String ex) {
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
