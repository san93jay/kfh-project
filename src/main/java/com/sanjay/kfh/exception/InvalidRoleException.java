package com.sanjay.kfh.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Sanjay Vishwakarma
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InvalidRoleException extends RuntimeException{

    private static final long serialVersionUID = -7295681805602069L;

    private String transactionId;

    private Integer statusCode;

    public InvalidRoleException() {
    }

    public InvalidRoleException(Integer statusCode, String errorMessage, Throwable err) {
        super(errorMessage, err);
        this.statusCode = statusCode;
    }
}
