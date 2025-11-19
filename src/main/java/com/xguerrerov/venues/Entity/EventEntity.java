package com.xguerrerov.venues.Entity;
import com.xguerrerov.venues.Model.Event;


import java.time.LocalDate;
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

    @Column(nullable = false)
    private Long venueId;

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