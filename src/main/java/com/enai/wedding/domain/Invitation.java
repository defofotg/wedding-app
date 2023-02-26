package com.enai.wedding.domain;

import java.util.Objects;

/**
 * @author Georges DEFO
 * @date 26/02/2023
 */
public class Invitation {

    private Long id;
    private String login;
    private String secret;
    private Guest mainGuest;
    private Guest attendant;
    private InvitationStatus status;

    public Invitation(final String login, final String secret, final Guest mainGuest) {
        this.login = login;
        this.secret = secret;
        this.mainGuest = mainGuest;
        this.status = InvitationStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSecret() {
        return secret;
    }

    public Guest getMainGuest() {
        return mainGuest;
    }

    public Guest getAttendant() {
        return attendant;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invitation invitation = (Invitation) o;
        return Objects.equals(id, invitation.id) && Objects.equals(login, invitation.login) &&
                Objects.equals(secret, invitation.secret) && Objects.equals(mainGuest, invitation.mainGuest)
                && Objects.equals(attendant, invitation.attendant) && Objects.equals(status, invitation.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, secret, mainGuest, attendant, status);
    }

    private Invitation() {}

}
