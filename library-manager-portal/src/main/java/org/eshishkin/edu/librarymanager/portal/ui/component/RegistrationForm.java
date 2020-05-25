package org.eshishkin.edu.librarymanager.portal.ui.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import java.util.function.Consumer;
import org.apache.commons.lang3.StringUtils;
import org.eshishkin.edu.librarymanager.portal.model.user.Gender;
import org.eshishkin.edu.librarymanager.portal.model.user.UserRegistrationRequest;
import static com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;

public final class RegistrationForm extends VerticalLayout {
    private static final String ALIGN_ITEMS = "align-items";

    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final TextField email = new TextField();
    private final PasswordField password = new PasswordField();
    private final ComboBox<Gender> gender = new ComboBox<>(StringUtils.EMPTY, Gender.values());

    private final Binder<UserRegistrationRequest> binder = new BeanValidationBinder<>(UserRegistrationRequest.class);

    private final Consumer<UserRegistrationRequest> onSuccessValidation;
    private final Consumer<BinderValidationStatus<UserRegistrationRequest>> onError;

    public RegistrationForm(Consumer<UserRegistrationRequest> onSuccessValidation) {
        this(onSuccessValidation, s -> {});
    }

    public RegistrationForm(Consumer<UserRegistrationRequest> onSuccessValidation,
                            Consumer<BinderValidationStatus<UserRegistrationRequest>> onError) {
        this.onSuccessValidation = onSuccessValidation;
        this.onError = onError;

        setAlignItems(CENTER);

        binder.setBean(new UserRegistrationRequest());
        binder.bindInstanceFields(this);

        add(preparePrepareForm());
        add(register());
    }

    private FormLayout preparePrepareForm() {
        FormLayout layout = new FormLayout();

        layout.setResponsiveSteps(new ResponsiveStep("100px", 1, LabelsPosition.TOP));

        layout.addFormItem(firstName, "First Name").getStyle().set(ALIGN_ITEMS, CENTER.toString());
        layout.addFormItem(lastName, "Last Name").getStyle().set(ALIGN_ITEMS, CENTER.toString());
        layout.addFormItem(gender, "Gender").getStyle().set(ALIGN_ITEMS, CENTER.toString());
        layout.addFormItem(email, "Email").getStyle().set(ALIGN_ITEMS, CENTER.toString());
        layout.addFormItem(password, "Password").getStyle().set(ALIGN_ITEMS, CENTER.toString());

        return layout;
    }

    private Button register() {
        return new Button("Register", event -> {
            BinderValidationStatus<UserRegistrationRequest> status = binder.validate();

            if (status.isOk()) {
                onSuccessValidation.accept(binder.getBean());
            } else {
                onError.accept(status);
            }
        });
    }
}
