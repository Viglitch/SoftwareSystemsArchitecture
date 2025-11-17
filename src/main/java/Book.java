import java.util.List;
import java.util.Queue;

public class Book {
    int bookID;
    String title;
    String author;
    boolean isAvailable;
    Queue<Application> applicationBuffer;
    //TODO: какая именно очередь?

    public void addToBuffer(Application app){
        applicationBuffer.add(app);
    }

    public void removeFromBuffer(int id){
        //TODO: удаляет заявку с данным id
    }

    public List<Application> sortApplications(){
        List<Application> res;
        //TODO: отсортировать по приоритету
        return res;
    }
}
