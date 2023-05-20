package kongkawdee;

import java.util.*;

public class Library {

    public static void main(String[] args) {
        LibraryManagement lm = new LibraryManagement();
        int option = 0, serial, price;
        String title, type, series, borrowdate, borrower;
        Scanner scan = new Scanner(System.in);

        while (option != 11) {
            System.out.println("");
            System.out.println("Main Menu");
            System.out.println("1. Display All Books");
            System.out.println("2. Regester New Book");
            System.out.println("3. Remove Book From Library");
            System.out.println("4. Search Book");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Buy Book");
            System.out.println("8. List All Borrowed Books");
            System.out.println("9. List All Selling Books");
            System.out.println("10. Change status of book");
            System.out.print("Enter your choice: ");
            option = scan.nextInt();
            scan.nextLine();
            System.out.println("");

            switch (option) {
                case 1:
                    System.out.println("||====================================================||");
                    System.out.println("||                  All Books List                    ||");
                    lm.listAllBook();
                    System.out.println("||====================================================||");
                    break;
                case 2:
                    System.out.print("Enter Book's Title: ");
                    title = scan.nextLine();
                    System.out.print("Enter Book's Type: ");
                    type = scan.nextLine();
                    System.out.print("Enter Book's Series: ");
                    series = scan.nextLine();
                    System.out.print("Is it Borrowable?: ");
                    String isBorowableString = scan.nextLine();
                    boolean isBorowable;
                    if (isBorowableString.equals("Yes") || isBorowableString.equals("yes") || isBorowableString.equals("YES")) {
                        isBorowable = true;
                    } else {
                        isBorowable = false;
                    }
                    if (isBorowable == true) {
                        lm.registerBook(new Borowable(title, type, series));
                    } else {
                        System.out.print("Enter Book's Price: ");
                        price = scan.nextInt();
                        lm.registerBook(new Selling(title, type, price, series, null, null));
                    }
                    System.out.println("=====================");
                    System.out.println("Registeration Success");
                    System.out.println("=====================");
                    break;
                case 3:
                    System.out.print("Enter Book's Name: ");
                    title = scan.nextLine();
                    lm.removeBook(title);
                    System.out.println("==============");
                    System.out.println("Remove Success");
                    System.out.println("==============");
                    break;
                case 4:
                    System.out.print("Enter Book's Name: ");
                    title = scan.nextLine();
                    lm.searchBook(title);
                    System.out.println("");
                    break;
                case 5:
                    System.out.print("Enter Book's Name: ");
                    title = scan.nextLine();
                    System.out.print("Enter Borrower's Name: ");
                    borrower = scan.nextLine();
                    lm.borrowBook(title, borrower);
                    System.out.println("");

                    break;
                case 6:
                    System.out.print("Enter Book's Name: ");
                    title = scan.nextLine();
                    lm.returnBook(title);
                    System.out.println("");
                    break;
                case 7:
                    System.out.print("Enter Book's Name: ");
                    title = scan.nextLine();
                    lm.sellBook(title);
                    System.out.println("");
                    break;
                case 8:
                    System.out.println("||==========================================||");
                    System.out.println("||            Borrowed Books List           ||");
                    lm.listAllBorrowedBook();
                    System.out.println("||==========================================||");
                    break;
                case 9:
                    System.out.println("||==========================================||");
                    System.out.println("||              Selling Books List          ||");
                    lm.listAllSellingBook();
                    System.out.println("||==========================================||");
                    break;
                case 10:
                    System.out.print("Enter Book's Name: ");
                    title = scan.nextLine();
                    lm.changeBookStatus(title);
                    System.out.println("");
                    break;
                case 99:
                    lm.clearTable();
            }
        }
    }
}
