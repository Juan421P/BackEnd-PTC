package ptc2025.backend.Services.cyclicStudentPerformances;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.cyclicStudentPerformances.CyclicStudentPerformanceEntity;
import ptc2025.backend.Models.DTO.cyclicStudentPerformances.CyclicStudentPerformanceDTO;
import ptc2025.backend.Respositories.cyclicStudentPerformaces.CyclicStudentPerformanceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CyclicStudentPerformanceService {

    @Autowired
    private CyclicStudentPerformanceRepository repository;

    public List<CyclicStudentPerformanceDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CyclicStudentPerformanceDTO insertar(CyclicStudentPerformanceDTO dto) {
        if (repository.existsById(dto.getId())) {
            throw new IllegalArgumentException("Ya existe un rendimiento con ese ID");
        }
        CyclicStudentPerformanceEntity entity = convertirAEntity(dto);
        return convertirADTO(repository.save(entity));
    }

    public CyclicStudentPerformanceDTO actualizar(String id, CyclicStudentPerformanceDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró el rendimiento con ID: " + id);
        }

        CyclicStudentPerformanceEntity entity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Error interno al acceder al rendimiento"));

        entity.setStudentId(dto.getStudentId());
        entity.setCycleCode(dto.getCycleCode());
        entity.setAcademicYearId(dto.getAcademicYearId());
        entity.setAverageGrade(dto.getAverageGrade());
        entity.setPassed(dto.getPassed());
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

    private CyclicStudentPerformanceDTO convertirADTO(CyclicStudentPerformanceEntity entity) {
        return new CyclicStudentPerformanceDTO(
                entity.getId(),
                entity.getStudentId(),
                entity.getCycleCode(),
                entity.getAcademicYearId(),
                entity.getAverageGrade(),
                entity.getPassed(),
                entity.getIsActive()
        );
    }

    private CyclicStudentPerformanceEntity convertirAEntity(CyclicStudentPerformanceDTO dto) {
        return new CyclicStudentPerformanceEntity(
                dto.getId(),
                dto.getStudentId(),
                dto.getCycleCode(),
                dto.getAcademicYearId(),
                dto.getAverageGrade(),
                dto.getPassed(),
                dto.getIsActive()
        );
    }
}
