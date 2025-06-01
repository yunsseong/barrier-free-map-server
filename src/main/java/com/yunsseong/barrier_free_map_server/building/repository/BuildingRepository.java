package com.yunsseong.barrier_free_map_server.building.repository;

import com.yunsseong.barrier_free_map_server.building.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
