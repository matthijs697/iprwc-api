package nl.hsleiden.service;

import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class UserService extends BaseService<User> {

    private final UserDAO dao;
    
    @Inject
    public UserService(UserDAO dao) {
        this.dao = dao;
    }
    
    public Collection getAllUsers(User user) {
        if (user.hasRole("ADMIN"))
            return dao.findAllUsers();
        return null;
    }
    
    public User findUserById(long id) {
        return requireResult(dao.findUserById(id));
    }
    
    public User addUser(User user) {
        return dao.updateOrCreateUser(user);
    }
    
    public User updateUser(User authenticator, int id, User user) {
        User oldUser = findUserById(id);
        if (!authenticator.hasRole("ADMIN")) {
            assertSelf(authenticator, oldUser);
        }
        return dao.updateOrCreateUser(user);
    }
    
    public void deleteUser(long id) {
        // Controleren of deze gebruiker wel bestaat
        User user = findUserById(id);
        dao.deleteUser(user);
    }
}
