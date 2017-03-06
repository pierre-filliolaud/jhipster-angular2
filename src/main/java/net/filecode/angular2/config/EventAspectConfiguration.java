package net.filecode.angular2.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

import io.github.jhipster.config.JHipsterConstants;
import net.filecode.angular2.aop.event.PublishingEventAspect;

@Configuration
@EnableAspectJAutoProxy
public class EventAspectConfiguration {

    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public PublishingEventAspect publishingEventAspect(ApplicationEventPublisher eventPublisher) {
        return new PublishingEventAspect(eventPublisher);
    }
}
