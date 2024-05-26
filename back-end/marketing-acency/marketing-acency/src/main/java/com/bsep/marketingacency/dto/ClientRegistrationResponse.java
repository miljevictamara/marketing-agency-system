package com.bsep.marketingacency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRegistrationResponse {
    private boolean mfa;
    private String secretImageUri;

    public ClientRegistrationResponse(boolean mfa, String secretImageUri) {
        this.mfa = mfa;
        this.secretImageUri = secretImageUri;
    }

    // Added the URI for the QR code image,
    // and the flag, so that, the front-end can decide whether to display the QR code or not
}
