package com.jobtrack.jobtrack.Controller;

import com.jobtrack.jobtrack.dto.InterviewDTO;
import com.jobtrack.jobtrack.service.InterviewService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(
            InterviewService interviewService) {

        this.interviewService = interviewService;
    }


    // =========================
    // CREATE INTERVIEW
    // =========================

    @PostMapping("/application/{applicationId}")
    public ResponseEntity<InterviewDTO> createInterview(
            @PathVariable Long applicationId,
            @Valid @RequestBody InterviewDTO dto) {

        return ResponseEntity.ok(
                interviewService.createInterview(
                        applicationId,
                        dto
                )
        );
    }


    // =========================
    // GET MY INTERVIEWS
    // =========================

    @GetMapping
    public ResponseEntity<List<InterviewDTO>> getMyInterviews() {

        return ResponseEntity.ok(
                interviewService.getMyInterviews()
        );
    }


    // =========================
    // GET INTERVIEW BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<InterviewDTO> getInterviewById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                interviewService.getInterviewById(id)
        );
    }


    // =========================
    // UPDATE INTERVIEW
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<InterviewDTO> updateInterview(
            @PathVariable Long id,
            @Valid @RequestBody InterviewDTO dto) {

        return ResponseEntity.ok(
                interviewService.updateInterview(
                        id,
                        dto
                )
        );
    }


    // =========================
    // DELETE INTERVIEW
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(
            @PathVariable Long id) {

        interviewService.deleteInterview(id);

        return ResponseEntity.noContent().build();
    }
}