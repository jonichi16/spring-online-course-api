package com.jonichi.soc.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private final Role role = Role.STUDENT;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Account account;

    public Student() {
    }

    public Student(String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(email, createdAt, updatedAt);
    }

    public Role getRole() {
        return role;
    }
}
