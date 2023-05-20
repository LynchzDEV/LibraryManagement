package kongkawdee;

public class Selling extends Book {
    
    private String selldate;
    private String seller;

    public Selling(String title, String type, int price, String series, String selldate, String seller) {
        super(title, type, false, price, series, selldate, seller);
        this.selldate = selldate;
        this.seller = seller;
    }

    public String getSelldate() {
        return selldate;
    }

    public void setSelldate(String selldate) {
        this.selldate = selldate;
    }
    
    public String getSeller() {
        return seller;
    }
    
    public void setSeller(String seller) {
        this.seller = seller;
    }
    
    @Override
    public String toString() {
        return "Selling{" + "selldate=" + selldate + ", seller=" + seller + '}';
    }
}
