package com.example.autocrypttest.exHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
/*
    Controller 에서 발생한 예외를 핸들링.
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

//    private ResponseEntity<Result> response(Result result ,HttpStatus status) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/json");
//        return new ResponseEntity<>(result, headers, status);
//    }

    @ExceptionHandler({IllegalArgumentException.class,UserNotMatchException.class, NullPointerException.class})
    public ResponseEntity<?> illegalExHandler(IllegalArgumentException e){
        log.error(e.getMessage());
        Result result = new Result(Status.ERROR, e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchExHandler(NoSuchElementException e){
        log.error(e.getMessage());
        Result result = new Result(Status.ERROR, e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    /*
    예상하지 못한 에러에 대한 처리.
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> ExHandler(Exception e){
        log.error(e.getMessage());
        Result result = new Result(Status.ERROR, e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
