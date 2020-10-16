package com.piano.exception;

public class DailyCheckException extends RuntimeException {
    private String msg;
    public DailyCheckException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
