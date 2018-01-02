package nl.hsleiden.resource;

import io.dropwizard.hibernate.UnitOfWork;
import nl.hsleiden.service.ProductService;

import javax.inject.Inject;
import javax.inject.Singleton;
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
    public Collection getAllProducts() {
        return service.getAllProducts();
    }

    @DELETE
    @UnitOfWork
    @Path("/{id}")
    public void deleteProduct(@PathParam("id") long id) {
        service.deleteProduct(id);
    }

}
