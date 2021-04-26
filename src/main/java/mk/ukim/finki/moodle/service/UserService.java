package mk.ukim.finki.moodle.service;

import mk.ukim.finki.moodle.model.exceptions.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(String email, String password, String repeatedPassword) throws EmailExistsException;
}
