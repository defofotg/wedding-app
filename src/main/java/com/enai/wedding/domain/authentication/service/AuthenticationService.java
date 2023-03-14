package com.enai.wedding.domain.authentication.service;

import com.enai.wedding.domain.authentication.model.AuthInfo;

public interface AuthenticationService {
    AuthInfo authenticate(String login, String password);
}
