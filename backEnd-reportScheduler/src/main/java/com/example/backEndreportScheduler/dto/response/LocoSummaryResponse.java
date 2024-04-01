package com.example.backEndreportScheduler.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LocoSummaryResponse {
    private String locoId;
    private int totalLocomotive;
    private int active;
    private int notActive;
    private int maintenance;
    private LocalDateTime updatedAt;
    
    public LocoSummaryResponse(String locoId, int totalLocomotive, int active, int notActive, int maintenance,
            LocalDateTime updatedAt) {
        this.locoId = locoId;
        this.totalLocomotive = totalLocomotive;
        this.active = active;
        this.notActive = notActive;
        this.maintenance = maintenance;
        this.updatedAt = updatedAt;
    }
}
