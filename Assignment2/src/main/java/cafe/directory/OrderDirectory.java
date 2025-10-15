package cafe.directory;

import com.cafe.model.Order;
import java.util.*;

public class OrderDirectory {
    private final List<Order> orders = new ArrayList<>();

    public void add(Order o) { orders.add(o); }
    public void remove(Order o) { orders.remove(o); }
    public List<Order> getAll() { return Collections.unmodifiableList(orders); }

    public Order findById(int id) {
        for (Order o : orders) if (o.getOrderId() == id) return o;
        return null;
    }
}
