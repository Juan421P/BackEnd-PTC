package ptc2025.backend.Services.DocumentCategories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.DocumentCategories.DocumentCategoriesEntity;
import ptc2025.backend.Entities.Universities.UniversityEntity;
import ptc2025.backend.Models.DTO.DocumentCategories.DocumentCategoriesDTO;
import ptc2025.backend.Respositories.DocumentCategories.DocumentCategoriesRepository;
import ptc2025.backend.Respositories.Universities.UniversityRespository;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class DocumentCategoriesService {

    @Autowired
    private DocumentCategoriesRepository repo;
    @Autowired
    private UniversityRespository repoUniversity;

    public List<DocumentCategoriesDTO> getDocumentCategories() {
        List<DocumentCategoriesEntity> documentCtype = repo.findAll();
        return documentCtype.stream()
                .map(this::ConvertDocumentCategoriesDTO)
                .collect(Collectors.toList());
    }

    public DocumentCategoriesDTO ConvertDocumentCategoriesDTO(DocumentCategoriesEntity documentType){
        DocumentCategoriesDTO dto = new DocumentCategoriesDTO();
        dto.setId(documentType.getId());
        dto.setDocumentCategory(documentType.getDocumentCategory());
        if(documentType.getUniversity() != null){
            dto.setUniversityName(documentType.getUniversity().getUniversityName());
            dto.setUniversityID(documentType.getUniversity().getUniversityID());
        }else {
            dto.setUniversityName("Sin Universidad Asignada");
            dto.setUniversityID(null);
        }
        return dto;
    }
    private DocumentCategoriesEntity convertirAEntity(DocumentCategoriesDTO data) {
        DocumentCategoriesEntity entity = new DocumentCategoriesEntity();
        entity.setDocumentCategory(data.getDocumentCategory());
        if(data.getUniversityID() != null){
            UniversityEntity university = repoUniversity.findById(data.getUniversityID())
                    .orElseThrow(() -> new IllegalArgumentException("Universidad no encontrada con ID: " + data.getUniversityID()));
            entity.setUniversity(university);
        }
        return entity;
    }

    public DocumentCategoriesDTO insertarDatos(DocumentCategoriesDTO data) {
        if (data == null){
            throw new IllegalArgumentException("No pueden haber campos nulos");
        }
        try{
            DocumentCategoriesEntity entity = convertirAEntity(data);
            DocumentCategoriesEntity documentCategorieGuardada = repo.save(entity);
            return ConvertDocumentCategoriesDTO(documentCategorieGuardada);
        }catch (Exception e){
            log.error("Error al nuevo documentcategory" + e.getMessage());
            throw new RuntimeException("Error al ingresar el nuevo documentCategory");
        }
    }


    public DocumentCategoriesDTO actualizarDocumentCategory(String id, DocumentCategoriesDTO json) {
        DocumentCategoriesEntity existente = repo.findById(id).orElseThrow(() -> new RuntimeException("No se encontro el DocumentCategory"));
        existente.setDocumentCategory(json.getDocumentCategory());
        if(json.getUniversityID() != null){
            UniversityEntity university = repoUniversity.findById(json.getUniversityID())
                    .orElseThrow(() -> new IllegalArgumentException("Universidad no encontrada con ID: " + json.getUniversityID()));
            existente.setUniversity(university);
        }else {
            existente.setUniversity(null);
        }
        DocumentCategoriesEntity documentCategoryActualizada = repo.save(existente);
        return ConvertDocumentCategoriesDTO(documentCategoryActualizada);
    }

    public boolean eliminardocumentCategories(String id) {
        try{
            DocumentCategoriesEntity existente = repo.findById(id).orElse(null);
            if (existente != null){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("no se encontro el registo", 1);
        }
    }
}
