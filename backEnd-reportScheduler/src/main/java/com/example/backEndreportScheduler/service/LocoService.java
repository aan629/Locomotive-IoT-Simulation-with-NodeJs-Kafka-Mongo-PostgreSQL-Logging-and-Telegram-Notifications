package com.example.backEndreportScheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.backEndreportScheduler.dto.response.LocoStatusSummaryResponse;
import com.example.backEndreportScheduler.dto.response.LocoSummaryResponse;
import com.example.backEndreportScheduler.model.LocoStatusSummaryModel;
import com.example.backEndreportScheduler.model.LocomotifSummaryModel;
import com.example.backEndreportScheduler.repository.LocoStatusSummaryRepos;
import com.example.backEndreportScheduler.repository.LocomotifSummaryRepos;

@Service
public class LocoService {
    private final LocomotifSummaryRepos summaryRepository;
    private final LocoStatusSummaryRepos statusSummaryRepository;

    public LocoService(LocomotifSummaryRepos summaryRepository, LocoStatusSummaryRepos statusSummaryRepository) {
        this.summaryRepository = summaryRepository;
        this.statusSummaryRepository = statusSummaryRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(LocoService.class);

    public List<LocoSummaryResponse> getDataAll(int page, int size, String orderBy) {
        Pageable pageable;

        if ("Desc".equalsIgnoreCase(orderBy)) {
            pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        } else if ("Asc".equalsIgnoreCase(orderBy)) {
            pageable = PageRequest.of(page, size, Sort.by("updatedAt").ascending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<LocomotifSummaryModel> summaryLocosPage = summaryRepository.findAll(pageable);
        List<LocomotifSummaryModel> summaryLocos = summaryLocosPage.getContent();

        List<LocoSummaryResponse> summaryResponses = new ArrayList<>();

        for (LocomotifSummaryModel summaryLoco : summaryLocos) {
            LocoSummaryResponse summaryResponse = new LocoSummaryResponse(
                summaryLoco.getId(),
                summaryLoco.getTotalLocomotive(),
                summaryLoco.getActive(),
                summaryLoco.getNotActive(),
                summaryLoco.getMaintenance(),
                summaryLoco.getUpdatedAt()
            );
            summaryResponses.add(summaryResponse);
        }

        logger.info("All Summary Data:\n " + summaryResponses);
        return summaryResponses;
    }
    

    public LocoSummaryResponse getLatest() {
        LocomotifSummaryModel latestSummary = summaryRepository.findFirstByOrderByUpdatedAtDesc();
    
        if (latestSummary != null) {
            logger.info("latest Summary Data by Updated_At:\n " + latestSummary);
            return new LocoSummaryResponse(
                latestSummary.getId(),
                latestSummary.getTotalLocomotive(),
                latestSummary.getActive(),
                latestSummary.getNotActive(),
                latestSummary.getMaintenance(),
                latestSummary.getUpdatedAt()
            );
        } else {
            return null;
        }
    }

    public List<LocoStatusSummaryResponse> getDataAllStatus() {
        List<LocoStatusSummaryModel> summaryLoco = statusSummaryRepository.findAll();
        List<LocoStatusSummaryResponse> statusSummaryResponses = new ArrayList<>();

        for (LocoStatusSummaryModel summaryLocoModel : summaryLoco) {
            LocoStatusSummaryResponse statusSummaryResponse = new LocoStatusSummaryResponse(
                summaryLocoModel.getLocoId().toString(),
                summaryLocoModel.getLocoName(),
                summaryLocoModel.getTotal(),
                summaryLocoModel.getActive(),
                summaryLocoModel.getNotActive(),
                summaryLocoModel.getMaintenance(),
                summaryLocoModel.getUpdatedAt()
            );
            statusSummaryResponses.add(statusSummaryResponse);
        }

        logger.info("All Summary Status Data:\n" + statusSummaryResponses);
        return statusSummaryResponses;
    }
}
