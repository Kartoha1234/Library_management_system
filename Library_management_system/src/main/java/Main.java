import Models.Book;
import Services.BooksManager;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BooksManager bm = new BooksManager();
        String comand = "";
        Scanner scanner = new Scanner(System.in);
        while (!comand.equals("exit")){
            System.out.println("\r\n\r\nFor user:\r\n" +
                    "Type \"list\" to get list of all books\r\n" +
                    "Type \"borrow\" to borrow a book\r\n" +
                    "Type \"return\" to return a book\r\n" +
                    "Type \"my books\" to get all your books\r\n" +
                    "For library manager:\r\n" +
                    "Type \"add\" to add a book to library\r\n" +
                    "Type \"edit\" to edit a book in library\r\n" +
                    "Type \"delete\" to delete a book from library\r\n");
            comand = scanner.nextLine();
            switch (comand){
                case "list":{
                    var books = bm.ReadBooks();
                    for (Book book : books){
                        Boolean available = false;
                        if (book.getBorrowedBy().equals("Not_Borrowed")){
                            available = true;
                        }
                        System.out.println("Id: " + book.getId() + ", Name: " + book.getName() +", Available: " + available);
                    }
                    break;}
                case "borrow":{
                    var books = bm.ReadBooks();
                    System.out.println("Please type Id of book: ");
                    int Id = Integer.parseInt(scanner.nextLine());
                    System.out.println("Please type your name: ");
                    String userName = scanner.nextLine();
                    if (bm.GetBooksByUser(userName).size() < 5){
                        bm.BorrowBook(Id,userName);
                    }
                    break;}
                case "return":{
                    System.out.println("Please type Id of book: ");
                    int Id = Integer.parseInt(scanner.nextLine());
                    bm.ReturnBook(Id);
                    break;}
                case "my books":{
                    System.out.println("Please type your name: ");
                    String userName = scanner.nextLine();
                    var myBooks = bm.GetBooksByUser(userName);
                    for (Book book : myBooks){
                        System.out.println("Id: " + book.getId() + ", Name: " + book.getName());
                    }
                    break;}
                case "add":{
                    System.out.println("Please type name of book: ");
                    String bookName = scanner.nextLine();
                    bm.AddBook(bookName);
                    break;}
                case "edit":{
                    System.out.println("Please type Id of book: ");
                    int Id = Integer.parseInt(scanner.nextLine());
                    System.out.println("Please type name of book: ");
                    String bookName = scanner.nextLine();
                    bm.UpdateBook(Id,bookName);
                    break;}
                case "delete":{
                    System.out.println("Please type Id of book: ");
                    int Id = Integer.parseInt(scanner.nextLine());
                    bm.RemoveBook(Id);
                    break;}


            }
        }

    }

}
