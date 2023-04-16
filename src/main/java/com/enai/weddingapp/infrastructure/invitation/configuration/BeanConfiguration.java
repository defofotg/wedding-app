package com.enai.weddingapp.infrastructure.invitation.configuration;

import com.enai.weddingapp.WeddingApplication;
import com.enai.weddingapp.domain.invitation.repository.InvitationRepository;
import com.enai.weddingapp.domain.invitation.service.DomainInvitationService;
import com.enai.weddingapp.domain.invitation.service.InvitationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = WeddingApplication.class)
public class BeanConfiguration {

    @Bean
    InvitationService invitationService(final InvitationRepository invitationRepository) {
        return new DomainInvitationService(invitationRepository);
    }
}
