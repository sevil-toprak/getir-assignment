package com.getir.assignment.controller;

import com.getir.assignment.controller.exception.InvalidDataException;
import com.getir.assignment.controller.request.CreateCustomerRequest;
import com.getir.assignment.controller.request.LoginRequest;
import com.getir.assignment.controller.response.JwtResponse;
import com.getir.assignment.domain.Customer;
import com.getir.assignment.repository.RoleRepository;
import com.getir.assignment.security.jwt.TokenProvider;
import com.getir.assignment.security.service.UserDetailsImpl;
import com.getir.assignment.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    AuthenticationManager authenticationManager;

    CustomerService customerService;

    TokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, CustomerService customerService,
                          RoleRepository roleRepository, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        logger.debug("Login with username: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateJwtToken(authentication);

        logger.debug("JWT token created successfully.");

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
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(bindingResult);
        }

        Customer customer = customerService.createCustomer(createCustomerRequest);
        logger.debug("Customer created successfully.");

        return ResponseEntity.ok(customer);
    }
}