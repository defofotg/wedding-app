package com.enai.weddingapp.application.invitation.rest;

import com.enai.weddingapp.domain.invitation.model.Guest;
import com.enai.weddingapp.application.invitation.dto.AcceptInvitationRequest;
import com.enai.weddingapp.application.invitation.dto.CreateInvitationRequest;
import com.enai.weddingapp.application.invitation.dto.CreateInvitationResponse;
import com.enai.weddingapp.domain.invitation.model.GuestStatus;
import com.enai.weddingapp.domain.invitation.model.Invitation;
import com.enai.weddingapp.domain.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    ResponseEntity<String> declineInvitation(@PathVariable final UUID id) {
        invitationService.refuseInvitation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/complete", consumes = MediaType.APPLICATION_JSON_VALUE)
    void acceptInvitation(@PathVariable final String id, @RequestBody final AcceptInvitationRequest acceptInvitationRequest) {
        invitationService.acceptInvitation(
                UUID.fromString(id),
                acceptInvitationRequest.events(),
                acceptInvitationRequest.hasGuest() ? new Guest(
                        UUID.randomUUID(),
                        acceptInvitationRequest.firstName(),
                        acceptInvitationRequest.lastName(),
                        GuestStatus.ATTENDANT
                ) : null,
                acceptInvitationRequest.secret()
        );
    }

    @GetMapping
    ResponseEntity<List<Invitation>> getAll(){
        return ResponseEntity.ok(invitationService.retrieveInvitations());
    }
}
