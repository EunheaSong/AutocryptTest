package com.example.autocrypttest.exHandler;

public class UserNotMatchException extends IllegalArgumentException{
    private static final String message = "사용자 정보가 일치하지 않습니다.";

    public UserNotMatchException (String tokenName, String requestName){
        super(message + tokenName + "!=" + requestName);
    }

}
