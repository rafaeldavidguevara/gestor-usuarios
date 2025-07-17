package com.globalogic.GestorUsuarios.util.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
public class SignUpRequestDto {
    private String name;
    @NotNull(message = "Field email is required")
    @NotBlank(message = "Field email must not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Field email must be a valid email")
    private String email;
    @NotNull(message = "Field password is required")
    @NotBlank(message = "Field password must not be empty")
    @Pattern(regexp = "^(?=(?:[^A-Z]*[A-Z]){1}[^A-Z]*$)(?=(?:[^0-9]*[0-9]){2}[^0-9]*$)[a-zA-Z0-9]{8,12}$",
            message = "Field password must be a valid password")
    private String password;
    private List<PhoneDto> phones;
}
