package ru.practicum.util;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

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
                endpointHit.getCreatedDate()
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

    public static ViewStatsDto toDto(ViewStats viewStats) {
        return new ViewStatsDto(
                viewStats.getApp(),
                viewStats.getUri(),
                viewStats.getHits());
    }

    public static List<EndpointHitDto> toDto(Iterable<EndpointHit> endpointHits) {
        List<EndpointHitDto> result = new ArrayList<>();

        for (EndpointHit endpointHit : endpointHits) {
            result.add(toDto(endpointHit));
        }

        return result;
    }

    public static List<ViewStatsDto> viewToDto(Iterable<ViewStats> viewStats) {
        List<ViewStatsDto> result = new ArrayList<>();

        for (ViewStats element : viewStats) {
            result.add(toDto(element));
        }

        return result;
    }
}
