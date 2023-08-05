package com.sanjay.kfh.responce;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sanjay Vishwakarma
 */


public class KfhJwtResponse implements Serializable {
    private static final long serialVersionUID = -80991924046844L;
    private final String jwttoken;

    public KfhJwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
}
