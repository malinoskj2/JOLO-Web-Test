package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.config.auth.AppUser;
import server.config.auth.TokenProvider;
import server.model.db.Examiner;
import server.model.request.AuthenticationRequest;
import server.model.request.SignupRequest;
import server.model.response.JwtResponse;
import server.repository.ExaminerRepository;
import server.service.JwtUserDetailsService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private ExaminerRepository examinerRepository;


    @RequestMapping(value = "/authenticate",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        final Optional<Examiner> examiner = this.examinerRepository.findByEmail(authenticationRequest.getEmail());

        if (examiner.isPresent()) {
            authenticate( authenticationRequest.getEmail(),
                    authenticationRequest.getPassword() + examiner.get().getSalt());

            final AppUser userDetails = (AppUser) userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            final String token = tokenProvider.generate(userDetails);

            JwtResponse response = new JwtResponse(token);
            response.setEmail(userDetails.getUsername());
            response.setFirstName(userDetails.getFirstName());
            response.setLastName(userDetails.getLastName());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Failed to authenticate, could not locate examiner");
        }

    }

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<?> register(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(this.userDetailsService.save(request));
    }

    private void authenticate(String email, String password) throws Exception {

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);

        } catch (BadCredentialsException e) {

            throw new Exception("INVALID_CREDENTIALS", e);

        }
    }

    @RequestMapping(value = "/existsEmail",
            method = RequestMethod.POST,
            produces = "application/json")
    public Boolean existsEmail(@RequestBody SignupRequest examinerRequest)
    //takes Signup info and user info to check if an email has already been registered to a doctor
    {
        final Optional<Examiner> examinerEmails =
                this.examinerRepository.findByEmail(examinerRequest.getEmail());
        //makes list of emails that match email given by user

        return examinerEmails.isPresent();
        //returns message if email is previously used, otherwise allows registration
    }
}
