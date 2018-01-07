package nl.hsleiden.persistence;

import io.dropwizard.hibernate.AbstractDAO;
import nl.hsleiden.model.Product;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProductDAO extends AbstractDAO<Product> {

    private final List<Product> products;

    @Inject
    public ProductDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        products = new ArrayList<>();
    }

    public Product findProductById(long id) {
        Optional<Product> result = products.stream()
                .filter(product -> product.getId() == id)
                .findAny();
        return result.orElse(get(id));
    }

    public List findAllProducts() {
        return list(namedQuery("Product.FIND_ALL"));
    }

    public Product updateOrCreateProduct(Product product) {
        return persist(product);
    }

    public void deleteProduct(Product product) {
        currentSession().delete(product);
        products.remove(product);
    }

}
