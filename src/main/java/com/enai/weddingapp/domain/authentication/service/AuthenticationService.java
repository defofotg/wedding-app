package com.enai.weddingapp.domain.authentication.service;

import com.enai.weddingapp.domain.authentication.model.AuthInfo;

public interface AuthenticationService {
    AuthInfo authenticate(String login, String password);
}
