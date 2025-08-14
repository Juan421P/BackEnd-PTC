package ptc2025.backend.Services.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.documents.DocumentEntity;
import ptc2025.backend.Models.DTO.documents.DocumentDTO;
import ptc2025.backend.Respositories.documents.DocumentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@Slf4j
public class DocumentService {

    @Autowired
    private DocumentRepository repository;

    public List<DocumentDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public DocumentDTO insertar(DocumentDTO dto) {
        if(repository.existsById(dto.getId())) {
            throw new IllegalArgumentException("El documento ya existe");
        }
        DocumentEntity entity = convertirAEntity(dto);
        return convertirADTO(repository.save(entity));
    }

    public DocumentDTO actualizar(String id, DocumentDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró el documento con ID: " + id);
        }
        DocumentEntity entity = repository.findById(id).orElseThrow(() ->
                new RuntimeException("Error interno al acceder al documento"));
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setUploadDate(dto.getUploadDate());
        entity.setOwnerId(dto.getOwnerId());
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

    private DocumentDTO convertirADTO(DocumentEntity entity) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setUploadDate(entity.getUploadDate());
        dto.setOwnerId(entity.getOwnerId());
        dto.setIsActive(entity.getIsActive());
        return dto;
    }

    private DocumentEntity convertirAEntity(DocumentDTO dto) {
        DocumentEntity entity = new DocumentEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setUploadDate(dto.getUploadDate());
        entity.setOwnerId(dto.getOwnerId());
        entity.setIsActive(dto.getIsActive());
        return entity;
    }
}
