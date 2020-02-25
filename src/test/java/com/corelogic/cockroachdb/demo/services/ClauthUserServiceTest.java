package com.corelogic.cockroachdb.demo.services;

import com.corelogic.cockroachdb.demo.entities.ClauthUser;
import com.corelogic.cockroachdb.demo.repositories.ClauthUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClauthUserServiceTest {

    @InjectMocks
    ClauthUserService subject;

    @Mock
    ClauthUserRepository userRepository;

    @Test
    public void save_callsUserRepositoryAndReturnsSavedUser() {
        ClauthUser user = new ClauthUser();
        ClauthUser savedUser = new ClauthUser();
        when(userRepository.save(user)).thenReturn(savedUser);

        ClauthUser actualUser = subject.save(user);

        assertThat(actualUser).isSameAs(savedUser);
        verify(userRepository).save(user);
    }
}
