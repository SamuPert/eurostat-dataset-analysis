package com.samupert.univpm.eurostat.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class that represent an API error response.
 */
@Data
public final class ApiErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime timestamp;
    private final HttpStatus status;
    private final String error;

    /**
     * Creates a new API error response.
     *
     * @param status The HTTP status code.
     * @param error  The error message.
     */
    public ApiErrorResponse(HttpStatus status, String error) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
    }

    /**
     * Gets the timestamp of the error.
     *
     * @return Returns the timestamp of the error.
     */
    public LocalDateTime timestamp() {
        return timestamp;
    }

    /**
     * Gets the Http status code. See {@link HttpStatus}.
     *
     * @return Returns the HTTP status code.
     */
    public HttpStatus status() {
        return status;
    }

    /**
     * Gets the error message.
     *
     * @return Returns the error message.
     */
    public String error() {
        return error;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ApiErrorResponse) obj;
        return Objects.equals(this.timestamp, that.timestamp) &&
                       Objects.equals(this.status, that.status) &&
                       Objects.equals(this.error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, status, error);
    }

    @Override
    public String toString() {
        return "ApiResponse[" +
                       "timestamp=" + timestamp + ", " +
                       "status=" + status + ", " +
                       "error=" + error + ']';
    }

}