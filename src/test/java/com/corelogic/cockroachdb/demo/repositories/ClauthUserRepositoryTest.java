package com.corelogic.cockroachdb.demo.repositories;

import com.corelogic.cockroachdb.demo.IntegrationTest;
import com.corelogic.cockroachdb.demo.entities.ClauthUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClauthUserRepositoryTest extends IntegrationTest {

    @Autowired
    ClauthUserRepository subject;

    @Test
    public void findByUsername_returnsListOfUsers() {
        List<ClauthUser> users = subject.findByUsername("testuser");

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getFirstName()).isEqualTo("William");
        assertThat(users.get(0).getLastName()).isEqualTo("Wallace");
    }
}
