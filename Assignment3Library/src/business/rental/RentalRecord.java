package business.rental;

import business.items.Book;
import business.org.Library;
import business.people.Customer;
import java.time.LocalDateTime;

public class RentalRecord {
    private static int COUNTER = 1;
    private final int id;
    private Book book;
    private Customer customer;
    private Library library;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private RentalStatus status;

    public RentalRecord() { this.id = COUNTER++; }

    public int getId() { return id; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Library getLibrary() { return library; }
    public void setLibrary(Library library) { this.library = library; }
    public LocalDateTime getRentedAt() { return rentedAt; }
    public void setRentedAt(LocalDateTime rentedAt) { this.rentedAt = rentedAt; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public void setReturnedAt(LocalDateTime returnedAt) { this.returnedAt = returnedAt; }
    public RentalStatus getStatus() { return status; }
    public void setStatus(RentalStatus status) { this.status = status; }

    @Override public String toString() {
        return "Rental#" + id + " " + (book!=null?book.getName():"?");
    }
}
