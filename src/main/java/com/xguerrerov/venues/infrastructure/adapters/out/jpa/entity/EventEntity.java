package com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity;

import com.xguerrerov.venues.Entity.VenueEntity;
import com.xguerrerov.venues.Model.Event;
import java.time.LocalDate;

import com.xguerrerov.venues.domain.model.Category;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class EventEntity implements Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDate dateBegin;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    @ToString.Exclude
    private VenueEntity venue;

    @PrePersist
    @PreUpdate
    public void normalizeData() {
        if (this.name != null) this.name = this.name.toUpperCase();
    }

}