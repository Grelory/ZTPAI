package com.grelory.quickbill.repositories;

import com.grelory.quickbill.entity.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationsRepository extends CrudRepository<Location, String> {

    Location findByLocationName(String locationName);
}
