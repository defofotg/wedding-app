package com.enai.weddingapp.application.authentication.rest;

import com.enai.weddingapp.application.authentication.dto.LoginRequest;
import com.enai.weddingapp.application.authentication.dto.LoginResponse;
import com.enai.weddingapp.domain.authentication.model.AuthInfo;
import com.enai.weddingapp.domain.authentication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    LoginResponse authenticate(@RequestBody final LoginRequest loginRequest) {
        AuthInfo authInfo = authenticationService.authenticate(loginRequest.login(), loginRequest.password());
        return new LoginResponse(authInfo.firstName(), authInfo.lastName(), authInfo.email(), authInfo.token(), authInfo.invitationId().toString());
    }
}
