package com.enai.wedding.domain.invitation.repository;

import com.enai.wedding.domain.invitation.model.Invitation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface InvitationRepository {
    Optional<Invitation> findById(UUID id);
    Optional<Invitation> findInvitationByEmailAndToken(String email, String token);
    void save(Invitation invitation);
    void bulkSave(InputStream invitations) throws IOException;
}
