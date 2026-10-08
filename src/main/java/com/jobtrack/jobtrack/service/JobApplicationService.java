package com.jobtrack.jobtrack.service;

import com.jobtrack.jobtrack.dto.JobApplicationDTO;
import com.jobtrack.jobtrack.exception.ResourceNotFoundException;
import com.jobtrack.jobtrack.model.JobApplication;
import com.jobtrack.jobtrack.model.User;
import com.jobtrack.jobtrack.Repository.JobApplicationRepository;
import com.jobtrack.jobtrack.Repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository,
            UserRepository userRepository) {

        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
    }


    // =========================
    // GET CURRENT USER
    // =========================

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );
    }


    // =========================
    // CREATE APPLICATION
    // =========================

    public JobApplicationDTO createApplication(
            JobApplicationDTO dto) {

        User user = getCurrentUser();

        JobApplication application =
                new JobApplication();

        application.setCompanyName(
                dto.getCompanyName()
        );

        application.setJobTitle(
                dto.getJobTitle()
        );

        application.setLocation(
                dto.getJobLocation()
        );

        application.setStatus(
                dto.getStatus()
        );

        application.setUser(user);

        JobApplication saved =
                jobApplicationRepository.save(application);

        return convertToDTO(saved);
    }


    // =========================
    // GET CURRENT USER APPLICATIONS
    // =========================

    public List<JobApplicationDTO> getMyApplications() {

        User user = getCurrentUser();

        return jobApplicationRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    // =========================
    // GET APPLICATION BY ID
    // =========================

    public JobApplicationDTO getApplicationById(Long id) {

        User user = getCurrentUser();

        JobApplication application =
                jobApplicationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job application not found"
                                )
                        );

        if (!application.getUser().getId()
                .equals(user.getId())) {

            throw new ResourceNotFoundException(
                    "Job application not found"
            );
        }

        return convertToDTO(application);
    }


    // =========================
    // UPDATE APPLICATION
    // =========================

    public JobApplicationDTO updateApplication(
            Long id,
            JobApplicationDTO dto) {

        User user = getCurrentUser();

        JobApplication application =
                jobApplicationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job application not found"
                                )
                        );

        if (!application.getUser().getId()
                .equals(user.getId())) {

            throw new ResourceNotFoundException(
                    "Job application not found"
            );
        }

        application.setCompanyName(
                dto.getCompanyName()
        );

        application.setJobTitle(
                dto.getJobTitle()
        );

        application.setLocation(
                dto.getJobLocation()
        );

        application.setStatus(
                dto.getStatus()
        );

        JobApplication updated =
                jobApplicationRepository.save(application);

        return convertToDTO(updated);
    }


    // =========================
    // DELETE APPLICATION
    // =========================

    public void deleteApplication(Long id) {

        User user = getCurrentUser();

        JobApplication application =
                jobApplicationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job application not found"
                                )
                        );

        if (!application.getUser().getId()
                .equals(user.getId())) {

            throw new ResourceNotFoundException(
                    "Job application not found"
            );
        }

        jobApplicationRepository.delete(application);
    }


    // =========================
    // CONVERT TO DTO
    // =========================

    private JobApplicationDTO convertToDTO(
            JobApplication application) {

        JobApplicationDTO dto =
                new JobApplicationDTO();

        dto.setId(application.getId());

        dto.setCompanyName(
                application.getCompanyName()
        );

        dto.setJobTitle(
                application.getJobTitle()
        );

        dto.setJobLocation(
                application.getLocation()
        );

        dto.setStatus(
                application.getStatus()
        );

        return dto;
    }
}