package ru.practicum.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author MR.k0F31N
 */
@Getter
@Setter
@Builder
public class CaseUpdatedStatusDto {
    private List<Long> idsFromUpdateStatus;
    private List<Long> processedIds;
}
