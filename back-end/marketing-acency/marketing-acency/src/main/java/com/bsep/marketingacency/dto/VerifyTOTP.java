package com.bsep.marketingacency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyTOTP {
    private String mail;
    private String code;
}
