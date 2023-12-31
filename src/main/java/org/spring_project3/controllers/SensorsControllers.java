package org.spring_project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.spring_project3.dto.SensorDTO;
import org.spring_project3.models.Sensor;
import org.spring_project3.services.SensorsService;
import org.spring_project3.util.errors.SensorErrorResponse;
import org.spring_project3.util.exceptions.SensorNotRegisteredException;
import org.spring_project3.util.SensorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsControllers {

    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsControllers(SensorsService sensorsService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult bindingResult) {

        sensorValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new SensorNotRegisteredException(errorMessage.toString());
        }

        sensorsService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotRegisteredException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
