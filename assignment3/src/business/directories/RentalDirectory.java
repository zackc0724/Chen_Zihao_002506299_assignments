package business.directories;

import business.rental.RentalRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RentalDirectory {
    private final List<RentalRecord> list = new ArrayList<>();
    public List<RentalRecord> getList() { return list; }
    public RentalRecord add(RentalRecord r) { list.add(r); return r; }
    public List<RentalRecord> forCustomer(int customerId) {
        return list.stream().filter(r -> r.getCustomer()!=null && r.getCustomer().getCustomerId()==customerId)
            .collect(Collectors.toList());
    }
}
