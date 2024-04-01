package com.example.backEndreportScheduler.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.backEndreportScheduler.model.LocoStatusSummaryModel;

import jakarta.transaction.Transactional;

@Repository
public interface LocoStatusSummaryRepos extends JpaRepository<LocoStatusSummaryModel, UUID>{
    @Modifying
    @Transactional
    @Query("UPDATE LocoStatusSummaryModel l SET l.active = 0, l.notActive = 0, l.maintenance = 0, l.total = 0")
    void resetAllNumbers();
}
