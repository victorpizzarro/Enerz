package com.enerz.enerz.repository;

import com.enerz.enerz.model.Device;
import com.enerz.enerz.model.EnergySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnergySummaryRepository extends JpaRepository<EnergySummary, UUID> {
    Optional<EnergySummary> findByDeviceAndDate(Device device, LocalDate date);
}
