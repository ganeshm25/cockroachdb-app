package com.corelogic.cockroachdb.demo.repositories;

import com.corelogic.cockroachdb.demo.entities.ClauthUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface ClauthUserRepository extends PagingAndSortingRepository<ClauthUser, UUID> {

    List<ClauthUser> findByUsername(@Param("username") String username);
}
