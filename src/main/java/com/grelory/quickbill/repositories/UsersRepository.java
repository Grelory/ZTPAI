package com.grelory.quickbill.repositories;

import com.grelory.quickbill.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, String> {
    Optional<User> findByUserEmail(String userEmail);
}
