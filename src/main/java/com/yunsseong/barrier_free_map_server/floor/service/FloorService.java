package com.yunsseong.barrier_free_map_server.floor.service;

import com.yunsseong.barrier_free_map_server.building.domain.Building;
import com.yunsseong.barrier_free_map_server.building.service.BuildingService;
import com.yunsseong.barrier_free_map_server.common.exception.BusinessException;
import com.yunsseong.barrier_free_map_server.common.exception.CommonStatus;
import com.yunsseong.barrier_free_map_server.floor.domain.Floor;
import com.yunsseong.barrier_free_map_server.floor.dto.CreateFloorRequest;
import com.yunsseong.barrier_free_map_server.floor.dto.FloorMapper;
import com.yunsseong.barrier_free_map_server.floor.dto.FloorResponse;
import com.yunsseong.barrier_free_map_server.floor.dto.UpdateFloorRequest;
import com.yunsseong.barrier_free_map_server.floor.repository.FloorRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FloorService {

    private final BuildingService buildingService;
    private final FloorRepository floorRepository;

    @Value("${aws.s3.publicUrl}")
    private String publicUrl;

    @Transactional
    public void createFloors(List<CreateFloorRequest> requests, Long buildingId, Long memberId) {
        Building building = buildingService.getBuildingEntity(buildingId);
        if (!building.getMap().getOwner().getId().equals(memberId)) {
            throw new BusinessException(CommonStatus.INVALID_INPUT);
        }
        List<Floor> createdFloors = requests.stream()
                .map(request -> Floor.builder()
                        .floorLabel(request.floorLabel())
                        .idx(request.idx())
                        .planImageUrl(publicUrl + "/" + request.fileName())
                        .building(building)
                        .build()
                ).toList();
        building.getFloors().clear();
        building.getFloors().addAll(createdFloors);
        floorRepository.saveAll(createdFloors);
    }

    @Transactional
    public void updateFloors(List<UpdateFloorRequest> requests, Long buildingId, Long memberId) {
        Building building = buildingService.getBuildingEntity(buildingId);
        if (!building.getMap().getOwner().getId().equals(memberId)) {
            throw new BusinessException(CommonStatus.INVALID_INPUT);
        }
        List<Floor> updatedFloors = requests.stream()
                .map(request -> Floor.builder()
                        .floorLabel(request.floorLabel())
                        .idx(request.idx())
                        .building(building)
                        .planImageUrl(publicUrl + "/" + request.fileName())
                        .build()
                ).toList();
        building.getFloors().clear();
        building.getFloors().addAll(updatedFloors);
        floorRepository.saveAll(updatedFloors);
    }

    public List<FloorResponse> getFloors(Long buildingId) {
        return floorRepository.findByBuildingId(buildingId)
                .stream()
                .map(FloorMapper::toFloorResponse)
                .toList();
    }

    public Floor findById(Long id) {
        return floorRepository.findById(id)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_INPUT));
    }

    @Transactional
    public void deleteFloor(Long buildingId, Long floorId, Long memberId) {
        Building building = buildingService.getBuildingEntity(buildingId);
        if (!building.getMap().getOwner().getId().equals(memberId)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }

        Floor floor = findById(floorId);

        if (!floor.getBuilding().getId().equals(buildingId)) {
            throw new BusinessException(CommonStatus.INVALID_OBJECT);
        }

        floorRepository.delete(floor);
    }
}
