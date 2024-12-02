package com.web4back.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointRequestDTO implements Serializable {

    @NotNull(message = "X cannot be null")
    private Double x;

    @NotNull(message = "Y cannot be null")
    @DecimalMin(value = "-3", message = "Y must be greater than or equal to -3")
    @DecimalMax(value = "5", message = "Y must be less than or equal to 5")
    private Double y;

    @NotNull(message = "R cannot be null")
    @DecimalMin(value = "0", message = "R must be greater than or equal to 0")
    private Double r;

}
