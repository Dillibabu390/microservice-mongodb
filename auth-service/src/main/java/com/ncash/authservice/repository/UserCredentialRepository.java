package com.ncash.authservice.repository;


import com.ncash.authservice.entity.UserCredential;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends MongoRepository<UserCredential,Integer> {
    Optional<UserCredential> findByName(String username);
}
