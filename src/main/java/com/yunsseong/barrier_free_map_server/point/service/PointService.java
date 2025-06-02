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
@Transactional(readOnly = true)
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

    public Point getPoint(Long pointId, Long memberId) {
        Point point = pointRepository.findById(pointId)
                .orElseThrow(() -> new BusinessException(CommonStatus.INVALID_OBJECT));
        if (!point.getMap().getOwner().equals(memberId)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }
        return point;
    }

    @Transactional
    public Point updatePoint(Long mapId, Long pointId, PointRequest pointRequest, Long memberId) {
        Point point = getPoint(pointId, memberId);
        point.setCoordinate(pointRequest.coordinate());
        point.setMemo(pointRequest.memo());
        point.setType(pointRequest.type());
        return pointRepository.save(point);
    }

    @Transactional
    public void deletePoint(Long mapId, Long pointId, Long memberId) {
        Point point = getPoint(pointId, memberId);
        pointRepository.delete(point);
    }

    public List<Point> getPoints(Long mapId) {
        return pointRepository.findByMapId(mapId);
    }


    public Point getPointForMap(Long mapId, Long pointId, Long memberId) {
        return getPoint(mapId, memberId);
    }

    public Long getPointCount(Long mapId, Long memberId) {
        List<Point> points = getPoints(mapId);
        if(!points.getFirst().getMap().getOwner().equals(memberId)) {
            throw new BusinessException(CommonStatus.UNAUTHORIZED);
        }
        return points.stream().count();
    }
}
