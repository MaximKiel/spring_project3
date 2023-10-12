package org.spring_project3.repositories;

import org.spring_project3.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {

    Sensor findByName(String name);
}
