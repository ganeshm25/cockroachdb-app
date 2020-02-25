package com.corelogic.cockroachdb.demo.controllers;

import com.corelogic.cockroachdb.demo.entities.ClauthUser;
import com.corelogic.cockroachdb.demo.services.ClauthUserService;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RepositoryRestController
public class ClauthUserController {

    private final ClauthUserService userService;

    public ClauthUserController(ClauthUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = POST, value = "/users")
    public @ResponseBody
    ResponseEntity<?> createUser(@RequestBody ClauthUser user, HttpServletRequest request) {
        ClauthUser createdUser = userService.save(user);

        URI createdUri = URI.create(request.getRequestURL().toString() + "/" + createdUser.getId());
        return ResponseEntity.created(createdUri).body(createdUser);
    }
}
