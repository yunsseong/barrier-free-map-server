package com.yunsseong.barrier_free_map_server.map.domain;

import com.yunsseong.barrier_free_map_server.building.domain.Building;
import com.yunsseong.barrier_free_map_server.common.domain.Coordinate;
import com.yunsseong.barrier_free_map_server.point.domain.Point;
import com.yunsseong.barrier_free_map_server.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BarrierFreeMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String nickname;

    private String description;

    @Enumerated(EnumType.STRING)
    private MapStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Member owner;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @Embedded
    private Coordinate centralCoordinate;

    @Builder.Default
    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Building> buildings = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> points = new ArrayList<>();

    public void addBuilding(Building building){
        buildings.add(building);
        building.setMap(this);
    }

    public void addPoint(Point point) {
        points.add(point);
        point.setMap(this);
    }

    public static BarrierFreeMapBuilder builder() {
        LocalDateTime now = LocalDateTime.now();
        return new BarrierFreeMapBuilder()
                .status(MapStatus.STOPPED)
                .createdDate(now)
                .updatedDate(now);
    }

    public void publish() {
        status = MapStatus.DEPLOYING;
    }

    public void unpublish() {
        status = MapStatus.STOPPED;
    }
}
