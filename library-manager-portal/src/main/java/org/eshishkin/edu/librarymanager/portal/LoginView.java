package org.eshishkin.edu.librarymanager.portal;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.eshishkin.edu.librarymanager.portal.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
public final class LoginView extends HorizontalLayout {

    @Autowired
    private LoginService loginService;

    public LoginView() {
        setHeightFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm form = new LoginForm();
        form.setForgotPasswordButtonVisible(false);
        form.addLoginListener(event -> loginService.login(event.getUsername(), event.getPassword()));

        add(form);
    }
}


