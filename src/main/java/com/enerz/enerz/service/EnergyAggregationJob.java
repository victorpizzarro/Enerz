package com.enerz.enerz.service;

import com.enerz.enerz.model.Device;
import com.enerz.enerz.model.EnergyData;
import com.enerz.enerz.model.EnergySummary;
import com.enerz.enerz.repository.EnergyDataRepository;
import com.enerz.enerz.repository.EnergySummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnergyAggregationJob {

    private final EnergyDataRepository energyDataRepository;
    private final EnergySummaryRepository energySummaryRepository;

    // Run every hour at minute 0
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void aggregateEnergyData() {
        log.info("Starting Energy Data Aggregation Job");

        List<EnergyData> unprocessedData = energyDataRepository.findByProcessedFalse();
        if (unprocessedData.isEmpty()) {
            log.info("No unprocessed energy data found. Job finished.");
            return;
        }

        Map<Device, List<EnergyData>> dataByDevice = unprocessedData.stream()
                .collect(Collectors.groupingBy(EnergyData::getDevice));

        for (Map.Entry<Device, List<EnergyData>> entry : dataByDevice.entrySet()) {
            Device device = entry.getKey();
            List<EnergyData> measurements = entry.getValue();

            try {
                processDeviceMeasurements(device, measurements);
            } catch (Exception e) {
                log.error("Error processing data for Device ID: {}. Skipping to next...", device.getId(), e);
            }
        }

        log.info("Energy Data Aggregation Job completed successfully.");
    }

    private void processDeviceMeasurements(Device device, List<EnergyData> measurements) {
        // Group by Date for the specific device in case the batch contains data for multiple days
        Map<LocalDate, List<EnergyData>> dataByDate = measurements.stream()
                .collect(Collectors.groupingBy(data -> 
                        data.getTimestamp().atZone(ZoneId.systemDefault()).toLocalDate()));

        for (Map.Entry<LocalDate, List<EnergyData>> dateEntry : dataByDate.entrySet()) {
            LocalDate date = dateEntry.getKey();
            List<EnergyData> dateMeasurements = dateEntry.getValue();

            double addedKwh = dateMeasurements.stream()
                    .mapToDouble(EnergyData::getKwh)
                    .sum();

            EnergySummary summary = energySummaryRepository.findByDeviceAndDate(device, date)
                    .orElse(EnergySummary.builder()
                            .device(device)
                            .date(date)
                            .totalKwh(0.0)
                            .baselineKwh(20.0) // Fictional business rule
                            .savedKwh(0.0)
                            .efficiency(0.0)
                            .co2Saved(0.0)
                            .tokensGenerated(0.0)
                            .build());

            // Add the newly processed consumption
            summary.setTotalKwh(summary.getTotalKwh() + addedKwh);

            // Calculate saving (minimum 0)
            double saved = summary.getBaselineKwh() - summary.getTotalKwh();
            summary.setSavedKwh(Math.max(0, saved));

            // Efficiency simply as percentage of baseline
            double efficiency = (summary.getTotalKwh() / summary.getBaselineKwh()) * 100;
            summary.setEfficiency(efficiency);

            // co2Saved = savedKwh * 0.085
            summary.setCo2Saved(summary.getSavedKwh() * 0.085);

            // tokensGenerated = savedKwh * 2
            summary.setTokensGenerated(summary.getSavedKwh() * 2);

            energySummaryRepository.save(summary);

            // Mark these as processed
            dateMeasurements.forEach(data -> data.setProcessed(true));
            energyDataRepository.saveAll(dateMeasurements);
        }
    }
}
