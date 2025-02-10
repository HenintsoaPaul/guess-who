package itu.crypto;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventPublisherHolder implements ApplicationContextAware {
    @Getter
    private static ApplicationEventPublisher publisher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        publisher = applicationContext;
    }

}
