package com.getir.assignment.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.getir.assignment.controller.request.LoginRequest;
import com.getir.assignment.controller.request.CreateCustomerRequest;
import com.getir.assignment.controller.response.JwtResponse;
import com.getir.assignment.controller.response.MessageResponse;
import com.getir.assignment.domain.Role;
import com.getir.assignment.domain.User;
import com.getir.assignment.repository.RoleRepository;
import com.getir.assignment.repository.CustomerRepository;
import com.getir.assignment.security.Roles;
import com.getir.assignment.security.jwt.TokenProvider;
import com.getir.assignment.security.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    AuthenticationManager authenticationManager;

    CustomerRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder encoder;

    TokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, CustomerRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder encoder, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        logger.debug("login with username: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateJwtToken(authentication);

        logger.debug("jwt token create successfully.");

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                                                 userDetails.getId(),
                                                 userDetails.getUsername(),
                                                 userDetails.getEmail(),
                                                 roles));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        if (userRepository.existsByUsername(createCustomerRequest.getUsername())) {
            logger.debug("existsByUsername with username: {}", createCustomerRequest.getUsername());

            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(createCustomerRequest.getEmail())) {
            logger.debug("existsByEmail with email: {}", createCustomerRequest.getEmail());

            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        logger.debug("registerUser with username: {}", createCustomerRequest.getUsername());

        // Create new user's account
        User user = new User(createCustomerRequest.getUsername(),
                createCustomerRequest.getEmail(),
                encoder.encode(createCustomerRequest.getPassword()));

        Set<String> strRoles = createCustomerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Roles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(Roles.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(Roles.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        logger.debug("registerUser successfully.");

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}