package ru.practicum.dto.input;

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
public class NewCommentDto {
    @NotBlank(message = "comment cannot blank")
    @Length(min = 1, max = 7000, message = "comment length max = 7000, min = 1")
    private String text;
}
