package com.sanjay.kfh.exception;

import com.sanjay.kfh.responce.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Sanjay Vishwakarma
 */
@ControllerAdvice
public class ExceptionHandling {
    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(InvalidRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleInvalidRoleException(InvalidRoleException ex, WebRequest request) {
        return new ExceptionResponse(ex.getTransactionId(),
                ex.getStatusCode(),
                "There was an error processing your request.","Incorrect role specified.");
    }

    @ExceptionHandler(StudentCourseIllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse handleStudentCourseIllegalStateException(InvalidRoleException ex, WebRequest request) {
        return new ExceptionResponse(ex.getTransactionId(),
                ex.getStatusCode(),
                "There was an error processing your request for Student Allocation.","Incorrect Student Course.");
    }


    @ExceptionHandler(StudentIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    protected ExceptionResponse handleStudentIdNotFoundException(InvalidRoleException ex, WebRequest request) {
        return new ExceptionResponse(ex.getTransactionId(),
                ex.getStatusCode(),
                "There was an error processing your request for Student.","Incorrect Student Id.");
    }
}
