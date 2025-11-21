package com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity;

import com.xguerrerov.venues.Entity.EventEntity;
import com.xguerrerov.venues.Model.Venue;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "venues")
public class VenueEntity implements Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String city;


    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<EventEntity> events;

    @PrePersist
    @PreUpdate
    public void normalizeData() {
        if (this.name != null) this.name = this.name.toUpperCase();
        if (this.city != null) this.city = this.city.toUpperCase();
    }

}