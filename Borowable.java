package kongkawdee;

public class Borowable extends Book{

    public Borowable(String title, String type, String series) {
        super(title, type, true, 0, series, null, null);
    }
}
