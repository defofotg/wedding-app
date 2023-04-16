package com.enai.weddingapp.infrastructure.invitation.mapper;

import com.enai.weddingapp.domain.invitation.model.Invitation;
import com.enai.weddingapp.infrastructure.invitation.entity.InvitationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = GuestMapper.class)
public interface InvitationMapper {

    @Mapping(target = "status", source = "status")
    Invitation toModel(InvitationEntity entity);
    InvitationEntity toEntity(Invitation model);

}
