package com.sanjay.kfh.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sanjay Vishwakarma
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentLoginRequest {
    private static final long serialVersionUID = 92646850707L;

    private String username;
    private String password;
}
