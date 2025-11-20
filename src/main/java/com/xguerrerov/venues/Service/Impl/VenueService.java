package com.xguerrerov.venues.Service.Impl;

import com.xguerrerov.venues.DTO.VenueDTO;
import com.xguerrerov.venues.Entity.VenueEntity;
import com.xguerrerov.venues.Exception.DuplicateResourceException;
import com.xguerrerov.venues.Exception.NotFoundException;
import com.xguerrerov.venues.Mapper.VenueMapper;
import com.xguerrerov.venues.Repository.Interface.IVenueRepository;
import com.xguerrerov.venues.Service.Interface.IVenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
public class VenueService implements IVenueService {

    private  final IVenueRepository repository;
    private final VenueMapper mapper;

    @Override
    public VenueDTO create(VenueDTO venueDTO) {
        VenueEntity entity = mapper.toEntity(venueDTO);
        if (repository.findByName(entity.getName()).isPresent()) {
            throw new DuplicateResourceException("Venue with name '" + venueDTO.getName() + "' already exists");
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

        VenueEntity existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Venue with id " + id + " not found for update"));
        repository.findByName(venueDTO.getName())
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new DuplicateResourceException(
                            "Venue with name '" + venueDTO.getName() + "' already exists"
                    );
                });
        existing.setName(venueDTO.getName());
        existing.setCity(venueDTO.getCity());
        VenueEntity updated = repository.save(existing);
        return mapper.toDto(updated);
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

    @Override
    public Page<VenueDTO> getAllPaged(Pageable pageable) {
        var page = repository.findAll(pageable);

        if (page.isEmpty()) {
            throw new NotFoundException("No venues found");
        }

        return page.map(mapper::toDto);
    }


}
