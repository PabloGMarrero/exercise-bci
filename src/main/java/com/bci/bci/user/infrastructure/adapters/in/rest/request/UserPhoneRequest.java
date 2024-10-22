package com.bci.bci.user.infrastructure.adapters.in.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserPhoneRequest {

    @Schema(description = "Phone number")
    private long number;

    @Schema(description = "Phone city code")
    @JsonProperty("citycode")
    private int cityCode;

    @Schema(description = "Phone country code")
    @JsonProperty("contrycode")
    private String contryCode;
}
