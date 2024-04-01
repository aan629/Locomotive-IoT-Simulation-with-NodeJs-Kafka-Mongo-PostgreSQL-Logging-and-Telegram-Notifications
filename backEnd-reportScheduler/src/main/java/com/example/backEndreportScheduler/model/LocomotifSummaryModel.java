package com.example.backEndreportScheduler.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "summary_loco")
public class LocomotifSummaryModel {
    @Id
    @Column(columnDefinition = "varchar(36)")
    private String id;

    @Column(name = "active")
    private Integer active;

    @Column(name = "not_active")
    private Integer notActive;

    @Column(name = "maintenance")
    private Integer maintenance;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "total_locomotive")
    private Integer totalLocomotive;
}
