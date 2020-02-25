package com.corelogic.cockroachdb.demo.controllers;

import com.corelogic.cockroachdb.demo.entities.ClauthUser;
import com.corelogic.cockroachdb.demo.services.ClauthUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(MockitoExtension.class)
class ClauthUserControllerTest {

    @InjectMocks
    ClauthUserController subject;

    @Mock
    ClauthUserService userService;

    @Mock
    ClauthUser user;
    @Mock
    HttpServletRequest request;

    @BeforeEach
    void setUp() {
        StringBuffer requestUrl = new StringBuffer("http://localhost/users");
        when(request.getRequestURL()).thenReturn(requestUrl);
    }

    @Test
    public void createUser_returnsCreatedStatusWithCreatedUri() {
        when(userService.save(user)).thenReturn(user);

        UUID uuid = randomUUID();
        when(user.getId()).thenReturn(uuid);

        ResponseEntity<?> response = subject.createUser(user, request);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getHeaders().get("Location")).containsOnly("http://localhost/users/" + uuid.toString());

        verify(userService).save(user);
        verify(user).getId();
    }
}
