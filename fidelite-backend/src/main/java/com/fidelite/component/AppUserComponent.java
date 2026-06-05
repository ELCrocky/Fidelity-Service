package com.fidelite.component;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fidelite.exceptions.NotFoundElementException;
import com.fidelite.models.AppUser;
import com.fidelite.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppUserComponent {

    private final AppUserRepository appUserRepository;

}
