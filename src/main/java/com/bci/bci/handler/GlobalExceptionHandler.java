package com.bci.bci.handler;

import com.bci.bci.user.domain.exceptions.CreateUserException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var message = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .map(s -> ErrorResponse.builder()
                        .code(400)
                        .detail(s)
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                        .build())
                .collect(Collectors.toList());
        return buildResponseEntity(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateUserException.class)
    protected ResponseEntity<Object> handleCreateUserException(CreateUserException ex) {
        var message = Collections.singletonList(ErrorResponse.builder()
                        .code(400)
                        .detail(ex.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                        .build());
        return buildResponseEntity(message, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(List<ErrorResponse> errorResponses, HttpStatus status) {
        return new ResponseEntity<>(ErrorMessageResponse.builder().error(errorResponses).build(), status);
    }


}
