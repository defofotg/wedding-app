package com.enai.wedding.application.authentication.dto;

import lombok.Builder;

@Builder
public record LoginResponse(String firstName, String lastName, String email, String token) {
}
