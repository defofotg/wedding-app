package com.enai.wedding.infrastructure.invitation.mapper;

import com.enai.wedding.domain.invitation.model.Guest;
import com.enai.wedding.infrastructure.invitation.entity.GuestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    Guest toModel(GuestEntity entity);

    GuestEntity toEntity(Guest model);
}
