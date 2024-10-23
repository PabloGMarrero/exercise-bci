package com.bci.bci.user.infrastructure.adapters.in.rest.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPhoneResponse {
    private Long number;
    private Integer cityCode;
    private String countryCode;
}
