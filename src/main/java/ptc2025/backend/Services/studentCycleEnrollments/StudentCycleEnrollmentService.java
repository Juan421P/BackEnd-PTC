package ptc2025.backend.Services.studentCycleEnrollments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.studentCycleEnrollments.StudentCycleEnrollmentEntity;
import ptc2025.backend.Models.DTO.studentCycleEnrollments.StudentCycleEnrollmentDTO;
import ptc2025.backend.Respositories.studentCycleEnrollments.StudentCycleEnrollmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentCycleEnrollmentService {

    @Autowired
    private StudentCycleEnrollmentRepository repository;

    public List<StudentCycleEnrollmentDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public StudentCycleEnrollmentDTO insertar(StudentCycleEnrollmentDTO dto) {
        if (repository.existsById(dto.getId())) {
            throw new IllegalArgumentException("Ya existe una inscripción con ese ID");
        }
        StudentCycleEnrollmentEntity entity = convertirAEntity(dto);
        return convertirADTO(repository.save(entity));
    }

    public StudentCycleEnrollmentDTO actualizar(String id, StudentCycleEnrollmentDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró inscripción con ID: " + id);
        }

        StudentCycleEnrollmentEntity entity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Error al acceder a la inscripción"));

        entity.setStudentId(dto.getStudentId());
        entity.setCycleId(dto.getCycleId());
        entity.setEnrollmentDate(dto.getEnrollmentDate());
        entity.setStatus(dto.getStatus());
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

    private StudentCycleEnrollmentDTO convertirADTO(StudentCycleEnrollmentEntity entity) {
        return new StudentCycleEnrollmentDTO(
                entity.getId(),
                entity.getStudentId(),
                entity.getCycleId(),
                entity.getEnrollmentDate(),
                entity.getStatus(),
                entity.getIsActive()
        );
    }

    private StudentCycleEnrollmentEntity convertirAEntity(StudentCycleEnrollmentDTO dto) {
        return new StudentCycleEnrollmentEntity(
                dto.getId(),
                dto.getStudentId(),
                dto.getCycleId(),
                dto.getEnrollmentDate(),
                dto.getStatus(),
                dto.getIsActive()
        );
    }
}
