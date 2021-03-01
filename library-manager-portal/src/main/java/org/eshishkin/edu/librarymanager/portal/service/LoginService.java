package org.eshishkin.edu.librarymanager.portal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final static Logger LOG = LoggerFactory.getLogger(LoginService.class);

    public void login(String login, String password) {
        LOG.info(login);
        LOG.info(password);
    }

}
