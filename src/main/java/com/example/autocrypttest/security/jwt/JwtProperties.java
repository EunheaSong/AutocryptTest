package com.example.autocrypttest.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public  class JwtProperties {

    public static String key; //jwt secret key

    @Value("${SECRET_KEY}")
    public void setKey(String value) {
        key = value;
    }

}
