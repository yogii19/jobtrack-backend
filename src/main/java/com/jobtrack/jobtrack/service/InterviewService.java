package com.jobtrack.jobtrack.service;

import com.jobtrack.jobtrack.dto.InterviewDTO;
import com.jobtrack.jobtrack.exception.ResourceNotFoundException;
import com.jobtrack.jobtrack.model.Interview;
import com.jobtrack.jobtrack.model.JobApplication;
import com.jobtrack.jobtrack.model.User;
import com.jobtrack.jobtrack.Repository.InterviewRepository;
import com.jobtrack.jobtrack.Repository.JobApplicationRepository;
import com.jobtrack.jobtrack.Repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;


    public InterviewService(
            InterviewRepository interviewRepository,
            JobApplicationRepository jobApplicationRepository,
            UserRepository userRepository) {

        this.interviewRepository =
                interviewRepository;

        this.jobApplicationRepository =
                jobApplicationRepository;

        this.userRepository =
                userRepository;
    }


    // =====================================================
    // GET CURRENT LOGGED-IN USER
    // =====================================================

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );
    }


    // =====================================================
    // CREATE INTERVIEW
    // =====================================================

    public InterviewDTO createInterview(
            Long applicationId,
            InterviewDTO dto) {

        User currentUser =
                getCurrentUser();


        // Find job application

        JobApplication jobApplication =
                jobApplicationRepository
                        .findById(applicationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job application not found"
                                )
                        );


        // Make sure application belongs
        // to currently logged-in user

        if (!jobApplication
                .getUser()
                .getId()
                .equals(currentUser.getId())) {

            throw new ResourceNotFoundException(
                    "Job application not found"
            );
        }


        // Create interview

        Interview interview =
                new Interview();


        interview.setInterviewDate(
                dto.getInterviewDate()
        );

        interview.setInterviewType(
                dto.getInterviewType()
        );

        interview.setStatus(
                dto.getStatus()
        );


        // Connect interview with
        // selected job application

        interview.setJobApplication(
                jobApplication
        );


        // Save

        Interview savedInterview =
                interviewRepository.save(
                        interview
                );


        return convertToDTO(
                savedInterview
        );
    }


    // =====================================================
    // GET CURRENT USER'S INTERVIEWS
    // =====================================================

    public List<InterviewDTO> getMyInterviews() {

        User currentUser =
                getCurrentUser();


        return interviewRepository
                .findByJobApplicationUserId(
                        currentUser.getId()
                )
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    // =====================================================
    // GET INTERVIEW BY ID
    // =====================================================

    public InterviewDTO getInterviewById(
            Long id) {

        User currentUser =
                getCurrentUser();


        Interview interview =
                interviewRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview not found"
                                )
                        );


        // Ownership check

        if (!interview
                .getJobApplication()
                .getUser()
                .getId()
                .equals(currentUser.getId())) {

            throw new ResourceNotFoundException(
                    "Interview not found"
            );
        }


        return convertToDTO(
                interview
        );
    }


    // =====================================================
    // UPDATE INTERVIEW
    // =====================================================

    public InterviewDTO updateInterview(
            Long id,
            InterviewDTO dto) {

        User currentUser =
                getCurrentUser();


        Interview interview =
                interviewRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview not found"
                                )
                        );


        // Ownership check

        if (!interview
                .getJobApplication()
                .getUser()
                .getId()
                .equals(currentUser.getId())) {

            throw new ResourceNotFoundException(
                    "Interview not found"
            );
        }


        // Update interview details

        interview.setInterviewDate(
                dto.getInterviewDate()
        );

        interview.setInterviewType(
                dto.getInterviewType()
        );

        interview.setStatus(
                dto.getStatus()
        );


        Interview updatedInterview =
                interviewRepository.save(
                        interview
                );


        return convertToDTO(
                updatedInterview
        );
    }


    // =====================================================
    // DELETE INTERVIEW
    // =====================================================

    public void deleteInterview(
            Long id) {

        User currentUser =
                getCurrentUser();


        Interview interview =
                interviewRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview not found"
                                )
                        );


        // Ownership check

        if (!interview
                .getJobApplication()
                .getUser()
                .getId()
                .equals(currentUser.getId())) {

            throw new ResourceNotFoundException(
                    "Interview not found"
            );
        }


        interviewRepository.delete(
                interview
        );
    }


    // =====================================================
    // CONVERT ENTITY → DTO
    // =====================================================

    private InterviewDTO convertToDTO(
            Interview interview) {

        InterviewDTO dto =
                new InterviewDTO();


        // Interview ID

        dto.setId(
                interview.getId()
        );


        // Job Application ID

        dto.setApplicationId(
                interview
                        .getJobApplication()
                        .getId()
        );


        // Company Name

        dto.setCompanyName(
                interview
                        .getJobApplication()
                        .getCompanyName()
        );


        // Job Title

        dto.setJobTitle(
                interview
                        .getJobApplication()
                        .getJobTitle()
        );


        // Interview Date

        dto.setInterviewDate(
                interview.getInterviewDate()
        );


        // Interview Type

        dto.setInterviewType(
                interview.getInterviewType()
        );


        // Interview Status

        dto.setStatus(
                interview.getStatus()
        );


        return dto;
    }
}