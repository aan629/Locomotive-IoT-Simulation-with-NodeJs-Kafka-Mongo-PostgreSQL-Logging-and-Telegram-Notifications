package com.example.backEndreportScheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backEndreportScheduler.model.LocomotifSummaryModel;

@Repository
public interface LocomotifSummaryRepos extends JpaRepository<LocomotifSummaryModel, String>{
    LocomotifSummaryModel findFirstByOrderByUpdatedAtDesc();

    List<LocomotifSummaryModel> findAllByOrderByUpdatedAtDesc();
    List<LocomotifSummaryModel> findAllByOrderByUpdatedAtAsc();
}
