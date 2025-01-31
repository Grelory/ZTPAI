package com.grelory.quickbill.repositories;

import com.grelory.quickbill.entity.TicketToBuy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketsToBuyRepository extends CrudRepository<TicketToBuy, String> {

    @Query("SELECT new TicketToBuy(null, p, l, tr, ti) " +
            "FROM Provider p " +
            "CROSS JOIN Location l " +
            "CROSS JOIN TransportType tr " +
            "CROSS JOIN TicketType ti " +
            "LEFT JOIN TicketToBuy t " +
            "ON t.provider.providerId = p.providerId " +
            "AND t.location.locationId = l.locationId " +
            "AND t.transportType.transportTypeId = tr.transportTypeId " +
            "AND t.ticketType.ticketTypeId = ti.ticketTypeId " +
            "WHERE t.ticketToBuyId IS NULL")
    List<TicketToBuy> findAllUnmatched();

}
