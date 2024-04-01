package com.example.backEndreportScheduler.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backEndreportScheduler.dto.response.LocoStatusSummaryResponse;
import com.example.backEndreportScheduler.dto.response.LocoSummaryResponse;
import com.example.backEndreportScheduler.service.LocoService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/summary")
public class LocoSummaryController {

    private final LocoService locoService;

    public LocoSummaryController(LocoService locoService) {
        this.locoService = locoService;
    }

    Logger logger = LoggerFactory.getLogger(LocoSummaryController.class);

    @GetMapping("/all")
    public ResponseEntity<List<LocoSummaryResponse>> getAllData(
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        
        logger.info("Getting all data in summary_loco Table -----------------------------------");

        List<LocoSummaryResponse> allSummaryData;

        if (orderBy != null && !orderBy.isEmpty()) {
            allSummaryData = locoService.getDataAll(page, size, orderBy);
        } else {
            allSummaryData = locoService.getDataAll(page, size, "Default");
        }

        return ResponseEntity.ok(allSummaryData);
    }

    
    @GetMapping("/latest")
    public ResponseEntity<LocoSummaryResponse> getLatest() {
        LocoSummaryResponse latestSummary = locoService.getLatest();
        if (latestSummary != null) {
            logger.info("Getting latest data in summary_loco Table  -----------------------------------");
            return ResponseEntity.ok(latestSummary);
        } else {
            logger.info("No latest data in summary_loco Table  -----------------------------------");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allstatus")
    public ResponseEntity<List<LocoStatusSummaryResponse>> getAllDataStatus(){
        List<LocoStatusSummaryResponse> allStatusData = locoService.getDataAllStatus(); 
        try {
            logger.info("Getting all status data in summary_loco_status Table -----------------------------------");
            return ResponseEntity.ok(allStatusData);
        } catch (Exception e) {
            logger.info("No data in summary_loco_status Table  -----------------------------------");
            return ResponseEntity.notFound().build();
        }
    }
}