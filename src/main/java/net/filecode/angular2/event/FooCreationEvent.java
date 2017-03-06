package net.filecode.angular2.event;

import org.springframework.context.ApplicationEvent;

public class FooCreationEvent extends ApplicationEvent {
	
	private Object entity;
	
	public FooCreationEvent(Object source, Object entity) {
		super(source);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}

}
