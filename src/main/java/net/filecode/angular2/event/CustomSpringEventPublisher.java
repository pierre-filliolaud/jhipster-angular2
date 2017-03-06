package net.filecode.angular2.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CustomSpringEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public CustomSpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void doStuffAndPublishAnEvent(final String message) {
		System.out.println("Publishing custom event. ");
		CustomEvent customSpringEvent = new CustomEvent(this, message);
		applicationEventPublisher.publishEvent(customSpringEvent);
	}

}
