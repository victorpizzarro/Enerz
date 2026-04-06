package com.enerz.enerz.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "energy_summaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double totalKwh;

    @Column(nullable = false)
    private Double baselineKwh;

    @Column(nullable = false)
    private Double savedKwh;

    @Column(nullable = false)
    private Double efficiency;

    @Column(nullable = false)
    private Double co2Saved;

    @Column(nullable = false)
    private Double tokensGenerated;
}
