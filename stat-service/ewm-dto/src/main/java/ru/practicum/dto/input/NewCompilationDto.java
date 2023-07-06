package ru.practicum.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author MR.k0F31N
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    private Boolean pinned;
    @NotBlank
    private String title;
    private Set<Long> events;

}