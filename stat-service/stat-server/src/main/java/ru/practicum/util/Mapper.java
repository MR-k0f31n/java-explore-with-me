package ru.practicum.util;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.EndpointHit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MR.k0F31n
 */
public class Mapper {
    public static EndpointHitDto toDto(EndpointHit endpointHit) {
        return new EndpointHitDto(
                endpointHit.getId(),
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp()
        );
    }

    public static EndpointHit toObject(EndpointHitDto endpointHitDto) {
        return new EndpointHit(
                endpointHitDto.getId(),
                endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                endpointHitDto.getTimestamp()
        );
    }

    public static List<EndpointHitDto> toDto(Iterable<EndpointHit> endpointHits) {
        List<EndpointHitDto> result = new ArrayList<>();

        for (EndpointHit endpointHit : endpointHits) {
            result.add(toDto(endpointHit));
        }

        return result;
    }
}
