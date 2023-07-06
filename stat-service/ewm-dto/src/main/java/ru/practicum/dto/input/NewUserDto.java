package ru.practicum.dto.input;

import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author MR.k0F31n
 */
@Data
@Getter
public class NewUserDto {
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Length(max = 250, min = 2, message = "Name length to be max = 250 min = 2")
    private String name;
    @Email(message = "Email not email format ")
    @NotNull(message = "Email cannot be null")
    @Length(max = 254, min = 6, message = "Email length to be max = 254 min = 6")
    private String email;
}
