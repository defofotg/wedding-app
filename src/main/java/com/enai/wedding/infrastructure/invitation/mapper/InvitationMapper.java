package com.enai.wedding.infrastructure.invitation.mapper;

import com.enai.wedding.domain.invitation.model.Invitation;
import com.enai.wedding.infrastructure.invitation.entity.InvitationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = GuestMapper.class)
public interface InvitationMapper {

    @Mapping(target = "status", source = "status")
    Invitation toModel(InvitationEntity entity);
    //@Mapping(target = "myEnumValue", source = "myEnumValue", qualifiedByName = "fromEnum")
    InvitationEntity toEntity(Invitation model);

    /*
    @Named("toEnum")
    default MyEnum toEnum(String value) {
        return MyEnum.valueOf(value);
    }
    @Named("fromEnum")
    default String fromEnum(MyEnum value) {
        return value.name();
    } */

}
