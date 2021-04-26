package mk.ukim.finki.moodle.model.enumerations;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_STUDENT,
    ROLE_PROFESSOR,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
