package com.jonichi.soc.responses.V1;

public class ExceptionResponseV1 {

    private int status;
    private Object error;
    private String message;

    public ExceptionResponseV1() {
    }

    public ExceptionResponseV1(int status, Object error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
