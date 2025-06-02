package com.yunsseong.barrier_free_map_server.data;

import com.yunsseong.barrier_free_map_server.data.dto.DashResponse;
import com.yunsseong.barrier_free_map_server.data.dto.QueryResponse;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import com.yunsseong.barrier_free_map_server.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final MapService mapService;

    public QueryResponse queryResponse(String nickname) {
        BarrierFreeMap map = mapService.queryMap(nickname);
        return QueryMapper.toQueryResponse(map);
    }

    public DashResponse queryDash(Long memberId) {
        DashResponse dashResponse = new DashResponse();
        List<BarrierFreeMap> maps = mapService.getAllMapEntity(memberId);
        dashResponse.addMapCount(maps.stream().count());
        for (BarrierFreeMap map : maps) {
            dashResponse.addBuildingCount(map.getBuildings().stream().count());
            dashResponse.addPointCount(map.getPoints().stream().count());
            dashResponse.addIssueCount(map.getIssues().stream().count());
        }
        return dashResponse;
    }
}
