package ptc2025.backend.Services.courseEnrollments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.courseEnrollments.CourseEnrollmentEntity;
import ptc2025.backend.Models.DTO.courseEnrollments.CourseEnrollmentDTO;
import ptc2025.backend.Respositories.courseEnrollments.CourseEnrollmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseEnrollmentService {

    @Autowired
    private CourseEnrollmentRepository repository;

    public List<CourseEnrollmentDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public CourseEnrollmentDTO insertar(CourseEnrollmentDTO dto) {
        if (repository.existsById(dto.getId())) {
            throw new IllegalArgumentException("La inscripción ya existe");
        }
        CourseEnrollmentEntity entity = convertirAEntity(dto);
        return convertirADTO(repository.save(entity));
    }

    public CourseEnrollmentDTO actualizar(String id, CourseEnrollmentDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró la inscripción con ID: " + id);
        }

        CourseEnrollmentEntity entity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Error interno al acceder a la inscripción"));

        entity.setStudentId(dto.getStudentId());
        entity.setOfferingId(dto.getOfferingId());
        entity.setEnrollmentDate(dto.getEnrollmentDate());
        entity.setGrade(dto.getGrade());
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

    private CourseEnrollmentDTO convertirADTO(CourseEnrollmentEntity entity) {
        return new CourseEnrollmentDTO(
                entity.getId(),
                entity.getStudentId(),
                entity.getOfferingId(),
                entity.getEnrollmentDate(),
                entity.getGrade(),
                entity.getIsActive()
        );
    }

    private CourseEnrollmentEntity convertirAEntity(CourseEnrollmentDTO dto) {
        return new CourseEnrollmentEntity(
                dto.getId(),
                dto.getStudentId(),
                dto.getOfferingId(),
                dto.getEnrollmentDate(),
                dto.getGrade(),
                dto.getIsActive()
        );
    }
}
