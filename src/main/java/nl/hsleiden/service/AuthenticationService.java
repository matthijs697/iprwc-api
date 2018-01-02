package nl.hsleiden.service;

import java.util.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;

@Singleton
public class AuthenticationService implements Authenticator<BasicCredentials, User>, Authorizer<User> {
    private final UserDAO userDAO;
    
    @Inject
    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @UnitOfWork
    public Optional<User> authenticate(BasicCredentials credentials) {
        User user = userDAO.findUserByEmail(credentials.getUsername());
        
        if (user != null && user.getPassword().equals(credentials.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public boolean authorize(User user, String roleName) {
        return user.hasRole(roleName);
    }
}
