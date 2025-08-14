package com.polarisdigitech.assessment.dispatch.dtos;

import com.polarisdigitech.assessment.dispatch.entities.Box;
import com.polarisdigitech.assessment.dispatch.enums.State;

public class BoxDisplayDTO extends CreateBoxDTO {
    Long id;

    public BoxDisplayDTO(Long id, 
    Integer batteryCapacity,
    Double weightLimit,
    String txRef,
    State state
    ) {
        super(txRef, weightLimit, batteryCapacity, state);
        this.id = id;
        this.batteryCapacity = batteryCapacity;
        this.txRef = txRef;
        this.weightLimit = weightLimit;
        this.state = state;
    }

    

    public Box toBox(){
        return new Box(txRef, weightLimit, batteryCapacity, state);
    }



    public Long getId() {
        return id;
    }
}
