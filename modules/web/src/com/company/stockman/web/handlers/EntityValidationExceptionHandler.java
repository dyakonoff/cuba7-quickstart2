package com.company.stockman.web.handlers;

import com.haulmont.cuba.core.global.validation.EntityValidationException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.exception.AbstractUiExceptionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component("stockman_EntityValidationExceptionHandler")
public class EntityValidationExceptionHandler extends AbstractUiExceptionHandler {

    public EntityValidationExceptionHandler() {
        super(EntityValidationException.class.getName());
    }

    @Override
    protected void doHandle(String className, String message, @Nullable Throwable throwable, UiContext context) {
        context.getNotifications()
                .create(Notifications.NotificationType.WARNING)
                .withCaption(throwable.getMessage())
                .withPosition(Notifications.Position.MIDDLE_CENTER)
                .show();
    }
}
