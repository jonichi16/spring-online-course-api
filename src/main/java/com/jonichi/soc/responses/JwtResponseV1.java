package com.jonichi.soc.responses;

import java.io.Serializable;

public class JwtResponseV1 implements Serializable {

    private static final long serialVersionUID = -7782912788474828628L;

    private String jwtToken;
    private Object data;
    private int status;
    private String message;

    public JwtResponseV1() {
    }

    public JwtResponseV1(String jwtToken, Object data, int status, String message) {
        this.jwtToken = jwtToken;
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
