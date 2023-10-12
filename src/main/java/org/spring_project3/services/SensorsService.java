package org.spring_project3.services;

import org.spring_project3.models.Sensor;
import org.spring_project3.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    public Optional<Sensor> findById(int id) {
        return sensorsRepository.findById(id);
    }

    public Optional<Sensor> findByName(String name) {
        return Optional.ofNullable(sensorsRepository.findByName(name));
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    @Transactional
    public void update(int id, Sensor updatedSensor) {
        updatedSensor.setId(id);
        sensorsRepository.save(updatedSensor);
    }

    @Transactional
    public void delete(int id) {
        sensorsRepository.deleteById(id);
    }
}
