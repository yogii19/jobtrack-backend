package com.jobtrack.jobtrack.Controller;

import com.jobtrack.jobtrack.dto.JobApplicationDTO;
import com.jobtrack.jobtrack.service.JobApplicationService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(
            JobApplicationService jobApplicationService) {

        this.jobApplicationService = jobApplicationService;
    }


    // =========================
    // CREATE JOB APPLICATION
    // =========================

    @PostMapping
    public ResponseEntity<JobApplicationDTO> createJobApplication(
            @Valid @RequestBody JobApplicationDTO dto) {

        return ResponseEntity.ok(
                jobApplicationService.createApplication(dto)
        );
    }


    // =========================
    // GET MY APPLICATIONS
    // =========================

    @GetMapping
    public ResponseEntity<List<JobApplicationDTO>> getAllJobApplications() {

        return ResponseEntity.ok(
                jobApplicationService.getMyApplications()
        );
    }


    // =========================
    // GET APPLICATION BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationDTO> getJobApplicationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                jobApplicationService.getApplicationById(id)
        );
    }


    // =========================
    // UPDATE APPLICATION
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationDTO> updateJobApplication(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationDTO dto) {

        return ResponseEntity.ok(
                jobApplicationService.updateApplication(id, dto)
        );
    }


    // =========================
    // DELETE APPLICATION
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(
            @PathVariable Long id) {

        jobApplicationService.deleteApplication(id);

        return ResponseEntity.noContent().build();
    }
}