package com.enai.weddingapp.domain.authentication.service;

import com.enai.weddingapp.domain.authentication.model.AuthInfo;
import com.enai.weddingapp.domain.invitation.service.InvitationService;

public class DomainAuthenticationService implements AuthenticationService {

    private final InvitationService invitationService;

    public DomainAuthenticationService(final InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @Override
    public AuthInfo authenticate(String login, String password) {
        return new AuthInfo(invitationService.getInvitation(login, password));
    }
}
