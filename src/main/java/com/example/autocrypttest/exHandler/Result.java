package com.example.autocrypttest.exHandler;

import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class Result {

    @Enumerated(EnumType.STRING)
    private Status status;
    private String message;

    public Result (){
    }

    public Result (Status status, String message) {
        this.status = status;
        this.message = message;
    }
}
