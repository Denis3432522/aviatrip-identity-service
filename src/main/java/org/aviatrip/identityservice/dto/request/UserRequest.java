package org.aviatrip.identityservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.aviatrip.identityservice.enumeration.Role;

@Getter
public class UserRequest {

    @NotNull
    @Pattern(regexp = "[A-Za-z]{2,32}", message = "{messages.user.name}")
    private String name;

    @NotNull
    @Pattern(regexp = "[A-Za-z]{2,54}", message = "{messages.user.surname}")
    private String surname;

    @NotNull
    @Size(min = 4, max = 50)
    @Email
    private String email;

    @NotNull
    @Size(min=8, max = 124)
    private String password;

    @NotNull
    @Pattern(regexp = "customer|representative", message = "invalid value")
    private String role;

    public Role getRole() {
        return Role.findByRoleName(role);
    }
}
