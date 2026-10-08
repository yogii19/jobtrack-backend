package com.jobtrack.jobtrack.Repository;

import com.jobtrack.jobtrack.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository
        extends JpaRepository<Interview, Long> {

    List<Interview> findByJobApplicationUserId(Long userId);

}