package com.enai.wedding.infrastructure.authentication.configuration;

import com.enai.wedding.WeddingApplication;
import com.enai.wedding.domain.authentication.service.AuthenticationService;
import com.enai.wedding.domain.authentication.service.DomainAuthenticationService;
import com.enai.wedding.domain.invitation.service.InvitationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = WeddingApplication.class)
public class AuthBeanConfiguration {

    @Bean
    AuthenticationService authenticationService(final InvitationService invitationService) {
        return new DomainAuthenticationService(invitationService);
    }
}
