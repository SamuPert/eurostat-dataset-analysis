package com.samupert.univpm.eurostat.monetary.poverty.controller;

import com.samupert.univpm.eurostat.common.ApiErrorResponse;
import com.samupert.univpm.eurostat.filtering.exception.InvalidFilterException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = MonetaryPovertyController.class)
public class MonetaryPovertyControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Invalid filter request.");
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(InvalidFilterException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidFilterException(@NonNull InvalidFilterException e) {
        ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
