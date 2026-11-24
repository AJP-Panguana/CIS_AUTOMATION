package mz.co.maximo.cis.models;

import mz.co.maximo.cis.core.config.ConfigManager;

/**
 * A CIS user with a role. Credentials come from config, never from code:
 * users.{role}.email / users.{role}.password
 * (local.properties, -D flags or env vars CIS_USERS_{ROLE}_EMAIL ...).
 */
public record User(String role, String email, String password) {

    public static User forRole(String role) {
        String key = "users." + role.toLowerCase();
        return new User(role,
                ConfigManager.require(key + ".email"),
                ConfigManager.require(key + ".password"));
    }

    @Override
    public String toString() {
        return role + " <" + email + ">"; // never print the password
    }
}
