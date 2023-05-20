package kongkawdee;

import java.util.*;
import java.time.*;
import java.sql.*;
import java.util.logging.*;
import java.text.*;

public class LibraryManagement {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // *99*//
    public void clearTable() {
        Connection connection = LibraryConnection.connect();
        Statement statement;

        try {
            statement = connection.createStatement();
            String sql = "DELETE * FROM bookshelf";
            ResultSet results = statement.executeQuery(sql);
            System.out.println("All data has been deleted");

        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listAllBook() {
        Connection connection = LibraryConnection.connect();
        Statement statement;

        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM bookshelf";
            ResultSet results = statement.executeQuery(sql);

            while (results.next()) {
                String title = results.getString(2);
                String type = results.getString(3);
                String additionalInfo = getAdditionalInfo(results.getInt(5));
                Timestamp moreInfo = moreInfo(results.getTimestamp(7));
                String evenmoreInfo = evenmoreInfo(results.getString(8));

                if (moreInfo == null || evenmoreInfo == null) {
                    System.out.println("|| Title: " + title + "  Type: " + type + additionalInfo);
                    // Debug
                    // System.out.println(results.getString(1) + " " + results.getString(2) + " " +
                    // results.getString(3) + " " + results.getString(4) + " " +
                    // results.getString(5) + " " + results.getString(6) + " " +
                    // results.getString(7) + " " + results.getString(8));
                } else
                    System.out.println(
                            "|| Title: " + title + "  Type: " + type + additionalInfo + "  " + moreInfo + evenmoreInfo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String getAdditionalInfo(int price) {
        if (price == 0) {
            return "  -Borrowable Book-";
        } else {
            return "  Price: " + price;
        }
    }

    private static Timestamp moreInfo(Timestamp date) {
        if (date != null) {
            return date;
        } else {
            return null;
        }
    }

    private static String evenmoreInfo(String name) {
        if (name == null) {
            return null;
        } else {
            return "  Borrower: " + name;
        }
    }

    // *2*//
    public void registerBook(Book book) {
        Connection connection = LibraryConnection.connect();
        String sql = "INSERT INTO bookshelf VALUES(?,?,?,?,?,?,?,?) ";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getSerial());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getType());
            preparedStatement.setBoolean(4, book.isBorrowable());
            preparedStatement.setInt(5, book.getPrice());
            preparedStatement.setString(6, book.getSeries());
            preparedStatement.setString(7, book.getBorrowdate());
            preparedStatement.setString(8, book.getBorrower());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // *3*//
    public void removeBook(String name) {
        Connection connection = LibraryConnection.connect();
        String sql1 = "SELECT * FROM bookshelf WHERE title = ?";
        String sql2 = "DELETE FROM bookshelf WHERE serial = ?";
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        try {
            preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, name);
            ResultSet results = preparedStatement1.executeQuery();

            List<String> books = new ArrayList<>();

            while (results.next()) {
                String bookInfo = results.getString(1) + " " + results.getString(2);
                books.add(bookInfo);
            }

            if (books.isEmpty()) {
                System.out.println("No such book");
            } else {
                System.out.println("Books found:");
                for (String book : books) {
                    System.out.println(book);
                }
                System.out.print("Which one do you want to remove? Enter the serial number: ");
                Scanner scanner = new Scanner(System.in);
                int serial = scanner.nextInt();

                preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, serial);
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // *4*//
    public void searchBook(String name) {
        Connection connection = LibraryConnection.connect();
        String sql = "SELECT * FROM bookshelf WHERE title = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                String title = results.getString(2);
                String type = results.getString(3);
                String additionalInfo = getAdditionalInfo(results.getInt(5));
                Timestamp moreInfo = moreInfo(results.getTimestamp(7));
                String evenmoreInfo = evenmoreInfo(results.getString(8));

                if (moreInfo == null || evenmoreInfo == null) {
                    System.out.println("Title: " + title + "  Type: " + type + additionalInfo);
                } else
                    System.out.println(
                            "Title: " + title + "  Type: " + type + additionalInfo + "  " + moreInfo + evenmoreInfo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // *5*//
    public void borrowBook(String name, String borrower) {
        Connection connection = LibraryConnection.connect();
        String sql1 = "SELECT * FROM bookshelf WHERE title = ? AND borrowable = true";
        String sql2 = "UPDATE bookshelf SET borrowable = ?, borrowdate = ?, borrower = ? WHERE serial = ?";
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        try {
            preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, name);
            ResultSet results = preparedStatement1.executeQuery();

            List<String> books = new ArrayList<>();
            List<Integer> serialNumbers = new ArrayList<>();

            while (results.next()) {
                int serialNumber = results.getInt(1);
                String bookInfo = serialNumber + ". " + results.getString(2);
                books.add(bookInfo);
                serialNumbers.add(serialNumber);
            }

            if (books.isEmpty()) {
                System.out.println("No such book");
            } else {
                System.out.println("Books found:");
                for (String book : books) {
                    System.out.println(book);
                }
                System.out.print("Enter the serial number of the book you want to borrow: ");
                Scanner scanner = new Scanner(System.in);
                int selectedSerial = scanner.nextInt();

                if (serialNumbers.contains(selectedSerial)) {
                    preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setBoolean(1, false);
                    preparedStatement2.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    preparedStatement2.setString(3, borrower);
                    preparedStatement2.setInt(4, selectedSerial);
                    preparedStatement2.executeUpdate();
                    System.out.println("Book borrowed successfully!");
                } else {
                    System.out.println("Invalid serial number");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // *6*//
    public void returnBook(String name) {
        Connection connection = LibraryConnection.connect();
        String sql1 = "SELECT * FROM bookshelf WHERE title = ? AND borrowable = false";
        String sql2 = "UPDATE bookshelf SET borrowable = ?, borrowdate = ?, borrower = ? WHERE serial = ?";
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        try {
            preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, name);
            ResultSet results = preparedStatement1.executeQuery();

            List<String> books = new ArrayList<>();
            List<Integer> serialNumbers = new ArrayList<>();

            while (results.next()) {
                int serialNumber = results.getInt("serial");
                String bookInfo = serialNumber + ". " + results.getString("title") + " (Borrower: "
                        + results.getString("borrower") + ")";
                books.add(bookInfo);
                serialNumbers.add(serialNumber);
            }

            if (books.isEmpty()) {
                System.out.println("No such book");
            } else {
                System.out.println("Books found:");
                for (String book : books) {
                    System.out.println(book);
                }
                System.out.print("Which one do you want to return? Enter the serial number: ");
                Scanner scanner = new Scanner(System.in);
                int selectedSerial = scanner.nextInt();

                if (serialNumbers.contains(selectedSerial)) {
                    preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setBoolean(1, true);
                    preparedStatement2.setNull(2, Types.TIMESTAMP);
                    preparedStatement2.setNull(3, Types.VARCHAR);
                    preparedStatement2.setInt(4, selectedSerial);
                    preparedStatement2.executeUpdate();
                    System.out.println("Book returned successfully!");
                } else {
                    System.out.println("Invalid serial number");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // *7*//
    public void sellBook(String name) {
        Connection connection = LibraryConnection.connect();
        String sql1 = "SELECT * FROM bookshelf WHERE title = ?";
        String sql2 = "DELETE FROM bookshelf WHERE serial = ?";
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        try {
            preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, name);
            ResultSet results = preparedStatement1.executeQuery();

            List<String> books = new ArrayList<>();
            List<Integer> serialNumbers = new ArrayList<>();

            while (results.next()) {
                int serialNumber = results.getInt("serial");
                String bookInfo = serialNumber + ". " + results.getString("title");
                books.add(bookInfo);
                serialNumbers.add(serialNumber);
            }

            if (books.isEmpty()) {
                System.out.println("No such book");
            } else {
                System.out.println("Books found:");
                for (String book : books) {
                    System.out.println(book);
                }
                System.out.print("Which one do you want to buy? Enter the serial number: ");
                Scanner scanner = new Scanner(System.in);
                int selectedSerial = scanner.nextInt();

                if (serialNumbers.contains(selectedSerial)) {
                    preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setInt(1, selectedSerial);
                    preparedStatement2.executeUpdate();
                    System.out.println("Book bought successfully!");
                } else {
                    System.out.println("Invalid serial number");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // *8*//
    public void listAllBorrowedBook() {
        Connection connection = LibraryConnection.connect();
        Statement statement;

        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM bookshelf WHERE borrowable = true";
            ResultSet results = statement.executeQuery(sql);

            while (results.next()) {
                while (results.next()) {
                    int serial = results.getInt(1);
                    String title = results.getString(2);
                    String type = results.getString(3);
                    System.out.println("|| Serial: " + serial + "  Title: " + title + "  Type: " + type);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // *9*//
    public void listAllSellingBook() {
        Connection connection = LibraryConnection.connect();
        Statement statement;

        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM bookshelf WHERE borrowable = false AND borrower IS NULL";
            ResultSet results = statement.executeQuery(sql);

            while (results.next()) {
                int serial = results.getInt(1);
                String title = results.getString(2);
                String type = results.getString(3);
                int price = results.getInt(5);
                System.out.println(
                        "|| Serial: " + serial + "  Title: " + title + "  Type: " + type + "  Price: " + price);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // *10*//
    public void changeBookStatus(String name) {
        Connection connection = LibraryConnection.connect();
        String sql1 = "SELECT * FROM bookshelf WHERE title = ? AND borrower IS NULL";
        String sql2 = "UPDATE bookshelf SET borrowable = ?, borrowdate = ?, borrower = ?, price = ? WHERE serial = ?";
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        try {
            preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, name);
            ResultSet results = preparedStatement1.executeQuery();

            List<String> books = new ArrayList<>();
            List<Integer> serialNumbers = new ArrayList<>();

            while (results.next()) {
                int serialNumber = results.getInt("serial");
                String bookInfo = serialNumber + ". " + results.getString("title");
                books.add(bookInfo);
                serialNumbers.add(serialNumber);
            }

            if (books.isEmpty()) {
                System.out.println("No such book");
            } else {
                System.out.println("Books found:");
                for (String book : books) {
                    System.out.println(book);
                }
                System.out.print("Which one do you want to change the status? Enter the serial number: ");
                Scanner scanner = new Scanner(System.in);
                int selectedSerial = scanner.nextInt();

                if (serialNumbers.contains(selectedSerial)) {
                    boolean currentStatus = isBorrowable(connection, selectedSerial);
                    boolean newStatus = !currentStatus;
                    preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setBoolean(1, newStatus);
                    preparedStatement2.setString(2, null);
                    preparedStatement2.setString(3, null);

                    if (!newStatus) {
                        System.out.print("Enter the price of the book: ");
                        double price = scanner.nextDouble();
                        preparedStatement2.setDouble(4, price);
                    } else {
                        preparedStatement2.setNull(4, Types.DOUBLE);
                    }

                    preparedStatement2.setInt(5, selectedSerial);
                    preparedStatement2.executeUpdate();
                    System.out.println("Status of the book changed successfully!");
                } else {
                    System.out.println("Invalid serial number");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isBorrowable(Connection connection, int serialNumber) throws SQLException {
        String sql = "SELECT borrowable FROM bookshelf WHERE serial = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, serialNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("borrowable");
            }
        }
        return false;
    }

    public static int serialGenerator() {
        Connection connection = LibraryConnection.connect();
        Statement statement;

        try {
            statement = connection.createStatement();
            String sql = "SELECT serial FROM bookshelf";
            ResultSet results = statement.executeQuery(sql);

            Set<Integer> existingSerials = new HashSet<>();
            while (results.next()) {
                int serial = results.getInt("serial");
                existingSerials.add(serial);
            }

            Random rand = new Random();
            int serial;
            do {
                serial = rand.nextInt(999999999 - 100000000) + 100000000;
            } while (existingSerials.contains(serial));

            return serial;
        } catch (SQLException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

        // In case of an error, return a default value or handle the exception
        // accordingly
        return -1;
    }
}