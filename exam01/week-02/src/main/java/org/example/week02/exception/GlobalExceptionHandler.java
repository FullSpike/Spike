package org.example.week02.exception;


import org.example.week02.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(serviceException.class)
        @ResponseBody
        public Result handleException(serviceException e){
            e.printStackTrace();
            return Result.error(e.getMessage(), e.getCode());
        }

        @ExceptionHandler(Exception.class)
        public Result handleException(Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage(), "400");
        }
}
