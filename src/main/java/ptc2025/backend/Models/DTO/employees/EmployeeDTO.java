package ptc2025.backend.Models.DTO.employees;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeDTO {

    @NotBlank(message = "El ID del empleado es obligatorio")
    private String id;

    @NotBlank(message = "El nombre completo es obligatorio")
    private String fullName;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Correo electrónico inválido")
    private String email;

    private String position;

    private String phone;

    @NotBlank(message = "El estado de actividad es obligatorio")
    private Boolean isActive = true;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String id, String fullName, String email, String position, String phone, Boolean isActive) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.position = position;
        this.phone = phone;
        this.isActive = isActive;
    }
}
