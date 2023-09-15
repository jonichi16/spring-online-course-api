package com.jonichi.soc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "instructor")
public class Instructor extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private final Role role = Role.INSTRUCTOR;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public Instructor() {
    }

    public Instructor(String email, LocalDateTime createdAt, LocalDateTime updatedAt, Account account) {
        super(email, createdAt, updatedAt);
        this.account = account;
    }

    public Role getRole() {
        return role;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
