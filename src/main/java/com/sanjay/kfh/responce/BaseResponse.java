package com.sanjay.kfh.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanjay.kfh.utils.KfhConstant;
import lombok.Data;

/**
 * @author Sanjay Vishwakarma
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BaseResponse {
    @JsonProperty(KfhConstant.KFH_PARAM_TRANSACTION_ID)
    private String transactionId;

    @JsonProperty(KfhConstant.KFH_RESPONSE_PARAM_STATUS_CODE)
    private Integer statusCode;

    @JsonProperty(KfhConstant.KFH_RESPONSE_PARAM_STATUS_MESSAGE)
    private String statusMessage;

    @JsonProperty(KfhConstant.KFH_RESPONSE_PARAM_SUPPORT_MESSAGE)
    private String supportMessage;
}