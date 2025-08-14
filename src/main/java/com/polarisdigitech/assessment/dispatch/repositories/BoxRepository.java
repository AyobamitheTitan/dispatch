package com.polarisdigitech.assessment.dispatch.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.polarisdigitech.assessment.dispatch.entities.Box;
import com.polarisdigitech.assessment.dispatch.enums.State;

public interface BoxRepository extends CrudRepository<Box, Long> {
    @Query("SELECT b.batteryCapacity FROM Box b WHERE b.id = :id")
    Optional<Integer> findbatteryCapacityById(@Param("id") Long id);

    @Query("SELECT b from Box b where b.state = :state")
    List<Box> findBoxesByState(@Param("state") State state);
}
