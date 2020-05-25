package org.eshishkin.edu.librarymanager.portal.model.user;

import java.io.Serializable;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationRequest implements Serializable {

    @NotEmpty(message = "{registration.first_name.empty}")
    private String firstName;

    @NotEmpty(message = "{registration.last_name.empty}")
    private String lastName;

    @NotEmpty(message = "{registration.email.empty}")
    private String email;

    @Nonnull
    private Gender gender;

    @NotEmpty(message = "{registration.password.empty}")
    @Size(min = 6, max = 20, message = "{registration.password.length}")
    private String password;
}
