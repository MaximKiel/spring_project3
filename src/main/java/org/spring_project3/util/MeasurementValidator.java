package org.spring_project3.util;

import org.spring_project3.dto.MeasurementDTO;
import org.spring_project3.dto.SensorDTO;
import org.spring_project3.models.Measurement;
import org.spring_project3.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        Optional<SensorDTO> sensor = Optional.ofNullable(measurementDTO.getSensor());
        if (sensor.isEmpty()) {
            errors.rejectValue("sensor", "", "Sensor should have a name");
        } else {
            if (sensorsService.findByName(sensor.get().getName()).isEmpty()) {
                errors.rejectValue("sensor", "", "Sensor with that name doesn't exist");
            }
        }
    }
}
