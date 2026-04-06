package com.enerz.enerz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "energy_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(nullable = false)
    private Double kwh;
    
    @Column
    private Double voltage;
    
    @Column
    private Double current;

    @Column(nullable = false)
    private Instant timestamp;
    
    @Column(nullable = false)
    @Builder.Default
    private boolean processed = false;
}
