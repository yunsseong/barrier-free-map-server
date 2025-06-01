package com.yunsseong.barrier_free_map_server.building.domain;

import com.yunsseong.barrier_free_map_server.floor.domain.Floor;
import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import com.yunsseong.barrier_free_map_server.common.domain.Coordinate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String number;

    private Boolean wheel;

    private Boolean toilet;

    private Boolean elevator;

    private Boolean dots;

    private Boolean floorplan;

    private String caution;

    @Embedded
    private Coordinate coordinate;

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Floor> floors = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "map_id")
    private BarrierFreeMap map;

    public void addFloor(Floor floor) {
        floors.add(floor);
        floor.setBuilding(this);
    }
}
