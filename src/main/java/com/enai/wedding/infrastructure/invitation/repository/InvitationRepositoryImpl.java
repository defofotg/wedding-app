package com.enai.wedding.infrastructure.invitation.repository;

import com.enai.wedding.domain.invitation.model.Invitation;
import com.enai.wedding.domain.invitation.repository.InvitationRepository;
import com.enai.wedding.infrastructure.invitation.mapper.InvitationMapper;
import com.enai.wedding.infrastructure.invitation.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InvitationRepositoryImpl implements InvitationRepository {
    private final SpringDataJPAInvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;

    @Override
    public Optional<Invitation> findById(UUID id) {
        return invitationRepository.findById(id).map(invitationMapper::toModel);
    }

    @Override
    public Optional<Invitation> findInvitationByEmailAndToken(String login, String secret) {
        return invitationRepository.findByLoginAndSecret(login, secret).map(invitationMapper::toModel);
    }

    @Override
    public void save(Invitation invitation) {
        invitationRepository.save(invitationMapper.toEntity(invitation));
    }

    @Override
    public void bulkSave(InputStream inputStream) {
        List<Invitation> invitations = Helper.toInvitations(inputStream);

        invitationRepository.saveAll(
                invitations.stream()
                        .map(invitationMapper::toEntity)
                        .toList()
        );
    }
}
