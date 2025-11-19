package com.xguerrerov.venues.Service.Impl;

import com.xguerrerov.venues.DTO.VenueDTO;
import com.xguerrerov.venues.Entity.VenueEntity;
import com.xguerrerov.venues.Exception.NotFoundException;
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
        if (repository.findByName(entity.getName()).isPresent()) {
            throw new IllegalArgumentException("Venue with name " + entity.getName() + " already exists");
        }
        VenueEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public List<VenueDTO> getAll() {
        List<VenueEntity> venues = repository.findAll();
        if (venues.isEmpty()) {
            throw new NotFoundException("No venues found");
        }
        return venues
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VenueDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Venue with id " + id + " not found"));
    }

    @Override
    public VenueDTO update(Long id, VenueDTO venueDTO) {
        repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Venue with id " + id + " not found for update"));
        VenueEntity entityToUpdate = mapper.toEntity(venueDTO);
        entityToUpdate.setId(id);
        VenueEntity updatedEntity = repository.save(entityToUpdate);
        return mapper.toDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Venue with id " + id + " not found for delete"));
        repository.deleteById(id);
    }

    @Override
    public List<VenueDTO> getVenueByCity(String city){
        List<VenueEntity> venues = repository.findByCity(city);
        if (venues.isEmpty()) {
            throw new NotFoundException("No venues found for city " + city);
        }
        return venues
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
