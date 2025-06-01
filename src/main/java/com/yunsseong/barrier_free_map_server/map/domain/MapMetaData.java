package com.yunsseong.barrier_free_map_server.map.domain;

import com.yunsseong.barrier_free_map_server.common.domain.Coordinate;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class MapMetaData {

    @Embedded
    private Coordinate centralCoordinate;
}
