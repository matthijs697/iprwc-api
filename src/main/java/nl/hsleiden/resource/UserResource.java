package nl.hsleiden.resource;

import com.google.inject.Singleton;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.model.User;
import nl.hsleiden.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

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
    @RolesAllowed("ADMIN")
    public Collection listUsers(@Auth User user) {
        return service.getAllUsers(user);
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
    public User createUser(@Valid User user) {
        return service.addUser(user);
    }
    
    @PUT
    @UnitOfWork
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "GUEST"})
    public User updateUser(@PathParam("id") int id, @Auth User authenticator, User user) {
        return service.updateUser(authenticator, id, user);
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
