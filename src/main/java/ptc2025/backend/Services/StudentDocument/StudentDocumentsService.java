package ptc2025.backend.Services.StudentDocument;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ptc2025.backend.Entities.PlanComponents.PlanComponentsEntity;
import ptc2025.backend.Entities.StudentDocument.StudentDocumentsEntity;
import ptc2025.backend.Models.DTO.PlanComponents.PlanComponentsDTO;
import ptc2025.backend.Models.DTO.StudentDocument.StudentDocumentsDTO;
import ptc2025.backend.Respositories.StudentDocument.StudentDocumentsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class StudentDocumentsService {
    @Autowired
    StudentDocumentsRepository repo;

    public List<StudentDocumentsDTO> getStudentDocument (){
        List<StudentDocumentsEntity> documents = repo.findAll();
        return documents.stream()
                .map(this:: convertirADTO)
                .collect(Collectors.toList());
    }
    public StudentDocumentsDTO insertStudentDocuments(@RequestBody StudentDocumentsDTO dto){
        if (dto == null || dto.getStudentID() == null || dto.getStudentID().isBlank()
        || dto.getDocumentID() == null || dto.getDocumentID().isBlank()){
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        if (dto.getSubmitted() != null &&
                !(dto.getSubmitted() == 'Y' || dto.getSubmitted() == 'N') ||
                dto.getVerified() != null &&
                !(dto.getVerified() == 'Y' || dto.getVerified() == 'N')){
            throw new IllegalArgumentException("Submitted y Verified solo puede ser Y' o 'N'");
        }
        try {
            StudentDocumentsEntity entity = convertirAEntity(dto);
            StudentDocumentsEntity guardardo = repo.save(entity);

            return convertirADTO (guardardo);
        }catch (Exception e){
            log.error("Error al guardar los documentos del estudiante: " + e.getMessage());
            throw new RuntimeException("Error interno al registrar los documentos del estudiante");
        }//los documentos del estudiante
    }
    public StudentDocumentsDTO updateStudentDocument(String id, StudentDocumentsDTO dto){
        StudentDocumentsEntity existente = new StudentDocumentsEntity();
        existente.setStudentID(dto.getStudentID());
        existente.setDocumentID(dto.getDocumentID());
        existente.setSubmitted(dto.getSubmitted());
        existente.setSubmissionDate(dto.getSubmissionDate());
        existente.setVerified(dto.getVerified());
        StudentDocumentsEntity actu = repo.save(existente);
        return convertirADTO(actu);
    }
    public boolean deleteStudentDocument(String id){
        try {
            StudentDocumentsEntity objCompo = repo.findById(id).orElse(null);
            if (objCompo != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Documentos del estudiante no encontrados");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException ("No se encontro ningún documentos de un estudiante con el ID: " + id + "para poder ser eliminado", 1);
        }

    }
    private StudentDocumentsDTO convertirADTO(StudentDocumentsEntity entity){
        StudentDocumentsDTO dto = new StudentDocumentsDTO();
        dto.setStudentDocumentID(entity.getStudentDocumentID());
        dto.setStudentID(entity.getStudentID());
        dto.setDocumentID(entity.getDocumentID());
        dto.setSubmitted(entity.getSubmitted());
        dto.setSubmissionDate(entity.getSubmissionDate());
        dto.setVerified(entity.getVerified());
        return dto;
    }
    private  StudentDocumentsEntity convertirAEntity (StudentDocumentsDTO dto){
        StudentDocumentsEntity entity = new StudentDocumentsEntity();
        entity.setStudentID(dto.getStudentID());
        entity.setDocumentID(dto.getDocumentID());
        entity.setSubmitted(dto.getSubmitted());
        entity.setSubmissionDate(dto.getSubmissionDate());
        entity.setVerified(dto.getVerified());
        return entity;
    }

}
