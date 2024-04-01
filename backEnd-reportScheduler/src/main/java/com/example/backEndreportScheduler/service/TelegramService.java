package com.example.backEndreportScheduler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backEndreportScheduler.model.LocoStatusSummaryModel;
import com.example.backEndreportScheduler.model.LocomotifSummaryModel;
import com.example.backEndreportScheduler.repository.LocoStatusSummaryRepos;
import com.example.backEndreportScheduler.repository.LocomotifSummaryRepos;

@Service
public class TelegramService {
    private final LocomotifSummaryRepos locoSummaryRepository;
    private final LocoStatusSummaryRepos locoStatusSummaryRepository;

    public TelegramService(LocomotifSummaryRepos locoSummaryRepository,
            LocoStatusSummaryRepos locoStatusSummaryRepository) {
        this.locoSummaryRepository = locoSummaryRepository;
        this.locoStatusSummaryRepository = locoStatusSummaryRepository;
    }

    public LocomotifSummaryModel getLatestSummary() {
        return locoSummaryRepository.findFirstByOrderByUpdatedAtDesc();
    }

    public List<LocoStatusSummaryModel> getLatestSummaryStatus() {
        return locoStatusSummaryRepository.findAll();
    }
}
