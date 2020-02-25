package com.corelogic.cockroachdb.demo.services;

import com.corelogic.cockroachdb.demo.entities.ClauthUser;
import com.corelogic.cockroachdb.demo.repositories.ClauthUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ClauthUserService {

    private final ClauthUserRepository userRepository;

    public ClauthUserService(ClauthUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ClauthUser save(ClauthUser user) {
        return userRepository.save(user);
    }
}
