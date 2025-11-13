package com.xguerrerov.venues.Repository.Impl;

import com.xguerrerov.venues.Entity.EventEntity;
import com.xguerrerov.venues.Repository.Interface.IEventRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepositoryH2 implements IEventRepository {

    // Simulación In-Memory: Almacena las EventEntities
    private final List<EventEntity> events = new ArrayList<>();

    // Generador de ID atómico
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public List<EventEntity> findAll() {

        return new ArrayList<>(events);
    }


    @Override
    public Optional<EventEntity> findById(Long id) {
        return events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }


    @Override
    public EventEntity save(EventEntity event) {
        if (event.getId() == null) {
            // CREATE: Asignar nuevo ID y añadir a la lista
            event.setId(nextId.getAndIncrement());
            events.add(event);
        } else {
            // UPDATE: Buscar el objeto existente y actualizar sus campos
            findById(event.getId()).ifPresent(existing -> {
                existing.setName(event.getName());
                existing.setDate(event.getDate());
                existing.setDescription(event.getDescription());
                existing.setVenueId(event.getVenueId()); // Actualiza la FK
            });
        }
        return event;
    }

    /**
     * Elimina una EventEntity por su ID.
     * @param id El ID del evento a eliminar.
     */
    @Override
    public void deleteById(Long id) {
        // Eliminar si el ID coincide
        events.removeIf(e -> e.getId().equals(id));
    }
}