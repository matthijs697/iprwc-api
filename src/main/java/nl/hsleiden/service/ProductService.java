package nl.hsleiden.service;

import nl.hsleiden.model.Product;
import nl.hsleiden.model.User;
import nl.hsleiden.persistence.ProductDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.ForbiddenException;
import java.util.Collection;

@Singleton
public class ProductService extends BaseService<Product> {

    private final ProductDAO dao;

    @Inject
    public ProductService(ProductDAO dao) {
        this.dao = dao;
    }

    public Product getProductById(long id) {
        return dao.findProductById(id);
    }

    public Collection getAllProducts() {
        return dao.findAllProducts();
    }

    public long updateOrCreateProduct(User auth, Product product) {
        if (!auth.hasRole("ADMIN")) {
            throw new ForbiddenException();
        }
        return dao.updateOrCreateProduct(product);
    }

    public void deleteProduct(long id) {
//        if (!auth.hasRole("ADMIN")) {
//            throw new ForbiddenException();
//        }
        Product product = getProductById(id);
        dao.deleteProduct(product);
    }

}
