package ru.web4back.dto.point;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointRequestDTO {

    @NotNull(message = "X cannot be null")
    @DecimalMin(value = "-3", message = "X must be greater than or equal to -3")
    @DecimalMax(value = "5", message = "X must be less than or equal to 5")
    private Double x;

    @NotNull(message = "Y cannot be null")
    @DecimalMin(value = "-3", message = "Y must be greater than or equal to -3")
    @DecimalMax(value = "5", message = "Y must be less than or equal to 5")
    private Double y;

    @NotNull(message = "R cannot be null")
    @DecimalMin(value = "1", message = "R must be greater than or equal to 1")
    @DecimalMax(value = "5", message = "R must be less than or equal to 5")
    private Double r;

}
