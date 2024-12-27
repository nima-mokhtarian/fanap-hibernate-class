package com.fanap.service;

import com.fanap.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getById(long id);

    void save(Book author);

    void update(Book author);

    void delete(Book author);
}
