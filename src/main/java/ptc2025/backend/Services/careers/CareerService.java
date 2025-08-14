package ptc2025.backend.Services.careers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.careers.CareerEntity;
import ptc2025.backend.Models.DTO.careers.CareerDTO;
import ptc2025.backend.Respositories.careers.CareerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CareerService {

    @Autowired
    private CareerRepository repository;

    public List<CareerDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CareerDTO insertar(CareerDTO dto) {
        if (repository.existsById(dto.getId())) {
            throw new IllegalArgumentException("La carrera ya existe");
        }
        CareerEntity entity = convertirAEntity(dto);
        return convertirADTO(repository.save(entity));
    }

    public CareerDTO actualizar(String id, CareerDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró la carrera con ID: " + id);
        }
        CareerEntity entity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Error interno al acceder a la carrera"));

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setFacultyId(dto.getFacultyId());
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

    private CareerDTO convertirADTO(CareerEntity entity) {
        return new CareerDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getFacultyId(),
                entity.getIsActive()
        );
    }

    private CareerEntity convertirAEntity(CareerDTO dto) {
        return new CareerEntity(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getFacultyId(),
                dto.getIsActive()
        );
    }
}

