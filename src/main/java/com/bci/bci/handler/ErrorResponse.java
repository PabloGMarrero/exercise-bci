package com.bci.bci.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private String timestamp;

    @JsonProperty("codigo")
    private Integer code;

    private String detail;
}
