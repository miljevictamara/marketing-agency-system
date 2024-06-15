package com.bsep.marketingacency.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class RecaptchaResponse {
    @Setter
    @Getter
    private boolean success;
    @Getter
    @Setter
    private String hostname;
    private String challenge_ts;
    @JsonProperty("error-codes")
    private String[] errorCodes;


}
