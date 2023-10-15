package org.spring_project3.services;

import org.spring_project3.models.Measurement;
import org.spring_project3.models.Sensor;
import org.spring_project3.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    public Optional<Measurement> findById(int id) {
        return measurementsRepository.findById(id);
    }

    public Sensor getSensor(int id) {
        return measurementsRepository.findById(id).map(Measurement::getSensor).orElse(null);
    }

    public long getRainyDaysCount() {
        return findAll().stream().filter(Measurement::isRaining).count();
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    @Transactional
    public void update(int id, Measurement updatedMeasurement) {
        Measurement measurementToUpdate = measurementsRepository.findById(id).get();
        updatedMeasurement.setSensor(measurementToUpdate.getSensor());
        updatedMeasurement.setId(id);
        measurementsRepository.save(updatedMeasurement);
    }

    @Transactional
    public void delete(int id) {
        measurementsRepository.deleteById(id);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setMeasureAt(LocalDateTime.now());
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).orElse(null));
    }
}
