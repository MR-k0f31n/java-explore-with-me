package ru.practicum.dto.input;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author MR.k0F31N
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateCompilationDto {
    private Long id;
    private List<Long> events;
    private Boolean pinned;
    @Size(max = 50)
    private String title;
}
