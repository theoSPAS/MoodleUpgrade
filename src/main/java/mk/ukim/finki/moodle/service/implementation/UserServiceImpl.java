package mk.ukim.finki.moodle.service.implementation;

import mk.ukim.finki.moodle.model.*;
import mk.ukim.finki.moodle.model.enumerations.Role;
import mk.ukim.finki.moodle.model.exceptions.*;
import mk.ukim.finki.moodle.repository.*;
import mk.ukim.finki.moodle.service.*;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Primary
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ProfessorRepository professorRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void register(String email, String password, String repeatedPassword) throws EmailExistsException {

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        if (!password.equals(repeatedPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if (this.userRepository.findByUsername(email).isPresent() && this.userRepository.findByUsername(email).get().getPassword()!=null){
            throw new EmailExistsException(email);
        }

        User user = new User(email,passwordEncoder.encode(password));

        if (this.studentRepository.findByEmail(email).isPresent()){

            Student student = studentRepository.findByEmail(email)
                    .orElseThrow(()->new StudentNotFoundException(email));

            studentRepository.save(student);

            user.setUsername(student.getIndex());
            user.setRole(Role.ROLE_STUDENT);
            userRepository.save(user);
        }

        else if (this.professorRepository.findByEmail(email).isPresent()){

                Professor professor = professorRepository.findByEmail(email)
                        .orElseThrow(()->new ProfessorNotFoundException(email));

                professorRepository.save(professor);

                user.setRole(Role.ROLE_PROFESSOR);
                userRepository.save(user);
        }

        else{
            throw new EmailDoesNotExistException();
        }


    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findById(s)
                .orElseThrow(()-> new UsernameNotFoundException(s));
    }
}
