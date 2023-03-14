package com.enai.wedding.domain.authentication.repository;

import com.enai.wedding.domain.invitation.model.Invitation;

import java.util.Optional;

public interface AuthenticationRepository {
    Optional<Invitation> login(String login, String password);
}
