package ru.practicum.dto.category;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author MR.k0F31N
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    @NotBlank(message = "Name not be full blank")
    @Length(min = 1, max = 50, message = "Name length must be min=1 and max=50")
    private String name;
}
