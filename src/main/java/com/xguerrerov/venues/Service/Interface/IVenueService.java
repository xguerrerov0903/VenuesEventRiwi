package com.xguerrerov.venues.Service.Interface;
import com.xguerrerov.venues.DTO.VenueDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVenueService {
    VenueDTO create(VenueDTO venueDTO);
    List<VenueDTO> getAll();
    VenueDTO getById(Long id);
    VenueDTO update(Long id, VenueDTO venueDTO);
    void delete(Long id);
    List<VenueDTO> getVenueByCity(String city);
    Page<VenueDTO> getAllPaged(Pageable pageable);

}
