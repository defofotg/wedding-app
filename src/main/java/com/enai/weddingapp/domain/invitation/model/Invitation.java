package com.enai.weddingapp.domain.invitation.model;

import java.util.*;

/**
 * @author Georges DEFO
 * @date 26/02/2023
 */
public class Invitation {

    private UUID id;
    private String login;
    private String secret;
    private Set<Guest> guests;
    private Set<Event> events;
    private InvitationStatus status;

    public Invitation(final UUID id, final String login, final String secret, final Guest mainGuest, final InvitationStatus status) {
        this.id = id;
        this.login = login;
        this.secret = secret;
        this.guests = new HashSet<>(Collections.singleton(mainGuest));
        this.status = status;
        events = new HashSet<>();
    }

    public void refuseInvitation(){
        validateState();

        status = InvitationStatus.DECLINED;
    }

    public void confirmInvitation(){
        validateState();
        status = InvitationStatus.CONFIRMED;
    }

    public void replyToInvitation(Guest attendant, Set<Event> eventSet){
        validateState();
        if (attendant != null){
            this.guests.add(attendant);
        }
        events = eventSet;
        confirmInvitation();
    }

    private void validateState() {
        if (InvitationStatus.CONFIRMED.equals(status) || InvitationStatus.DECLINED.equals(status)) {
            throw new DomainException("Invalid state!");
        }
    }
    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSecret() {
        return secret;
    }

    public Set<Guest> getGuests() {
        return guests;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public Set<Event> getEvents() {
        return events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invitation invitation = (Invitation) o;
        return Objects.equals(id, invitation.id) && Objects.equals(login, invitation.login) &&
                Objects.equals(secret, invitation.secret) && Objects.equals(guests, invitation.guests)
                && Objects.equals(status, invitation.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id, login, secret, guests, status);
    }

    public Guest getGuest(Invitation invitation, GuestStatus type) {
        return invitation.getGuests()
                .stream()
                .filter(guest -> Objects.nonNull(guest) && guest.getStatus() == type)
                .findFirst()
                .orElse(null);
    }

    private Invitation() {}

}
