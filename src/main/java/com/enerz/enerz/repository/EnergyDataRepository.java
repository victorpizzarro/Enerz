package com.enerz.enerz.repository;

import com.enerz.enerz.model.EnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnergyDataRepository extends JpaRepository<EnergyData, UUID> {
    List<EnergyData> findByProcessedFalse();
}
