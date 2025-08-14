package com.polarisdigitech.assessment.dispatch.entities;

import java.util.Set;

import com.polarisdigitech.assessment.dispatch.enums.State;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "boxes")
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tx_ref")
    private String txRef;

    @Column(name = "weight_limit")
    @Max(value = 500, message = "must not be more than 500 grams in weight")
    private Double weightLimit;

    @Column(name = "battery_capacity")
    @Max(value = 100)
    @Min(value = 0)
    private Integer batteryCapacity;

    private State state;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> items;

    public Box(String txRef, Double weightLimit, Integer batteryCapacity, State state) {
        this.txRef = txRef;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    

    
    public Box(Long id, String txRef,
            @Max(value = 500, message = "must not be more than 500 grams in weight") Double weightLimit,
            @Max(100) @Min(0) Integer batteryCapacity, State state, Set<Item> items) {
        this.id = id;
        this.txRef = txRef;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.items = items;
    }




    public void setId(Long id) {
        this.id = id;
    }




    public void setTxRef(String txRef) {
        this.txRef = txRef;
    }




    public void setWeightLimit(Double weightLimit) {
        this.weightLimit = weightLimit;
    }




    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }




    public void setState(State state) {
        this.state = state;
    }




    public void setItems(Set<Item> items) {
        this.items = items;
    }




    public Box() {
    }


    public Long getId() {
        return id;
    }

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

    public Set<Item> getItems() {
        return items;
    }

    
}
