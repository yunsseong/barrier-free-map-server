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

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapService {

    private final MapRepository mapRepository;
    private final MemberService memberService;

    @Value("${front.url}")
    private String frontUrl;

    @Transactional
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

    public BarrierFreeMap queryMap(String nickname) {
        BarrierFreeMap map = mapRepository.findByNickname(nickname)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_INPUT));
        if (map.getStatus().equals(MapStatus.STOPPED)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }
        return map;
    }

    @Transactional
    public void updateMap(Long mapId, UpdateMapRequest updateMapRequest, Long memberId) {
        BarrierFreeMap map = getMapEntity(mapId, memberId);
        BarrierFreeMap updatedMap = MapMapper.toUpdatedMap(map, updateMapRequest);
        mapRepository.save(updatedMap);
    }

    @Transactional
    public void deleteMap(Long mapId, Long memberId) {
        BarrierFreeMap map = getMapEntity(mapId, memberId);
        mapRepository.delete(map);
    }

    public List<BarrierFreeMap> getAllMapEntity(Long memberId) {
        List<BarrierFreeMap> maps = mapRepository.findAllByOwnerId(memberId);
        if (!maps.isEmpty() && !maps.getFirst().getOwner().getId().equals(memberId)){
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }
        return maps;
    }

    public List<MapResponse> getAllMaps(Long memberId) {
        List<BarrierFreeMap> maps = mapRepository.findAllByOwnerId(memberId);
        if (!maps.getFirst().getOwner().getId().equals(memberId)){
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }
        return maps.stream()
                .map(map -> MapMapper.toMapResponse(map, frontUrl))
                .toList();
    }

    public BarrierFreeMap getMapByCode(String code) {
        return mapRepository.findByNickname(code)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_INPUT));
    }

    @Transactional
    public void publishMap(Long mapId, Long memberId) {
        BarrierFreeMap map = getMapEntity(mapId, memberId);
        map.publish();
        mapRepository.save(map);
    }

    @Transactional
    public void unpublishMap(Long mapId, Long memberId) {
        BarrierFreeMap map = getMapEntity(mapId, memberId);
        map.unpublish();
        mapRepository.save(map);
    }
}
