package ru.practicum.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author MR.k0F31N
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CompilationNewDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private final String title;
    private List<Long> events;
    private Boolean pinned;
}
