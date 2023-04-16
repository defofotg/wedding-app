package com.enai.weddingapp.domain.authentication.repository;

import com.enai.weddingapp.domain.invitation.model.Invitation;

import java.util.Optional;

public interface AuthenticationRepository {
    Optional<Invitation> login(String login, String password);
}
