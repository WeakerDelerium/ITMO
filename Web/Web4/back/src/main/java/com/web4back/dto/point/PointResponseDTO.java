package ru.web4back.dto.point;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointResponseDTO {
    private Double x;
    private Double y;
    private Double r;
    private Boolean hit;
    private Long exec;
}
