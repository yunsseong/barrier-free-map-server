package com.yunsseong.barrier_free_map_server.point.service;

import com.yunsseong.barrier_free_map_server.common.exception.BusinessException;
import com.yunsseong.barrier_free_map_server.common.exception.CommonStatus;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import com.yunsseong.barrier_free_map_server.map.service.MapService;
import com.yunsseong.barrier_free_map_server.member.domain.Member;
import com.yunsseong.barrier_free_map_server.point.domain.Point;
import com.yunsseong.barrier_free_map_server.point.domain.dto.PointRequest;
import com.yunsseong.barrier_free_map_server.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final MapService mapService;

    @Transactional
    public Point createPoint(Long mapId, PointRequest pointRequest, Long memberId) {
        BarrierFreeMap map = mapService.getMapEntity(mapId, memberId);
        Member owner = map.getOwner();

        if (!owner.getId().equals(memberId)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }

        Point point = new Point();
        point.setCoordinate(pointRequest.coordinate());
        point.setMemo(pointRequest.memo());
        point.setType(pointRequest.type());
        point.setMap(map);

        return pointRepository.save(point);
    }

    @Transactional(readOnly = true)
    public Point getPoint(Long pointId) {
        return pointRepository.findById(pointId)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_OBJECT));
    }

    @Transactional
    public Point updatePoint(Long mapId, Long pointId, PointRequest pointRequest, Long memberId) {
        BarrierFreeMap map = mapService.getMapEntity(mapId, memberId);
        Member owner = map.getOwner();

        if (!owner.getId().equals(memberId)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }

        Point point = getPoint(pointId);

        if (!point.getMap().getId().equals(mapId)) {
            throw new BusinessException(CommonStatus.INVALID_OBJECT);
        }

        point.setCoordinate(pointRequest.coordinate());
        point.setMemo(pointRequest.memo());
        point.setType(pointRequest.type());

        return pointRepository.save(point);
    }

    @Transactional
    public void deletePoint(Long mapId, Long pointId, Long memberId) {
        BarrierFreeMap map = mapService.getMapEntity(mapId, memberId);
        Member owner = map.getOwner();

        if (!owner.getId().equals(memberId)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }

        Point point = getPoint(pointId);

        if (!point.getMap().getId().equals(mapId)) {
            throw new BusinessException(CommonStatus.INVALID_OBJECT);
        }

        pointRepository.delete(point);
    }

    @Transactional(readOnly = true)
    public List<Point> getPointsByMapId(Long mapId) {
        return pointRepository.findByMapId(mapId);
    }

    @Transactional(readOnly = true)
    public Point getPointForMap(Long mapId, Long pointId) {
        Point point = getPoint(pointId);

        if (!point.getMap().getId().equals(mapId)) {
            throw new BusinessException(CommonStatus.INVALID_OBJECT);
        }

        return point;
    }
}
