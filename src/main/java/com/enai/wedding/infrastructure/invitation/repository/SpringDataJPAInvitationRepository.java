package com.enai.wedding.infrastructure.invitation.repository;

import com.enai.wedding.infrastructure.invitation.entity.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataJPAInvitationRepository extends JpaRepository<InvitationEntity, UUID> {
    Optional<InvitationEntity> findByLoginAndSecret(String login, String secret);
}
