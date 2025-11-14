package com.xguerrerov.venues.Service.Interface;
import com.xguerrerov.venues.DTO.EventDTO;
import java.util.List;


public interface IEventService {
    EventDTO create(EventDTO eventDTO);
    List<EventDTO> findAll();
    EventDTO findById(Long id);
    EventDTO update(Long id, EventDTO eventDTO);
    void delete(Long id);
}
