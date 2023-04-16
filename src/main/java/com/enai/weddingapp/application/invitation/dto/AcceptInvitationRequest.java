package com.enai.weddingapp.application.invitation.dto;

import com.enai.weddingapp.domain.invitation.model.Event;
import lombok.Builder;

import java.util.Set;

@Builder
public record AcceptInvitationRequest(
        String invitationId,
        Set<Event> events,
        String firstName,
        String lastName,
        String secret
){
}
