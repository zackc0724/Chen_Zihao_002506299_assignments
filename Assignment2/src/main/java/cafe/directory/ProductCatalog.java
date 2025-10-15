package cafe.directory;

import com.cafe.model.Product;
import java.util.*;
import java.util.stream.Collectors;

public class ProductCatalog {
    private final List<Product> products = new ArrayList<>();

    public void add(Product p) { products.add(p); }
    public void remove(Product p) { products.remove(p); }
    public List<Product> getAll() { return Collections.unmodifiableList(products); }

    public Product getById(int id) {
        for (Product p : products) if (p.getId() == id) return p;
        return null;
    }

    public List<Product> findByName(String name) {
        String n = name.toLowerCase();
        return products.stream().filter(p -> p.getName().toLowerCase().equals(n)).collect(Collectors.toList());
    }
}
