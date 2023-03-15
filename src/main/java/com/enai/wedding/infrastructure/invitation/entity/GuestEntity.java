package com.enai.wedding.infrastructure.invitation.entity;

import com.enai.wedding.domain.invitation.model.GuestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "Guests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestEntity {
    @Id
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    String firstName;
    @NotBlank
    @Column(unique = true, nullable = false)
    String lastName;
    //@Enumerated(EnumType.STRING)
    GuestStatus status;
}
