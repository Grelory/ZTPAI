package com.grelory.quickbill.repositories;

import com.grelory.quickbill.entity.TicketType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypesRepository extends CrudRepository<TicketType, String> {
    TicketType findByTicketTypeName(String ticketTypeName);
}
