package com.andresantiago.vendorsservice.repository;

import com.andresantiago.vendorsservice.entity.JobEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JobDatabaseInMemory {

    List<JobEntity> jobs = new ArrayList<>();

    public List<JobEntity> findJobsByVendor(String taxId) {
       return jobs.stream()
                .filter(jobEntity -> jobEntity.getVendor().getTaxId().equals(taxId))
                .collect(Collectors.toList());
    }

    public List<JobEntity> findJobsByCompany(String taxId) {
        return jobs.stream()
                .filter(jobEntity -> jobEntity.getCompanyTaxId().equals(taxId))
                .collect(Collectors.toList());
    }

    public void create(JobEntity job) {
        jobs.add(job);
    }
}