package com.swifta.ussd.serviceClient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppSuccessResponse<T> {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date timestamp;
    private String statusCode;
    private String responseCode;
    private String message;
    private T data;

    public AppSuccessResponse(Date timestamp, String statusCode, String message) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
    }

    public AppSuccessResponse(String statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.setData(data);
    }

    public AppSuccessResponse(Date timestamp, String statusCode, String message, T data) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.setData(data);
    }
}
