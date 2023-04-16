package com.enai.weddingapp.infrastructure.invitation.repository;

import com.enai.weddingapp.domain.invitation.model.Invitation;
import com.enai.weddingapp.domain.invitation.repository.InvitationRepository;
import com.enai.weddingapp.infrastructure.invitation.mapper.InvitationMapper;
import com.enai.weddingapp.infrastructure.invitation.utils.Helper;
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
    public Optional<Invitation> findInvitationByIdAndToken(UUID id, String token) {
        return invitationRepository.findByIdAndSecret(id, token).map(invitationMapper::toModel);
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

    @Override
    public List<Invitation> findAll() {
        return invitationRepository.findAll()
                .stream()
                .map(invitationMapper::toModel)
                .toList();
    }


}
