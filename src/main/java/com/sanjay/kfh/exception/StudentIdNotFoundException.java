package com.sanjay.kfh.exception;

/**
 * @author Sanjay Vishwakarma
 */
public class StudentIdNotFoundException extends RuntimeException{

    private static final long serialVersionUID = -681805602069L;

    private String transactionId;

    private Integer statusCode;

    public StudentIdNotFoundException() {
    }

    public StudentIdNotFoundException(Integer statusCode, String errorMessage, Throwable err) {
        super(errorMessage, err);
        this.statusCode = statusCode;
    }
}
