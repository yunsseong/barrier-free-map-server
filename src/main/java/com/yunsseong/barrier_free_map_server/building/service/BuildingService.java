package com.yunsseong.barrier_free_map_server.building.service;

import com.yunsseong.barrier_free_map_server.building.domain.Building;
import com.yunsseong.barrier_free_map_server.building.dto.BuildingMapper;
import com.yunsseong.barrier_free_map_server.building.dto.BuildingRequest;
import com.yunsseong.barrier_free_map_server.building.dto.BuildingResponse;
import com.yunsseong.barrier_free_map_server.building.repository.BuildingRepository;
import com.yunsseong.barrier_free_map_server.common.exception.BusinessException;
import com.yunsseong.barrier_free_map_server.common.exception.CommonStatus;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import com.yunsseong.barrier_free_map_server.map.service.MapService;
import com.yunsseong.barrier_free_map_server.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final MapService mapService;

    @Transactional
    public Building createBuilding(Long mapId, BuildingRequest buildingRequest, Long memberId) {
        BarrierFreeMap map = mapService.getMapEntity(mapId, memberId);
        Member owner = map.getOwner();

        if (!owner.getId().equals(memberId)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }

        Building building = BuildingMapper.toBuilding(buildingRequest, map);
        return buildingRepository.save(building);
    }

    public Building getBuildingEntity(Long buildingId) {
        return buildingRepository.findById(buildingId)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_OBJECT));
    }

    public Building getBuilding(Long mapId, Long buildingId, Long memberId) {
        Building building = getBuildingEntity(buildingId);
        if (!building.getMap().getId().equals(mapId)) {
            throw new BusinessException(CommonStatus.INVALID_INPUT);
        }
        if (!building.getMap().getOwner().getId().equals(memberId))
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        return building;
    }

    public List<BuildingResponse> getBuildings(Long mapId, Long memberId) {
        BarrierFreeMap map = mapService.getMapEntity(mapId, memberId);
        return map.getBuildings().stream()
                .map(BuildingMapper::toBuildingResponse)
                .toList();
    }

    @Transactional
    public BuildingResponse updateBuilding(Long mapId, Long buildingId, BuildingRequest request, Long memberId) {
        BarrierFreeMap map = mapService.getMapEntity(mapId, memberId);
        Member owner = map.getOwner();

        if (!owner.getId().equals(memberId)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }

        Building existingBuilding = getBuildingEntity(buildingId);

        if (!existingBuilding.getMap().getId().equals(mapId)) {
            throw new BusinessException(CommonStatus.INVALID_OBJECT);
        }

        Building updatedBuilding = BuildingMapper.toUpdatedBuilding(existingBuilding, request);
        buildingRepository.save(updatedBuilding);

        return BuildingMapper.toBuildingResponse(updatedBuilding);
    }


    @Transactional
    public void deleteBuilding(Long mapId, Long buildingId, Long memberId) {
        BarrierFreeMap map = mapService.getMapEntity(mapId, memberId);
        Member owner = map.getOwner();

        if (!owner.getId().equals(memberId)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }

        Building building = getBuildingEntity(buildingId);

        if (!building.getMap().getId().equals(mapId)) {
            throw new BusinessException(CommonStatus.INVALID_OBJECT);
        }

        buildingRepository.delete(building);
    }
}
