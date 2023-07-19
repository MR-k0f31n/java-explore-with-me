package ru.practicum.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author MR.k0F31N
 */

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class NewCategoryDto {
    @NotNull(message = "Name category is null")
    @NotBlank(message = "Name category cannot be blank")
    @Length(max = 50, min = 1, message = "Name category length max = 50 min = 1")
    private String name;
}
