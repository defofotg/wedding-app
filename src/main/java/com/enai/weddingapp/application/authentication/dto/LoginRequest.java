package com.enai.weddingapp.application.authentication.dto;

import lombok.Builder;

@Builder
public record LoginRequest(String login, String password) {
}
