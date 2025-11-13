package com.xguerrerov.venues.Service.Impl;

import com.xguerrerov.venues.DTO.VenueDTO;
import com.xguerrerov.venues.Entity.EventEntity;
import com.xguerrerov.venues.Entity.VenueEntity;
import com.xguerrerov.venues.Mapper.VenueMapper;
import com.xguerrerov.venues.Repository.Interface.IVenueRepository;
import com.xguerrerov.venues.Service.Interface.IVenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenueService implements IVenueService {

    private  final IVenueRepository repository;
    private final VenueMapper mapper;

    @Override
    public VenueDTO create(VenueDTO venueDTO) {
        VenueEntity entity = mapper.toEntity(venueDTO);
        VenueEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public List<VenueDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VenueDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Venue not found (404)"));
    }

    @Override
    public VenueDTO update(Long id, VenueDTO venueDTO) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found for update (404)"));
        VenueEntity entityToUpdate = mapper.toEntity(venueDTO);
        entityToUpdate.setId(id);
        VenueEntity updatedEntity = repository.save(entityToUpdate);
        return mapper.toDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
