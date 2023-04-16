package com.enai.weddingapp.infrastructure.invitation.mapper;

import com.enai.weddingapp.domain.invitation.model.Guest;
import com.enai.weddingapp.infrastructure.invitation.entity.GuestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    Guest toModel(GuestEntity entity);

    GuestEntity toEntity(Guest model);
}
