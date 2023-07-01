package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MR.k0F31n
 */
public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    @Query("select new ru.practicum.model.ViewStats(eh.app, eh.uri, count(eh.ip) as hits) " +
            "from EndpointHit eh where eh.timestamp between ?1 and ?2 " +
            "group by eh.app, eh.uri " +
            "order by hits")
    List<ViewStats> findAll(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.model.ViewStats(eh.app, eh.uri, count(distinct eh.ip) as hits) " +
            "from EndpointHit eh where eh.timestamp between ?1 and ?2 " +
            "group by eh.app, eh.uri " +
            "order by hits")
    List<ViewStats> findAllFromUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.model.ViewStats(eh.app, eh.uri, count(eh.ip) as hits) " +
            "from EndpointHit eh " +
            "where eh.timestamp between ?1 and ?2 " +
            "and (eh.uri in ?3 or ?3 = null) group by eh.app, eh.uri " +
            "order by hits desc")
    List<ViewStats> findAllByUris(LocalDateTime start, LocalDateTime end, List<String> uris);


    @Query("select new ru.practicum.model.ViewStats(eh.app, eh.uri, count(distinct eh.ip) as hits) " +
            "from EndpointHit eh " +
            "where eh.timestamp between ?1 and ?2 " +
            "and (eh.uri in ?3 or ?3 = null) group by eh.app, eh.uri " +
            "order by hits desc")
    List<ViewStats> findAllByUrisFromUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);
}
