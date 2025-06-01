package com.yunsseong.barrier_free_map_server.map.service;

import com.yunsseong.barrier_free_map_server.common.exception.BusinessException;
import com.yunsseong.barrier_free_map_server.common.exception.CommonStatus;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import com.yunsseong.barrier_free_map_server.map.domain.MapStatus;
import com.yunsseong.barrier_free_map_server.map.dto.CreateMapRequest;
import com.yunsseong.barrier_free_map_server.map.dto.MapMapper;
import com.yunsseong.barrier_free_map_server.map.dto.MapResponse;
import com.yunsseong.barrier_free_map_server.map.dto.UpdateMapRequest;
import com.yunsseong.barrier_free_map_server.map.repository.MapRepository;
import com.yunsseong.barrier_free_map_server.member.domain.Member;
import com.yunsseong.barrier_free_map_server.member.service.MemberService;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService {

    private final MapRepository mapRepository;
    private final MemberService memberService;

    @Value("${front.url}")
    private String frontUrl;

    public MapResponse createEmptyMap(CreateMapRequest createMapRequest, Long memberId) {
        Member foundMember = memberService.getMember(memberId);
        BarrierFreeMap map = mapRepository.save(MapMapper.toBarrierFreeMap(createMapRequest, foundMember));
        return MapMapper.toMapResponse(map, frontUrl);
    }

    public MapResponse getMap(Long mapId, Long memberId) {
        return MapMapper.toMapResponse(getMapEntity(mapId, memberId), frontUrl);
    }

    public BarrierFreeMap getMapEntity(Long mapId, Long memberId) {
        return mapRepository.findByIdAndOwnerId(mapId, memberId)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_OBJECT));
    }

    public BarrierFreeMap queryMap(String uuid) {
        BarrierFreeMap map = mapRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_INPUT));
        if (map.getStatus().equals(MapStatus.STOPPED)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }
        return map;
    }

    public void updateMap(Long mapId, UpdateMapRequest updateMapRequest, Long memberId) {
        BarrierFreeMap map = getMapEntity(mapId, memberId);
        BarrierFreeMap updatedMap = MapMapper.toUpdatedMap(map, updateMapRequest);
        mapRepository.save(updatedMap);
    }

    public void deleteMap(Long mapId, Long memberId) {
        BarrierFreeMap map = getMapEntity(mapId, memberId);
        mapRepository.delete(map);
    }

    public List<MapResponse> getAllMaps(Long memberId) {
        List<BarrierFreeMap> maps = mapRepository.findAllByOwnerId(memberId);
        if (maps.isEmpty()) {
            throw new BusinessException(CommonStatus.INVALID_OBJECT);
        }
        if (!maps.getFirst().getOwner().getId().equals(memberId)){
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }
        return maps.stream()
                .map(map -> MapMapper.toMapResponse(map, frontUrl))
                .toList();
    }

    public void publishMap(Long mapId, Long memberId) {
        BarrierFreeMap map = getMapEntity(mapId, memberId);
        map.publish();
        mapRepository.save(map);
    }

    public void unpublishMap(Long mapId, Long memberId) {
        BarrierFreeMap map = getMapEntity(mapId, memberId);
        map.unpublish();
        mapRepository.save(map);
    }
}
