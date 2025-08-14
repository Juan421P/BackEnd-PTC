package ptc2025.backend.Services.employees;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.employees.EmployeeEntity;
import ptc2025.backend.Models.DTO.employees.EmployeeDTO;
import ptc2025.backend.Respositories.employees.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public List<EmployeeDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO insertar(EmployeeDTO dto) {
        if (repository.existsById(dto.getId())) {
            throw new IllegalArgumentException("El empleado ya existe");
        }
        EmployeeEntity entity = convertirAEntity(dto);
        return convertirADTO(repository.save(entity));
    }

    public EmployeeDTO actualizar(String id, EmployeeDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró el empleado con ID: " + id);
        }
        EmployeeEntity entity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Error al acceder al empleado"));
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPosition(dto.getPosition());
        entity.setPhone(dto.getPhone());
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

    private EmployeeEntity convertirAEntity(EmployeeDTO dto) {
        return new EmployeeEntity(
                dto.getId(),
                dto.getFullName(),
                dto.getEmail(),
                dto.getPosition(),
                dto.getPhone(),
                dto.getIsActive()
        );
    }

    private EmployeeDTO convertirADTO(EmployeeEntity entity) {
        return new EmployeeDTO(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPosition(),
                entity.getPhone(),
                entity.getIsActive()
        );
    }
}

