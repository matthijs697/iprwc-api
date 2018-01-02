package nl.hsleiden.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.dropwizard.hibernate.AbstractDAO;
import nl.hsleiden.model.User;
import org.hibernate.SessionFactory;

@Singleton
public class UserDAO extends AbstractDAO<User> {

    private final List<User> users;

    @Inject
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        users = new ArrayList<>();
    }

    public User findUserById(long id) {
        Optional<User> result = users.stream()
                .filter(user -> user.getId() == id)
                .findAny();
        return result.orElse(get(id));
    }

    public User findUserByEmail(String email) {
        Optional<User> result = users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
        return result.orElse(uniqueResult(namedQuery("User.FIND_BY_EMAIL").setParameter("email", email)));
    }

    public long updateOrCreateUser(User user) {
        User usr = (User) currentSession().merge(user);
        return usr.getId();
    }

    public void deleteUserById(int id) {
        currentSession().delete(id);
    }

    public List findAllUsers() {
        return list(namedQuery("User.FIND_ALL"));
    }
}
