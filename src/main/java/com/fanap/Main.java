package com.fanap;

import com.fanap.model.Author;
import com.fanap.model.Book;
import com.fanap.service.AuthorService;
import com.fanap.service.AuthorServiceImpl;
import com.fanap.service.BookService;
import com.fanap.service.BookServiceImpl;
import com.fanap.util.SFUtil;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {

    static BookService bookService = new BookServiceImpl();
    static AuthorService authorService = new AuthorServiceImpl();

    public static void main(String[] args) {
//        Author author = new Author();
//        author.setFirstName("nima");
//        author.setLastName("mokhtarian");
//        authorService.save(author);
//        for  (int i = 0; i < 10; i++) {
//            Book book = new Book();
//            book.setMainTitle("title " + i);
//            book.setAuthor(author);
//            bookService.save(book);
//        }
//        List<Author> authorList = authorService.getAll();
//        authorList.forEach(System.out::println);
//        authorList.stream().map(a -> a.getFirstName() + " - " + a.getLastName()).forEach(System.out::println);

        SFUtil.runHibernateCode(s -> {
            List<Book> bookList = bookService.getAll();
            System.out.println(bookList.size());
            System.out.println(bookList.getFirst());
            s.remove(bookList.getFirst());
            System.out.println(bookList.get(1));
            s.remove(bookList.get(1));
//            s.flush();
            throw new RuntimeException("an exception occurred");
        }, true);

    }

    private static void loadBookWithThread() {
        Configuration configuration = new Configuration();
        SessionFactory sessionFactory = configuration.configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Book book = session.get(Book.class, 1);
        session.lock(book, LockMode.PESSIMISTIC_WRITE);
        Runnable myThread = () ->
        {
            Thread.currentThread().setName("myThread");
            System.out.println(Thread.currentThread().getName() + " is running");
            Configuration configuration2 = new Configuration();
            SessionFactory sessionFactory2 = configuration2.configure().buildSessionFactory();
            Session session2 = sessionFactory2.openSession();
            Book book2 = session2.get(Book.class, 1);
            session2.lock(book2, LockMode.PESSIMISTIC_WRITE);
            System.out.println(book2.getMainTitle());
            session2.close();
            sessionFactory2.close();
        };

        Thread run = new Thread(myThread);

        // Starting the thread
        run.start();

        try {
            run.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(book.getMainTitle());
        session.close();
        sessionFactory.close();
    }
}