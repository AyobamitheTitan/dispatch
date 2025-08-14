package com.polarisdigitech.assessment.dispatch.dtos;

import com.polarisdigitech.assessment.dispatch.enums.State;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateBoxDTO {
    @NotEmpty
    @Size(max = 20)
    String txRef;
    @NotNull
    @Max(value = 500, message = "must not be more than 500 grams in weight")
    @Min(value = 0, message = "cannot be negative")
    Double weightLimit;
    @NotNull
    @Max(value = 100)
    @Min(value = 0)
    Integer batteryCapacity;
    @NotNull
    State state;

    public String getTxRef() {
        return txRef;
    }

    public Double getWeightLimit() {
        return weightLimit;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public State getState() {
        return state;
    }

    public CreateBoxDTO(@NotEmpty @Size(max = 20) String txRef,
            @NotNull @Max(value = 500, message = "must not be more than 500 grams in weight") @Min(value = 0, message = "cannot be negative") Double weightLimit,
            @NotNull @Max(100) @Min(0) Integer batteryCapacity, @NotNull State state) {
        this.txRef = txRef;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

}
