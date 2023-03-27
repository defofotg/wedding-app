package com.enai.wedding.infrastructure.invitation.entity;

import com.enai.wedding.domain.invitation.model.Event;
import com.enai.wedding.domain.invitation.model.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Invitations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String login;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String secret;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "invitation_id")
    private Set<GuestEntity> guests = new HashSet<>();
    @ElementCollection(targetClass = Event.class)
    private Set<Event> events = new HashSet<>();
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationStatus status;
}
