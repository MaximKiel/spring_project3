package org.spring_project3.util;

import org.spring_project3.dto.MeasurementDTO;
import org.spring_project3.models.Measurement;
import org.spring_project3.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
        if (sensorsService.findByName(measurementDTO.getSensor().getName()).isEmpty()) {
            errors.rejectValue("name", "", "Sensor with that name doesn't exist");
        }
    }
}
