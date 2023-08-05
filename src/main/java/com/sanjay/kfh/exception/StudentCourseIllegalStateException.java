package com.sanjay.kfh.exception;

/**
 * @author Sanjay Vishwakarma
 */

public class StudentCourseIllegalStateException extends IllegalStateException {
    private static final long serialVersionUID = 1L;

    private String transactionId;

    private Integer statusCode;

    public StudentCourseIllegalStateException() {
    }

    public StudentCourseIllegalStateException(Integer statusCode, String errorMessage, Throwable err) {
        super(errorMessage, err);
        this.statusCode = statusCode;
    }
}

