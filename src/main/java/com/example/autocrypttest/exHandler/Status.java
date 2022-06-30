package com.example.autocrypttest.exHandler;

public enum Status {
    SUCCESS("SUCCESS"), ERROR("ERROR");

    private final String status;

    Status(String status){
        this.status = status;
    }

    public String status () {
        return this.status;
    }

}
