package ru.practicum.dto.location;

import lombok.*;

/**
 * @author MR.k0F31N
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    private Double lat;
    private Double lon;
}
