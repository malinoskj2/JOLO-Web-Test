package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.config.auth.TokenProvider;
import server.model.request.AuthenticationRequest;
import server.model.request.SignupRequest;
import server.service.JwtUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest credentials) throws Exception {
        return null;
    }

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<?> register(@RequestBody SignupRequest request) {
            return ResponseEntity.ok(this.userDetailsService.save(request));
    }

}
