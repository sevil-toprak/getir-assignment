package com.getir.assignment.service;

import com.getir.assignment.controller.exception.AlreadyExistsException;
import com.getir.assignment.controller.request.CreateCustomerRequest;
import com.getir.assignment.controller.response.MessageResponse;
import com.getir.assignment.domain.Book;
import com.getir.assignment.domain.Customer;
import com.getir.assignment.domain.Role;
import com.getir.assignment.repository.BookRepository;
import com.getir.assignment.repository.CustomerRepository;
import com.getir.assignment.repository.RoleRepository;
import com.getir.assignment.security.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerService {
    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;

    RoleRepository roleRepository;

    PasswordEncoder encoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public Customer createCustomer(CreateCustomerRequest createCustomerRequest) {
        if (customerRepository.existsByUsername(createCustomerRequest.getUsername())) {
            logger.debug("existsByUsername with username: {}", createCustomerRequest.getUsername());

            throw new AlreadyExistsException(createCustomerRequest.getUsername());
        }

        if (customerRepository.existsByEmail(createCustomerRequest.getEmail())) {
            logger.debug("existsByEmail with email: {}", createCustomerRequest.getEmail());

            throw new AlreadyExistsException(createCustomerRequest.getEmail());
        }

        logger.debug("registerUser with username: {}", createCustomerRequest.getUsername());

        Customer customer = new Customer(createCustomerRequest.getUsername(),
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

        customer.setRoles(roles);
        return customerRepository.save(customer);
    }

}
