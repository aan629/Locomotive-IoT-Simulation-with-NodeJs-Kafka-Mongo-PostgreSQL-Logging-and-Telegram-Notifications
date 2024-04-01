package com.example.backEndreportScheduler.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "summary_loco_status")
public class LocoStatusSummaryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loco_id")
    private UUID locoId;

    @Column(name = "loco_name")
    private String locoName;

    @Column(name = "active")
    private Integer active;

    @Column(name = "not_active")
    private Integer notActive;

    @Column(name = "maintenance")
    private Integer maintenance;

    @Column(name = "total")
    private Integer total;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;
}
