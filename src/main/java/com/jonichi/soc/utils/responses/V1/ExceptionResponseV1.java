package com.jonichi.soc.utils.responses.V1;

public class ExceptionResponseV1 {

    private int status;
    private Object error;

    public ExceptionResponseV1() {
    }

    public ExceptionResponseV1(int status, Object error) {
        this.status = status;
        this.error = error;
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
}
