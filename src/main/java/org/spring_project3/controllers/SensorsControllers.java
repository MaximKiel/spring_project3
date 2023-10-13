package org.spring_project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.spring_project3.dto.SensorDTO;
import org.spring_project3.models.Sensor;
import org.spring_project3.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class SensorsControllers {

    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsControllers(SensorsService sensorsService, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // TODO create special exception

            throw new RuntimeException("Sensor registration exception");
        }

        sensorsService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
