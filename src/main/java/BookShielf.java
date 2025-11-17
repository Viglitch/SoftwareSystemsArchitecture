import java.util.List;
import java.util.Map;

public class BookShielf {
    Map<Integer, Book > AllBooks;
    List<Reader> Readers;
    List<Librarian> Librarians;

    public void addBook(Book newBook){
        AllBooks.put(AllBooks.size(), newBook);
    }

    public void registerReader(Reader newReader){
        Readers.add(newReader);
    }

    public boolean processApplication(Application myApp){
        //TODO: логика заявок
        return true;
    }

    public List<Book> getAvailableBooks(){
        List<Book> res;
        //TODO: только свободные
        return res;
    }
}
