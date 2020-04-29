package org.eshishkin.edu.librarymanager.portal.ui.view;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ReadOnlyHasValue;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.eshishkin.edu.librarymanager.portal.external.UserRepository;
import org.eshishkin.edu.librarymanager.portal.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Route("reader")
public final class ReaderView extends Div implements HasUrlParameter<String> {
    private static final Logger LOG = LoggerFactory.getLogger(ReaderView.class);

    @Autowired
    private UserRepository userRepository;

    private final Binder<User> binder = new Binder<>(User.class);

    public ReaderView() {
        Label firstName = new Label();
        Label lastName = new Label();
        Label email = new Label();
        Label gender = new Label();

        FormLayout layout = new FormLayout();
        layout.setResponsiveSteps(new ResponsiveStep("100px", 1, LabelsPosition.ASIDE));
        layout.addFormItem(firstName, "First Name");
        layout.addFormItem(lastName, "Last Name");
        layout.addFormItem(email, "Email");
        layout.addFormItem(gender, "Gender");


        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        content.add(layout);

        add(content);

        binder.bind(new ReadOnlyHasValue<>(firstName::setText), User::getFirstName, null);
        binder.bind(new ReadOnlyHasValue<>(lastName::setText), User::getLastName, null);
        binder.bind(new ReadOnlyHasValue<>(email::setText), User::getEmail, null);
        binder.bind(new ReadOnlyHasValue<>(gender::setText), u -> u.getGender().name(), null);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        User user = userRepository.getUser(parameter).block();
        LOG.info("Parameter {}", parameter);
        LOG.info("ReaderView {}", this.hashCode());
        binder.readBean(user);
    }
}


