package ptc2025.backend.Services.CodeTokens;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ptc2025.backend.Entities.CodeTokens.CodeTokensEntity;
import ptc2025.backend.Models.DTO.CodeTokens.CodeTokensDTO;
import ptc2025.backend.Respositories.CodeTokens.CodeTokensRespository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CodeTokensService {
    @Autowired
    CodeTokensRespository repo;

    //get
    public List<CodeTokensDTO> getCodeToken (){
        List <CodeTokensEntity> token = repo.findAll();
        return token.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //post
    public  CodeTokensDTO insertCodeToken(@RequestBody CodeTokensDTO dto){
        //Validacion
        if(dto == null || dto.getUniversityID() == null || dto.getUniversityID().isBlank()||
                 dto.getTokenKey() == null || dto.getTokenKey().isBlank()
                || dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }
        try {
            CodeTokensEntity entity = convertirAEntity(dto);
            CodeTokensEntity guardado = repo.save(entity);
            return convertirADTO(guardado);
        }catch (Exception e){
            log.error("Error al registrar el toker " + e.getMessage());
            throw new RuntimeException("Eror interno al guardar el nuevo token");
        }

    }

    //put
    public CodeTokensDTO updateToken(String id, CodeTokensDTO dto){
        CodeTokensEntity existente = repo.findById(id).orElseThrow(() -> new RuntimeException("El dato no pudo ser actualizado. Evento no encontrado")) ;
        existente.setUniversityID(dto.getUniversityID());
        existente.setTokenKey(dto.getTokenKey());
        existente.setDescription(dto.getDescription());

        CodeTokensEntity actualizar = repo.save(existente);
        return convertirADTO(actualizar);
    }

    //delete
    public boolean deleteToken(String id){
        try {
            CodeTokensEntity objToken = repo.findById(id).orElse(null);
            if (objToken != null){
                repo.deleteById(id);
                return true;
            }else {
                System.out.println("Token no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro ningún token con el ID: " + id + "para poder ser eliminado", 1);

        }
    }
    //Convertir a DTO
    private CodeTokensDTO convertirADTO (CodeTokensEntity entity){
        CodeTokensDTO dto = new CodeTokensDTO();
        dto.setCodeTokenID(entity.getCodeTokenID());
        dto.setUniversityID(entity.getUniversityID());
        dto.setTokenKey(entity.getTokenKey());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    //Convertir a Entity
    private CodeTokensEntity convertirAEntity (CodeTokensDTO dto){
        CodeTokensEntity entity = new CodeTokensEntity();
        entity.setUniversityID(dto.getUniversityID());
        entity.setTokenKey(dto.getTokenKey());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
