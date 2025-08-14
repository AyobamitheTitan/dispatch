package com.polarisdigitech.assessment.dispatch.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;

@Entity
@Table(name = "items")

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Max(value = 500, message = "must be less than 500 grams in weight")
    private Integer weight;

    private String code;

    @ManyToOne
    @JoinColumn(name = "box_id", referencedColumnName = "id")
    private Box box;

    public Item(String name,
            @Max(value = 500, message = "must be less than 500 grams in weight") Integer weight, String code,
            Box box) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.box = box;
    }

    

    public Item() {
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getCode() {
        return code;
    }

    public Box getBox() {
        return box;
    }

}
