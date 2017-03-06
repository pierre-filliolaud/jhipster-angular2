package net.filecode.angular2.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SecondFooCreationEventListener implements ApplicationListener<FooCreationEvent> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
   @Override
   public void onApplicationEvent(FooCreationEvent event) {
	   log.info("Created second foo instance: " + event.getSource().toString() + " entity:" + event.getEntity().toString());
   }
}
