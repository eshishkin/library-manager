package org.eshishkin.edu.librarymanager.portal.ui.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.eshishkin.edu.librarymanager.portal.model.User;
import org.eshishkin.edu.librarymanager.portal.model.user.Gender;
import org.eshishkin.edu.librarymanager.portal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Route("registration")
public final class RegistrationForm extends FormLayout {
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationForm.class);

    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final TextField email = new TextField();
    private final PasswordField password = new PasswordField();
    private final ComboBox<Gender> gender = new ComboBox<>(StringUtils.EMPTY, Gender.values());
//  ToDo try to use BeanValidationBinder
    private final Binder<User> binder = new Binder<>(User.class);

    @Autowired
    private UserService userService;

    public RegistrationForm() {
        binder.forField(firstName)
                .asRequired("FirstName is requiered")
                .bind(User::getFirstName, User::setFirstName);

        binder.bind(firstName, User::getFirstName, User::setFirstName);
        binder.bind(lastName, User::getLastName, User::setLastName);
        binder.bind(email, User::getEmail, User::setEmail);
        binder.bind(password, User::getPassword, User::setPassword);
        binder.bind(password, User::getPassword, User::setPassword);
        binder.bind(gender, User::getGender, User::setGender);


        binder.setBean(new User());

        lastName.setRequired(true);
        lastName.setRequiredIndicatorVisible(true);

        email.setRequired(true);
        email.setRequiredIndicatorVisible(true);

        lastName.setRequired(true);
        lastName.setRequiredIndicatorVisible(true);

        Button register = new Button("Register", event ->  {
            LOG.info("Event: {}", event);
            BinderValidationStatus<User> status = binder.validate();

            if (status.isOk()) {
                LOG.info("User is valid: {}", binder.getBean());
                userService.create(binder.getBean()).block();
            } else {
                LOG.info("User is invalid: {}", status.getFieldValidationErrors());
            }
        });

        addFormItem(firstName, "First Name");
        addFormItem(lastName, "Last Name");
        addFormItem(email, "Email");
        addFormItem(password, "Password");
        addFormItem(gender, "Gender");

        add(register);
    }

}
