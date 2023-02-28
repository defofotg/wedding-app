package com.enai.wedding.domain.invitation.service;

import com.enai.wedding.domain.invitation.model.*;
import com.enai.wedding.domain.invitation.repository.InvitationRepository;

import java.util.Set;
import java.util.UUID;

public class DomainInvitationService implements InvitationService {

    private final InvitationRepository invitationRepository;

    public DomainInvitationService(final InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @Override
    public UUID createInvitation(String login, String secret, Guest guest) {
        final Invitation invitation = new Invitation(
                UUID.randomUUID(),
                login,
                secret,
                guest,
                InvitationStatus.PENDING
        );
        invitationRepository.save(invitation);

        return invitation.getId();
    }

    @Override
    public void refuseInvitation(UUID id) throws DomainException {
        final Invitation invitation = getInvitation(id);
        invitation.refuseInvitation();
        invitationRepository.save(invitation);
    }

    @Override
    public void acceptInvitation(UUID id, Set<Event> eventSet, Guest attendant) throws DomainException {
        final Invitation invitation = getInvitation(id);
        invitation.replyToInvitation(attendant, eventSet);
        invitationRepository.save(invitation);
    }

    @Override
    public Invitation getInvitation(String email, String token) {
        return invitationRepository.findInvitationByEmailAndToken(email, token)
                .orElseThrow(() -> new DomainException("No invitation matches this credentials!"));
    }

    private Invitation getInvitation(UUID id) {
        return invitationRepository
                .findById(id)
                .orElseThrow(() -> new DomainException("Invitation with given id doesn't exist"));
    }
}
