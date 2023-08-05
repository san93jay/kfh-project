package com.sanjay.kfh.responce;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Sanjay Vishwakarma
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ExceptionResponse extends BaseResponse {

    public ExceptionResponse(String transactionId, Integer statusCode, String statusMessage, String supportMessage) {
        super.setTransactionId(transactionId);
        super.setStatusCode(statusCode);
        super.setStatusMessage(statusMessage);
        super.setSupportMessage(supportMessage);
    }

    public ExceptionResponse(Integer statusCode, String statusMessage, String supportMessage) {
        super.setStatusCode(statusCode);
        super.setStatusMessage(statusMessage);
        super.setSupportMessage(supportMessage);
    }
}