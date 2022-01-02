package Services;

import Models.Book;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

public class BooksManager {
    public void WriteBooks(ArrayList<Book> books){
        try (PrintWriter writer = new PrintWriter("Books.csv")) {

            StringBuilder sb = new StringBuilder();
            sb.append("Id,");
            sb.append("Name,");
            sb.append("Borrowed_By\r\n");
            for (Book book : books){
               sb.append(book.getId());
               sb.append(',');
               sb.append(book.getName());
               sb.append(',');
               sb.append(book.getBorrowedBy());
               sb.append("\r\n");

            }
            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Book> ReadBooks(){
        //parsing a CSV file into Scanner class constructor
        ArrayList<String>records = new ArrayList<String>();
        ArrayList<Book>books = new ArrayList<Book>();
        try {
            Scanner sc = new Scanner(new File("Books.csv"));
            sc.useDelimiter(",");   //sets the delimiter pattern
            sc.nextLine();
            while (sc.hasNextLine())  //returns a boolean value
            {
                books.add(getDataFromLine(sc.nextLine()));

                //System.out.print(sc.next() + " ");  //find and returns the next complete token from this scanner
            }
            sc.close();  //closes the scanner

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }
    private Book getDataFromLine(String line){
        int id;
        String name;
        String borrowed;
        Book result = null;
        try {
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");
            id = lineScanner.nextInt();
            name = lineScanner.next();
            borrowed = lineScanner.next();
            result = new Book(id,name,borrowed);
        }catch (Exception e){

        }
        return result;
    }

    public void AddBook(String bookName){
        var books = ReadBooks();
        var newBook = new Book(books.get(books.size()-1).getId() + 1,bookName,"Not_Borrowed");
        books.add(newBook);
        WriteBooks(books);
        RegisterEvent("Book added",newBook);
    }

    public void RemoveBook(int Id){
        var books = ReadBooks();
        books.removeIf(book -> book.getId() == Id);
        WriteBooks(books);
    }

    public void UpdateBook(int Id,String name){
        var books = ReadBooks();
        int indexOfBook = -1;
        for (int i = 0; i < books.size(); i++){
            if (books.get(i).getId() == Id){
               indexOfBook = i;
            }
        }
        if (indexOfBook != -1){
            var original = books.get(indexOfBook);
            books.set(indexOfBook,new Book(original.getId(),name,original.getBorrowedBy()));
        }
        WriteBooks(books);
    }

    public void BorrowBook(int Id,String userName){
        var books = ReadBooks();
        int indexOfBook = -1;
        for (int i = 0; i < books.size(); i++){
            if (books.get(i).getId() == Id){
                indexOfBook = i;
            }
        }
        Book original = null;
        if (indexOfBook != -1){
            String borrowedBy = books.get(indexOfBook).getBorrowedBy();
            if (borrowedBy.equals("Not_Borrowed")){
                original = books.get(indexOfBook);
                books.set(indexOfBook,new Book(original.getId(),original.getName(),userName));
            }
        }
        WriteBooks(books);
        RegisterEvent("Book borrowed by " + userName,original);
    }

    public void ReturnBook(int Id){
        var books = ReadBooks();
        int indexOfBook = -1;
        for (int i = 0; i < books.size(); i++){
            if (books.get(i).getId() == Id){
                indexOfBook = i;
            }
        }
       Book original = null;
        if (indexOfBook != -1){
            String borrowedBy = books.get(indexOfBook).getBorrowedBy();
            if (!borrowedBy.equals("Not_Borrowed")){
                original = books.get(indexOfBook);
                books.set(indexOfBook,new Book(original.getId(),original.getName(),"Not_Borrowed"));
            }
        }
        WriteBooks(books);
        RegisterEvent("Book returned ",original);
    }

    public ArrayList<Book> GetBooksByUser(String userName){
       var books = ReadBooks();
       ArrayList<Book> usersBooks = new ArrayList<Book>();
       for (Book book : books){
           if (book.getBorrowedBy().equals(userName)){
               usersBooks.add(book);
           }
       }
       return usersBooks;
    }

    public void RegisterEvent(String event, Book book){
        ArrayList<String>records = new ArrayList<String>();

        try {
            Scanner sc = new Scanner(new File("Records.csv"));
            while (sc.hasNextLine())
            {
                records.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try (PrintWriter writer = new PrintWriter("Records.csv")) {
            StringBuilder sb = new StringBuilder();
            sb.append(event +",");
            sb.append(" Time: " + LocalDateTime.now());
            sb.append(" Book Id: " + book.getId() + ",");
            sb.append(" Book name: " + book.getName() + ",");
            sb.append(" Borrowed by: " + book.getBorrowedBy() + "\r\n");
            records.add(sb.toString());
            StringBuilder sb1 = new StringBuilder();
            for (String record : records){
                sb1.append(record + "\r\n");
            }
            writer.write(sb1.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


}

