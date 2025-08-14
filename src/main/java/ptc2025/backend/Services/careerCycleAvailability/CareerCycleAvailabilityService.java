package ptc2025.backend.Services.careerCycleAvailability;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.careerCycleAvailability.CareerCycleAvailabilityEntity;
import ptc2025.backend.Models.DTO.careerCycleAvailability.CareerCycleAvailabilityDTO;
import ptc2025.backend.Respositories.careerCycleAvailability.CareerCycleAvailabilityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CareerCycleAvailabilityService {

    @Autowired
    private CareerCycleAvailabilityRepository repository;

    public List<CareerCycleAvailabilityDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CareerCycleAvailabilityDTO insertar(CareerCycleAvailabilityDTO dto) {
        if (repository.existsById(dto.getId())) {
            throw new IllegalArgumentException("La disponibilidad ya existe");
        }
        CareerCycleAvailabilityEntity entity = convertirAEntity(dto);
        return convertirADTO(repository.save(entity));
    }

    public CareerCycleAvailabilityDTO actualizar(String id, CareerCycleAvailabilityDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró disponibilidad con ID: " + id);
        }

        CareerCycleAvailabilityEntity entity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Error interno al acceder a la disponibilidad"));

        entity.setCareerId(dto.getCareerId());
        entity.setAcademicYearId(dto.getAcademicYearId());
        entity.setCycleCode(dto.getCycleCode());
        entity.setMaxCapacity(dto.getMaxCapacity());
        entity.setIsActive(dto.getIsActive());

        return convertirADTO(repository.save(entity));
    }

    public boolean eliminar(String id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private CareerCycleAvailabilityDTO convertirADTO(CareerCycleAvailabilityEntity entity) {
        return new CareerCycleAvailabilityDTO(
                entity.getId(),
                entity.getCareerId(),
                entity.getAcademicYearId(),
                entity.getCycleCode(),
                entity.getMaxCapacity(),
                entity.getIsActive()
        );
    }

    private CareerCycleAvailabilityEntity convertirAEntity(CareerCycleAvailabilityDTO dto) {
        return new CareerCycleAvailabilityEntity(
                dto.getId(),
                dto.getCareerId(),
                dto.getAcademicYearId(),
                dto.getCycleCode(),
                dto.getMaxCapacity(),
                dto.getIsActive()
        );
    }
}

