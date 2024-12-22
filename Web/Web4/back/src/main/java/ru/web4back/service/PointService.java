package ru.web4back.service;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import ru.web4back.dto.point.PointRequestDTO;
import ru.web4back.dto.point.PointResponseDTO;
import ru.web4back.entity.Point;
import ru.web4back.entity.User;
import ru.web4back.repository.PointRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final ModelMapper modelMapper;

    public List<PointResponseDTO> getAll(User user) {
        return pointRepository
                .findAllByUserId(user.getId())
                .stream()
                .map(point -> modelMapper.map(point, PointResponseDTO.class))
                .toList();
    }

    public PointResponseDTO check(User user, PointRequestDTO request) {
        double start = System.nanoTime();

        Double x = request.getX();
        Double y = request.getY();
        Double r = request.getR();

        Boolean hit = isHit(x, y, r);

        double end = System.nanoTime();

        Point point = Point
                .builder()
                .user(user)
                .x(x)
                .y(y)
                .r(r)
                .hit(hit)
                .exec((end - start) / 1000)
                .build();

        pointRepository.save(point);

        return modelMapper.map(point, PointResponseDTO.class);
    }

    private Boolean isHit(Double x, Double y, Double r) {
        return (0 <= x && x <= r) && (0 <= y && y <= r) && (x * x + y * y <= r * r) ||
                (-r <= x && x <= 0) && (-r <= y && y <= 0) && (-r <= x + y) ||
                (0 < x && x <= r) && (-r <= y && y < 0);
    }

}
