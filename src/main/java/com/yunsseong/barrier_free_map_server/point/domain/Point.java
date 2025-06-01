package com.yunsseong.barrier_free_map_server.point.domain;

import com.yunsseong.barrier_free_map_server.common.domain.Coordinate;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Coordinate coordinate;

    private String memo;

    @Enumerated(EnumType.STRING)
    private PointType type;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "map_id")
    private BarrierFreeMap map;
}
