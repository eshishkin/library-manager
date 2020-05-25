package org.eshishkin.edu.librarymanager.portal.ui.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import javax.annotation.PostConstruct;
import org.eshishkin.edu.librarymanager.portal.model.User;
import org.eshishkin.edu.librarymanager.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("readers")
public final class ReaderListView extends Div {

    @Autowired
    private UserService userRepository;

    @PostConstruct
    public void init() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        Grid<User> users = createTable();
        users.setDataProvider(new ListDataProvider<>(userRepository.search()));

        layout.add(new Label("Readers"));
        layout.add(users);

        add(layout);
    }

    private Grid<User> createTable() {
        Grid<User> users = new Grid<>();
        users.setWidthFull();

        users.setHeightByRows(true);
        users.setRowsDraggable(true);

        users.addColumn(User::getFirstName).setHeader("First Name").setResizable(true).setSortable(true);
        users.addColumn(User::getLastName).setHeader("Last Name").setResizable(true).setSortable(true);
        users.addColumn(User::getEmail).setHeader("Email").setResizable(true).setSortable(true);
        users.addColumn(User::getGender).setHeader("Gender");
        users.addComponentColumn(user -> new RouterLink("View", ReaderView.class, user.getId()));

        return users;
    }


}


