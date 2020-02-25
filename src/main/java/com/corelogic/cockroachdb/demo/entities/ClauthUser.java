package com.corelogic.cockroachdb.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "CLAUTHUSER")
@Data
public class ClauthUser {

    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean active = false;
    private String userRole = "USER";
}
