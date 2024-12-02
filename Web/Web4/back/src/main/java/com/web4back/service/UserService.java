package com.web4back.service;

import com.web4back.dao.PointDAO;
import com.web4back.dao.UserDAO;
import com.web4back.dto.PointRequestDTO;
import com.web4back.dto.PointResponseDTO;
import com.web4back.entity.PointEntity;
import com.web4back.entity.UserEntity;
import com.web4back.exception.UserNotFoundException;
import com.web4back.util.AreaChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final PointDAO pointDAO;
    private final AreaChecker areaChecker;
    private final UserDAO userDAO;

    @Autowired
    public UserService(PointDAO pointDAO, AreaChecker areaChecker, UserDAO userDAO) {
        this.pointDAO = pointDAO;
        this.areaChecker = areaChecker;
        this.userDAO = userDAO;
    }

    public List<PointResponseDTO> getAllPoints(Long userId) {
        return pointDAO.findByUserId(userId).stream()
                .map(PointResponseDTO::new)
                .toList();
    }

    public PointResponseDTO addPoint(PointRequestDTO point, Long userId) throws UserNotFoundException {
        UserEntity user = userDAO
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " does not exist"));

        PointResponseDTO response = areaChecker.checkPoint(point);

        PointEntity pointEntity = PointEntity.builder()
                .x(response.getX())
                .y(response.getY())
                .r(response.getR())
                .hit(response.isHit())
                .exec(response.getExec())
                .user(user)
                .build();

        pointDAO.save(pointEntity);
        return response;
    }
}
