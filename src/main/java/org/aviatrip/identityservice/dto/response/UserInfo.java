package org.aviatrip.identityservice.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.aviatrip.identityservice.enumeration.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserInfo {

    private UUID id;

    private String name;

    private String surname;

    private String email;

    private long balance;

    private Role role;

    private LocalDateTime createdAt;

    @JsonProperty("role")
    public String getRole() {
        return role.getRoleName();
    }
}
