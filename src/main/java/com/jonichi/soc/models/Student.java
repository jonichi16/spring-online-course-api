package com.jonichi.soc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private final Role role = Role.STUDENT;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public Student() {
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
