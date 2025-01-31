package com.grelory.quickbill.repositories;

import com.grelory.quickbill.entity.TransportType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportTypesRepository extends CrudRepository<TransportType, String> {
    TransportType findByTransportTypeName(String transportTypeName);
}
