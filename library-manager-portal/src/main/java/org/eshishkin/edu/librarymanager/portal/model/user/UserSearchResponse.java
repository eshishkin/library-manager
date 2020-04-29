package org.eshishkin.edu.librarymanager.portal.model.user;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.eshishkin.edu.librarymanager.portal.model.User;

@Getter
@Setter
public class UserSearchResponse {
    private List<User> docs;
}
