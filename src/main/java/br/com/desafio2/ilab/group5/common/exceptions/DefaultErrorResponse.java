package br.com.desafio2.ilab.group5.common.exceptions;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultErrorResponse {
    private String status;
    private int code;
    private String message;
    private long timestamp;
    private String path;
    private ArrayList<ValidationErrors> validationErrors;

    public DefaultErrorResponse(HttpStatus status, String message, String path) {
        super();
        this.status = status.name();
        this.code = status.value();
        this.message = message;
        this.timestamp = new Date().getTime();
        this.path = path;
    }

}
