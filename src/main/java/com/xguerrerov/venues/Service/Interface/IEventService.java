package com.xguerrerov.venues.Service.Interface;
import com.xguerrerov.venues.DTO.EventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;



public interface IEventService {
    EventDTO create(EventDTO eventDTO);
    List<EventDTO> getAll();
    EventDTO getById(Long id);
    EventDTO update(Long id, EventDTO eventDTO);
    void delete(Long id);
    List<EventDTO> getEventsByVenueId(Long venueId);
    Page<EventDTO> getEventsByCategory(String category, Pageable pageable);
    List<EventDTO> getEventsByDateBegin(java.time.LocalDate dateBegin);
    Page<EventDTO> getAllPaged(Pageable pageable);
}
