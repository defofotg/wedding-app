package com.enai.weddingapp.domain.invitation.model;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Georges DEFO
 * @date 26/02/2023
 */
public class Guest {
    UUID id;
    String firstName;
    String lastName;
    GuestStatus status;

    public Guest(final UUID id, final String firstName, final String lastName, final GuestStatus status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UUID getId() {
        return id;
    }

    public GuestStatus getStatus() {
        return status;
    }

    public String getFullname() {
        return getLastName().concat(" ")
                .concat(getFirstName())
                .toUpperCase()
                .trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(id, guest.id) && Objects.equals(firstName, guest.firstName) && Objects.equals(lastName, guest.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
