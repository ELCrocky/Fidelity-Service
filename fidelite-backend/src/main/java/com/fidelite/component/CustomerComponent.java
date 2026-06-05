package com.fidelite.component;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.Customer;
import com.fidelite.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerComponent {

    private final CustomerRepository customerRepository;

}
