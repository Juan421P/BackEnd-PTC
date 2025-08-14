package ptc2025.backend.Services.careerSocialServiceProjects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.careerSocialServiceProjects.CareerSocialServiceProjectEntity;
import ptc2025.backend.Models.DTO.careerSocialServiceProjects.CareerSocialServiceProjectDTO;
import ptc2025.backend.Respositories.careerSocialServiceProjects.CareerSocialServiceProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CareerSocialServiceProjectService {

    @Autowired
    private CareerSocialServiceProjectRepository repository;

    public List<CareerSocialServiceProjectDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CareerSocialServiceProjectDTO insertar(CareerSocialServiceProjectDTO dto) {
        if (repository.existsById(dto.getId())) {
            throw new IllegalArgumentException("El proyecto ya existe");
        }
        CareerSocialServiceProjectEntity entity = convertirAEntity(dto);
        return convertirADTO(repository.save(entity));
    }

    public CareerSocialServiceProjectDTO actualizar(String id, CareerSocialServiceProjectDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró el proyecto con ID: " + id);
        }

        CareerSocialServiceProjectEntity entity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Error al acceder al proyecto"));

        entity.setCareerId(dto.getCareerId());
        entity.setProjectName(dto.getProjectName());
        entity.setSupervisorName(dto.getSupervisorName());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
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

    private CareerSocialServiceProjectEntity convertirAEntity(CareerSocialServiceProjectDTO dto) {
        return new CareerSocialServiceProjectEntity(
                dto.getId(),
                dto.getCareerId(),
                dto.getProjectName(),
                dto.getSupervisorName(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getIsActive()
        );
    }

    private CareerSocialServiceProjectDTO convertirADTO(CareerSocialServiceProjectEntity entity) {
        return new CareerSocialServiceProjectDTO(
                entity.getId(),
                entity.getCareerId(),
                entity.getProjectName(),
                entity.getSupervisorName(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getIsActive()
        );
    }
}
