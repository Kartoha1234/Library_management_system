package Models;

public class Book {
    private int id;
    private String name;
    private String borrowedBy;

    public Book(int id, String name, String borrowedBy) {
        this.id = id;
        this.name = name;
        this.borrowedBy = borrowedBy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBorrowedBy(String borrowedBy){
        this.borrowedBy = borrowedBy;
    }
}
