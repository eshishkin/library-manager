package org.eshishkin.edu.librarymanager.portal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.eshishkin.edu.librarymanager.portal.model.user.Gender;

@Data
public class User {
    @JsonProperty("_id")
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private String password;
}
