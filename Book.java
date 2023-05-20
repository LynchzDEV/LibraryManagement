package kongkawdee;

public abstract class Book {
    private int serial;
    private String title;
    private String type;
    private boolean borrowable;
    private int price;
    private String series;
    private String borrowdate;
    private String borrower;

    public Book(String title, String type, boolean borrowable, int price, String series, String borrowdate, String borrower) {
        this.serial = LibraryManagement.serialGenerator();
        this.title = title;
        this.type = type;
        this.borrowable = borrowable;
        this.price = price;
        this.series = series;
        this.borrowdate = borrowdate;
        this.borrower = borrower;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBorrowable() {
        return borrowable;
    }

    public void setBorrowable(boolean borrowable) {
        this.borrowable = borrowable;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getBorrowdate() {
        return borrowdate;
    }

    public void setBorrowdate(String borrowdate) {
        this.borrowdate = borrowdate;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    
}
