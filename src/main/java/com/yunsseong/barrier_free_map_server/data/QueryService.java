package com.yunsseong.barrier_free_map_server.data;

import com.yunsseong.barrier_free_map_server.data.dto.QueryResponse;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import com.yunsseong.barrier_free_map_server.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final MapService mapService;

    public QueryResponse queryResponse(String uuid) {
        BarrierFreeMap map = mapService.queryMap(uuid);
        return QueryMapper.toQueryResponse(map);
    }
}
