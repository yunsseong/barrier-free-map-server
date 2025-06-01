package com.yunsseong.barrier_free_map_server.map.repository;

import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MapRepository extends JpaRepository<BarrierFreeMap, Long> {
    List<BarrierFreeMap> findAllByOwnerId(Long memberId);

    Optional<BarrierFreeMap> findByIdAndOwnerId(Long mapId, Long memberId);

    Optional<BarrierFreeMap> findByUuid(String uuid);
}
