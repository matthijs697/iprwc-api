package nl.hsleiden.resource;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.model.Product;
import nl.hsleiden.model.User;
import nl.hsleiden.service.ProductService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Singleton
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductService service;

    @Inject
    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GET
    @UnitOfWork
    public Collection listProducts() {
        return service.getAllProducts();
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Product getProduct(@PathParam("id") long id) {
        return service.getProductById(id);
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public void createProduct(@Auth User user, @Valid Product product) {
        service.updateOrCreateProduct(user, product);
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public void updateProduct(@Auth User user, Product product) {
        service.updateOrCreateProduct(user, product);
    }

    @DELETE
    @UnitOfWork
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public void deleteProduct(@PathParam("id") long id) {
        service.deleteProduct(id);
    }

}
