package com.fanap.service;

import com.fanap.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author getById(long id);

    void save(Author author);

    void update(Author author);

    void delete(Author author);
}
