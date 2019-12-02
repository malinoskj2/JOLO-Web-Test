package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.config.auth.AppUser;
import server.exception.UserAlreadyExistAuthenticationException;
import server.model.db.Examiner;
import server.model.request.SignupRequest;
import server.repository.ExaminerRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private ExaminerRepository examinerRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    private static final int NUM_SALT_ROUNDS = 12;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.examinerRepo.findByEmail(email)
                .map(user ->{
                    return new AppUser(user.getEmail(), user.getPassword(),
                                true, true, true, true, new ArrayList<>(),
                                user.getExamID(), user.getfName(), user.getlName());
                })
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public Examiner save(final SignupRequest signupRequest)  throws UserAlreadyExistAuthenticationException {

        if(this.examinerRepo.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistAuthenticationException("Username already taken");
        } else {
            final String salt = BCrypt.gensalt(NUM_SALT_ROUNDS);

            final Examiner examiner = new Examiner();
            examiner.setEmail(signupRequest.getEmail());
            examiner.setfName(signupRequest.getfName());
            examiner.setlName(signupRequest.getlName());
            examiner.setSalt(salt);
            examiner.setPassword(bcryptEncoder.encode(signupRequest.getPassword() + salt));
            return this.examinerRepo.save(examiner);
        }
    }
}
