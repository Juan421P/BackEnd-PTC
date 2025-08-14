package ptc2025.backend.Services.InstrumentType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ptc2025.backend.Entities.InstrumentType.InstrumentTypeEntity;
import ptc2025.backend.Models.DTO.InstrumentType.InstrumentTypeDTO;
import ptc2025.backend.Respositories.InstrumentType.InstrumentTypeRespository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InstrumentTypeService {
    @Autowired
    InstrumentTypeRespository repo;

    //get
    public List<InstrumentTypeDTO> getInstrumentType (){
        List<InstrumentTypeEntity> instrumentos = repo.findAll();
        return instrumentos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Post
    public InstrumentTypeDTO insertInstrumentType(@RequestBody InstrumentTypeDTO dto){
        //Validamos los datos de entrada
        if (dto == null || dto.getUniversityID() == null || dto.getUniversityID().isBlank()
                || dto.getInstrumentTypeName() == null || dto.getInstrumentTypeName().isBlank()){
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }try {
            InstrumentTypeEntity entidad = convertirAEntity(dto);
            InstrumentTypeEntity guardar = repo.save(entidad);
            return convertirADTO(guardar);
        }catch (Exception e){
            log.error("Error al registrar el tipo de instrumento" + e.getMessage());
            throw new RuntimeException("Eror interno al tipo de instrumento");
        }
    }
    //Put
    public InstrumentTypeDTO updateInstrumentType(String id, InstrumentTypeDTO dto){
        InstrumentTypeEntity existente = repo.findById(id).orElseThrow(() -> new RuntimeException("El dato no pudo ser actualizado"));
        existente.setUniversityID(dto.getUniversityID());
        existente.setInstrumentTypeName(dto.getInstrumentTypeName());

        InstrumentTypeEntity actualizado = repo.save(existente);
        return convertirADTO(actualizado);
    }
    //Delete
    public boolean deleteInstrumentType(String id){
        try {
            InstrumentTypeEntity objInstrument = repo.findById(id).orElse(null);
            if (objInstrument != null){repo.deleteById(id); return true;}
            else {
                System.out.println("Tipo de instrumento no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro ningún tipo de instrumento con el ID: " + id + "para podeer ser eliminado", 1);
        }
    }
    //Convertir a DTO
    private InstrumentTypeDTO convertirADTO(InstrumentTypeEntity entity){
        InstrumentTypeDTO dto = new InstrumentTypeDTO();
        dto.setInstrumentTypeID(entity.getInstrumentTypeID());
        dto.setUniversityID(entity.getUniversityID());
        dto.setInstrumentTypeName(entity.getInstrumentTypeName());
        return dto;
    }

    //convertir a Entity
    private InstrumentTypeEntity convertirAEntity (InstrumentTypeDTO dto){
        InstrumentTypeEntity entity = new InstrumentTypeEntity();
        entity.setUniversityID(dto.getUniversityID());
        entity.setInstrumentTypeName(dto.getInstrumentTypeName());
        return entity;
    }
}
