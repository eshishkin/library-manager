package org.eshishkin.edu.librarymanager.portal.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.eshishkin.edu.librarymanager.portal.exception.UserAlreadyExistException;
import org.eshishkin.edu.librarymanager.portal.model.User;
import org.eshishkin.edu.librarymanager.portal.service.UserService;
import org.eshishkin.edu.librarymanager.portal.ui.component.RegistrationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Route("register")
public final class RegistrationView extends VerticalLayout {
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationView.class);

    @Autowired
    private UserService userService;

    private final Binder<User> binder = new Binder<>(User.class);

    public RegistrationView() {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        RegistrationForm form = new RegistrationForm(user -> {
            try {
                userService.create(user);
                UI.getCurrent().navigate(ReaderListView.class);
            } catch (UserAlreadyExistException ex) {
                Notification.show("User with such email already exists");
            } catch (RuntimeException ex) {
                //Do not know how to avoid that crap. I did not find how to set central error handler for all errors
                Notification.show("Unexpected exception: " + ex.getMessage());
            }
        });
        form.setMaxWidth("400px");

        add(new H2("Registration"));
        add(form);
    }
}


