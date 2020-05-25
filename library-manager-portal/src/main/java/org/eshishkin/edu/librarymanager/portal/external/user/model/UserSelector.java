package org.eshishkin.edu.librarymanager.portal.external.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSelector implements SearchRequest.Selector {
    private String firstName;
    private String lastName;
    private String email;

    public UserSelector firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserSelector lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserSelector email(String email) {
        this.email = email;
        return this;
    }
}
