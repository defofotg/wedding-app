package com.enai.weddingapp.domain.invitation.service;

import com.enai.weddingapp.domain.invitation.model.Event;
import com.enai.weddingapp.domain.invitation.model.Guest;
import com.enai.weddingapp.domain.invitation.model.Invitation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface InvitationService {

    UUID createInvitation(String login, String secret, Guest guest);
    void refuseInvitation(UUID id);
    void acceptInvitation(UUID id, Set<Event> eventSet, Guest attendant, String secret);
    Invitation getInvitation(String email, String token);
    void createInvitations(InputStream list) throws IOException;
    List<Invitation> retrieveInvitations();


}
