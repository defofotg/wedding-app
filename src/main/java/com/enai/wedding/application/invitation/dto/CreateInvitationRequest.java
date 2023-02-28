package com.enai.wedding.application.invitation.dto;

import lombok.Builder;

@Builder
public record CreateInvitationRequest (String email, String password, String firstName, String lastName){
}
