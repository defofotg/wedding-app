package com.enai.wedding.application.invitation.rest;

import com.enai.wedding.domain.invitation.model.Guest;
import com.enai.wedding.application.invitation.dto.AcceptInvitationRequest;
import com.enai.wedding.application.invitation.dto.CreateInvitationRequest;
import com.enai.wedding.application.invitation.dto.CreateInvitationResponse;
import com.enai.wedding.domain.invitation.model.GuestStatus;
import com.enai.wedding.domain.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    CreateInvitationResponse createInvitation(@RequestBody final CreateInvitationRequest createOrderRequest) {
        final UUID id = invitationService.createInvitation(
                createOrderRequest.email(),
                createOrderRequest.password(),
                new Guest(
                        UUID.randomUUID(),
                        createOrderRequest.firstName(),
                        createOrderRequest.lastName(),
                        GuestStatus.MAIN
                )
        );

        return new CreateInvitationResponse(id);
    }

    @PostMapping ( "/{id}/decline")
    void declineInvitation(@PathVariable final UUID id) {
        invitationService.refuseInvitation(id);
    }

    @PostMapping(value = "/{id}/complete", consumes = MediaType.APPLICATION_JSON_VALUE)
    void acceptInvitation(@PathVariable final UUID id, @RequestBody final AcceptInvitationRequest acceptInvitationRequest) {
        invitationService.acceptInvitation(
                id,
                acceptInvitationRequest.events(),
                new Guest(
                        UUID.randomUUID(),
                        acceptInvitationRequest.firstName(),
                        acceptInvitationRequest.lastName(),
                        GuestStatus.ATTENDANT
                )
        );
    }
}
