package org.example.week02.exception;



public class serviceException extends RuntimeException {

    private String code;
    public serviceException(String message,String code) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
