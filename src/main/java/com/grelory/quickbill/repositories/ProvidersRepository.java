package com.grelory.quickbill.repositories;

import com.grelory.quickbill.entity.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvidersRepository extends CrudRepository<Provider, String> {
    Provider findByProviderName(String providerName);
}
