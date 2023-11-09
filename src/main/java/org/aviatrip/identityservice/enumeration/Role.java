package org.aviatrip.identityservice.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ROLE_CUSTOMER("customer"),
    ROLE_REPRESENTATIVE("representative"),
    ROLE_VERIFIED_REPRESENTATIVE("verified_representative");

    private final String roleName;
    private final static Map<String, Role> map = new HashMap<>();

    static {
        for(Role role : Role.values()) {
            map.put(role.roleName, role);
        }
    }

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static Role findByRoleName(String roleName) {
        Role role = map.get(roleName);

        if(role == null)
            throw new IllegalArgumentException("No such role name " + roleName);

        return role;
    }
}
