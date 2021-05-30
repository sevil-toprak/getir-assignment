package com.getir.assignment.controller.exception;

public class BookStockException extends RuntimeException {

    private String ex;

    public BookStockException(String ex) {
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
