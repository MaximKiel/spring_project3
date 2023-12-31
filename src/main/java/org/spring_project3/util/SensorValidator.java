package org.spring_project3.util;

import org.spring_project3.dto.SensorDTO;
import org.spring_project3.models.Sensor;
import org.spring_project3.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;
        if (sensorsService.findByName(sensorDTO.getName()).isPresent()) {
            errors.rejectValue("name", "", "Sensor with that name already exist");
        }
    }
}
