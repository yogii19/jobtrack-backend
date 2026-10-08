package com.jobtrack.jobtrack.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime interviewDate;

    private String interviewType;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    @ManyToOne
    @JoinColumn(name = "job_application_id")
    private JobApplication jobApplication;

    public Interview() {
    }

    public Interview(Long id, LocalDateTime interviewDate,
                     String interviewType, InterviewStatus status,
                     JobApplication jobApplication) {
        this.id = id;
        this.interviewDate = interviewDate;
        this.interviewType = interviewType;
        this.status = status;
        this.jobApplication = jobApplication;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(String interviewType) {
        this.interviewType = interviewType;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public JobApplication getJobApplication() {
        return jobApplication;
    }

    public void setJobApplication(JobApplication jobApplication) {
        this.jobApplication = jobApplication;
    }
}