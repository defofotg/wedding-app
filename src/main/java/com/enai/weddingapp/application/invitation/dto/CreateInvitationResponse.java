package com.enai.weddingapp.application.invitation.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateInvitationResponse(UUID id) {
}
