package com.enai.weddingapp.domain.authentication.model;

import com.enai.weddingapp.domain.invitation.model.Guest;
import com.enai.weddingapp.domain.invitation.model.GuestStatus;
import com.enai.weddingapp.domain.invitation.model.Invitation;
import lombok.Builder;

import java.util.Objects;
import java.util.UUID;

@Builder
public record AuthInfo(String firstName, String lastName, String email, String token, UUID invitationId) {
    public AuthInfo(Invitation invitation) {
        this(invitation.getGuests()
                        .stream()
                        .filter(guest -> Objects.nonNull(guest) && GuestStatus.MAIN.equals(guest.getStatus()))
                        .map(Guest::getFirstName).findFirst().orElse(null),
                invitation.getGuests()
                        .stream()
                        .filter(guest -> Objects.nonNull(guest) && GuestStatus.MAIN.equals(guest.getStatus()))
                        .map(Guest::getLastName).findFirst().orElse(null),
                invitation.getLogin(),
                invitation.getSecret(),
                invitation.getId()

        );
    }
}
