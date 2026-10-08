package com.jobtrack.jobtrack.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<JobApplication> jobApplications;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(
            Long id,
            String name,
            String email,
            String password) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(
            List<JobApplication> jobApplications) {

        this.jobApplications = jobApplications;
    }
}