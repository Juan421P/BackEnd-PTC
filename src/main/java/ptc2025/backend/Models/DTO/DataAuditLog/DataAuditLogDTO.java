package ptc2025.backend.Models.DTO.DataAuditLog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DataAuditLogDTO {
    private String auditLogID;

    @NotBlank(message = "El usuario es obligatorio")
    private String userID;

    @NotBlank(message = "La tabla afectada es obligatoria")
    @Size(max = 50, message = "La tabla afectada debe superar los 50 caracteres")
    private String affectedTable;

    @Size(max = 100, message = "El campo no debe superar los 100 caracteres")
    private String recordID;

    @NotBlank(message = "operationType es obligatorio")
    @Pattern(regexp = "INSERT|UPDATE|DELETE", message = "Debe decir que cambio se realizo (INSERT, UPDATE o DELETE) ")
    private String operationType;

    private LocalDate operationAt;

    private String oldValues;
    private String newValues;
}
