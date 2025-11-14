package business;

import java.time.LocalDateTime;

/**
 * Records the lifecycle of each rental.
 */
public class RentalRecord {

    private static int nextId = 1;

    private int id;
    private Book book;
    private Customer customer;
    private Library library;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private RentalStatus status;

    public RentalRecord(Book book, Customer customer, Library library) {
        this.id = nextId++;
        this.book = book;
        this.customer = customer;
        this.library = library;
        this.rentedAt = LocalDateTime.now();
        this.status = RentalStatus.RENTED;
    }

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Library getLibrary() {
        return library;
    }

    public LocalDateTime getRentedAt() {
        return rentedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void markReturned() {
        this.returnedAt = LocalDateTime.now();
        this.status = RentalStatus.RETURNED;
    }
}
