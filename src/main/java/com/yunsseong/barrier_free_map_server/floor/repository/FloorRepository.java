package com.yunsseong.barrier_free_map_server.floor.repository;

import com.yunsseong.barrier_free_map_server.floor.domain.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface FloorRepository extends JpaRepository<Floor, Long> {
    List<Floor> findByBuildingId(Long buildingId);
}
