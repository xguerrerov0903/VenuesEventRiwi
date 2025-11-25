package com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xguerrerov.venues.domain.model.Category;
import com.xguerrerov.venues.domain.model.State;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDate dateBegin;

    @Column(nullable = false)
    private LocalDate dateEnd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private VenueEntity venue;

    @PrePersist
    @PreUpdate
    public void normalizeData() {
        if (this.name != null) this.name = this.name.toUpperCase();
    }
}
