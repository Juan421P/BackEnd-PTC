package ptc2025.backend.Services.DataAuditLog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ptc2025.backend.Entities.DataAuditLog.DataAuditLogEntity;
import ptc2025.backend.Entities.PlanComponents.PlanComponentsEntity;
import ptc2025.backend.Models.DTO.DataAuditLog.DataAuditLogDTO;
import ptc2025.backend.Models.DTO.PlanComponents.PlanComponentsDTO;
import ptc2025.backend.Respositories.DataAuditLog.DataAuditLogRespository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataAuditLogService {
    @Autowired
    DataAuditLogRespository repo;
    //Get
    public List<DataAuditLogDTO> getDataAuditLog (){
        List<DataAuditLogEntity> components = repo.findAll();
        return components.stream()
                .map(this:: convertirADTO)
                .collect(Collectors.toList());
    }
    public DataAuditLogDTO insertDataAuditLog(DataAuditLogDTO dto){
        if (dto == null || dto.getUserID() == null || dto.getUserID().isBlank() ||
                dto.getAffectedTable() == null || dto.getAffectedTable().isBlank()||
                dto.getOperationType() == null || dto.getOperationType().isBlank()){
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }try {
            DataAuditLogEntity entity = convertirAEntity(dto);
            DataAuditLogEntity guardardo = repo.save(entity);
            return convertirADTO(guardardo);
        } catch (Exception e) {
            throw new RuntimeException("Error interno al guardar la auditoría: " + e.getMessage());
        }
    }
    public DataAuditLogDTO updateDataAuditLog (String id, DataAuditLogDTO dto){
        DataAuditLogEntity existente = new DataAuditLogEntity();
        existente.setUserID(dto.getUserID());
        existente.setAffectedTable(dto.getAffectedTable());
        existente.setRecordID(dto.getRecordID());
        existente.setOperationType(dto.getOperationType());
        existente.setOperationAt(dto.getOperationAt());
        existente.setNewValues(dto.getNewValues());
        existente.setOldValues(dto.getOldValues());
        DataAuditLogEntity actualizar = repo.save(existente);
        return convertirADTO(actualizar);
    }
    public boolean deleteDataAuditLog(String id){
        try {
            DataAuditLogEntity objCompo = repo.findById(id).orElse(null);
            if (objCompo != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Auditoria no encontrada");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException ("No se encontro ninguna auditoria con el ID: " + id + "para poder ser eliminado", 1);
        }

    }
    public DataAuditLogEntity convertirAEntity(DataAuditLogDTO dto) {
        DataAuditLogEntity entity = new DataAuditLogEntity();
        entity.setAuditLogID(dto.getAuditLogID());
        entity.setUserID(dto.getUserID());
        entity.setAffectedTable(dto.getAffectedTable());
        entity.setRecordID(dto.getRecordID());
        entity.setOperationType(dto.getOperationType().toUpperCase());
        entity.setOperationAt(dto.getOperationAt());
        entity.setOldValues(dto.getOldValues());
        entity.setNewValues(dto.getNewValues());
        return entity;
    }

    public DataAuditLogDTO convertirADTO(DataAuditLogEntity entity) {
        DataAuditLogDTO dto = new DataAuditLogDTO();
        dto.setUserID(entity.getUserID());
        dto.setAffectedTable(entity.getAffectedTable());
        dto.setRecordID(entity.getRecordID());
        dto.setOperationType(entity.getOperationType());
        dto.setOperationAt(entity.getOperationAt());
        dto.setOldValues(entity.getOldValues());
        dto.setNewValues(entity.getNewValues());
        return dto;
    }
}
