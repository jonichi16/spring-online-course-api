package com.jonichi.soc.requests;

import java.io.Serializable;

public class InstructorRequest implements Serializable {

    private static final long serialVersionUID = -1091903722936477390L;


    private String username;
    private String password;

    private String fullName;
    private String email;

    private String imageUrl;

    public InstructorRequest() {
    }

    public InstructorRequest(String username, String password, String fullName, String email, String imageUrl) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
