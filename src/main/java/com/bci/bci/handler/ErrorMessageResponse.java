package com.bci.bci.handler;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ErrorMessageResponse {

    private List<ErrorResponse> error;
}
