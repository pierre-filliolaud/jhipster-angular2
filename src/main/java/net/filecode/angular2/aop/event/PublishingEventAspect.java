package net.filecode.angular2.aop.event;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;

import net.filecode.angular2.event.FooCreationEvent;

@Aspect
public class PublishingEventAspect {

	private ApplicationEventPublisher eventPublisher;
	
	public PublishingEventAspect(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	@Pointcut("execution(* net.filecode.angular2.service.*.save(..))")
	public void repositoryMethods() {}

	//	   @Pointcut("execution(* *..insert*(..))")
	//	   public void firstLongParamMethods() {}

	@Pointcut("repositoryMethods()")
	public void entityCreationMethods() {}

	@AfterReturning(value = "repositoryMethods()", returning = "entity")
	public void logMethodCall(JoinPoint jp, Object entity) throws Throwable {
		eventPublisher.publishEvent(new FooCreationEvent(this, entity));
	}

}
