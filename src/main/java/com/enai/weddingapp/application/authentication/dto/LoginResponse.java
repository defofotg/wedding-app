package com.enai.weddingapp.application.authentication.dto;

import lombok.Builder;

@Builder
public record LoginResponse(String firstName, String lastName, String email, String token, String invitationId) {
}
