package com.enai.wedding.application.invitation.dto;

import com.enai.wedding.domain.invitation.model.Event;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record AcceptInvitationRequest(UUID id, Set<Event> events, String firstName, String lastName){
}
