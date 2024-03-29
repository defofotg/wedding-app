package com.enai.weddingapp.domain.invitation.repository;

import com.enai.weddingapp.domain.invitation.model.Invitation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvitationRepository {
    Optional<Invitation> findById(UUID id);
    Optional<Invitation> findInvitationByIdAndToken(UUID id, String token);
    Optional<Invitation> findInvitationByEmailAndToken(String email, String token);
    void save(Invitation invitation);
    void bulkSave(InputStream invitations) throws IOException;
    List<Invitation> findAll();
}
