package com.jonichi.soc.dto.V1;

public class AccountDtoV1 {

    private Long accountId;
    private String username;

    public AccountDtoV1() {
    }

    public Long getId() {
        return accountId;
    }

    public void setId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
