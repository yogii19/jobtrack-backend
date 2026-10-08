package com.jobtrack.jobtrack.dto;

import com.jobtrack.jobtrack.model.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class JobApplicationDTO {

    private Long id;
    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Job location is required")
    private String jobLocation;

    @NotNull(message = "Application status is required")
    private ApplicationStatus status;

    public JobApplicationDTO() {
    }

    public JobApplicationDTO(Long id, String companyName, String jobTitle,
                             String jobLocation, ApplicationStatus status) {
        this.id = id;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobLocation = jobLocation;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}