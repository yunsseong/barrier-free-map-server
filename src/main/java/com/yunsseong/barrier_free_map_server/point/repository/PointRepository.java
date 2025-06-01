package com.yunsseong.barrier_free_map_server.point.repository;

import com.yunsseong.barrier_free_map_server.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByMapId(Long mapId);
}
