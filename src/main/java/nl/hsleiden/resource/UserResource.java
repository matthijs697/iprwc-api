package nl.hsleiden.resource;

import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.model.User;
import nl.hsleiden.service.UserService;

@Singleton
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService service;
    
    @Inject
    public UserResource(UserService service) {
        this.service = service;
    }
    
    @GET
    @UnitOfWork
    @RolesAllowed("GUEST")
    public Collection listUsers() {
        return service.getAllUsers();
    }
    
    @GET
    @UnitOfWork
    @Path("/{id}")
    @RolesAllowed("GUEST")
    public User getUser(@PathParam("id") int id) {
        return service.findUserById(id);
    }
    
    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(@Valid User user) {
        service.addUser(user);
    }
    
    @PUT
    @UnitOfWork
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("GUEST")
    public void updateUser(@PathParam("id") int id, @Auth User authenticator, User user) {
        service.updateUser(authenticator, id, user);
    }
    
    @DELETE
    @UnitOfWork
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public void deleteUser(@PathParam("id") int id) {
        service.deleteUser(id);
    }
    
    @GET
    @UnitOfWork
    @Path("/me")
    public User authenticateUser(@Auth User authenticator) {
        return authenticator;
    }
}
