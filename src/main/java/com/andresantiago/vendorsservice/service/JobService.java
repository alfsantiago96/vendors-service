package com.andresantiago.vendorsservice.service;

import com.andresantiago.vendorsservice.api.request.CreateJobRequest;
import com.andresantiago.vendorsservice.entity.JobEntity;
import com.andresantiago.vendorsservice.mapper.JobEntityMapper;
import com.andresantiago.vendorsservice.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public void createJob (CreateJobRequest request) {
        log.info("Saving the Job...");
        JobEntity map = JobEntityMapper.map(request);
        jobRepository.save(map);
        log.info("Job saved with success.");
    }
}