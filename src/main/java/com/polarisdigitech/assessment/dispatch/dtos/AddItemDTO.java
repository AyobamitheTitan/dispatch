package com.polarisdigitech.assessment.dispatch.dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AddItemDTO {
    @NotEmpty
    @Pattern(regexp = "[0-9A-Za-z_-]+", message = "must contain only letters, numbers, underscores and hyphens")
    String name;
    @NotNull
    Integer weight;
    @NotEmpty
    @Pattern(regexp = "[0-9A-Z_]+", message = "must contain only uppercase letters, numbers and underscores")
    String code;
    @NotNull
    Long boxId;

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getCode() {
        return code;
    }

    public Long getBoxId() {
        return boxId;
    }

    public AddItemDTO(
            @NotEmpty @Pattern(regexp = "[0-9A-Za-z_-]+", message = "must contain only letters, numbers, underscores and hyphens") String name,
            @NotNull Integer weight,
            @NotEmpty @Pattern(regexp = "[0-9A-Z_]+", message = "must contain only uppercase letters, numbers and underscores") String code,
            @NotNull Long boxId) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.boxId = boxId;
    }

    
}
