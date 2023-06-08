package com.samupert.univpm.eurostat.monetary.poverty.controller;

import com.samupert.univpm.eurostat.common.ApiErrorResponse;
import com.samupert.univpm.eurostat.common.mapper.MappingException;
import com.samupert.univpm.eurostat.filtering.exception.InvalidFilterException;
import com.samupert.univpm.eurostat.filtering.exception.InvalidOperatorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = MonetaryPovertyController.class)
public class MonetaryPovertyControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "Invalid filter request.");
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(InvalidFilterException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidFilterException(InvalidFilterException e) {
        ApiErrorResponse apiResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
