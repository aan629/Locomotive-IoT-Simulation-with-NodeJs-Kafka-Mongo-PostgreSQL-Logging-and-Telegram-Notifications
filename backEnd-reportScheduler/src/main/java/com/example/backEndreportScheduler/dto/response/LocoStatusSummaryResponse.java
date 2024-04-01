package com.example.backEndreportScheduler.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class LocoStatusSummaryResponse {
    UUID locoId;
    String locoName;
    int totalLocomotive;
    int active;
    int notActive;
    int maintenance;
    LocalDateTime updatedAt;
    
    public LocoStatusSummaryResponse(String locoId, String locoName, int totalLocomotive, int active, int notActive, int maintenance,
            LocalDateTime updatedAt) {
                this.locoId = UUID.fromString(locoId);
                this.locoName = locoName;
                this.totalLocomotive = totalLocomotive;
                this.active = active;
                this.notActive = notActive;
                this.maintenance = maintenance;
                this.updatedAt = updatedAt;
    }
}
