package com.yunsseong.barrier_free_map_server.issue.domain;

import com.yunsseong.barrier_free_map_server.map.domain.BarrierFreeMap;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "map_id")
    private BarrierFreeMap map;

    public static IssueBuilder builder() {
        return new IssueBuilder().createdDate(LocalDateTime.now());
    }
}
