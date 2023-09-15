package com.jonichi.soc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jonichi.soc.utils.Role;

import javax.persistence.*;
import java.util.Set;

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

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Course> courses;

    public Instructor() {
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

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
