package br.com.andesson.taskmanager;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Component to provide access to the Spring ApplicationContext throughout the application.
 */
@Component
public class ApplicationContextLoad implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * Sets the ApplicationContext when it is initialized by the Spring framework.
     *
     * @param context the ApplicationContext object
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * Retrieves the ApplicationContext.
     *
     * @return the ApplicationContext object
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
