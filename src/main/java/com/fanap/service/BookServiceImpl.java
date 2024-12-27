package com.fanap.service;

import com.fanap.model.Book;
import com.fanap.util.SFUtil;

import java.util.List;

public class BookServiceImpl implements BookService {
    public List<Book> getAll() {
        return SFUtil.runHibernateCode(s -> s.createQuery("from Book",  Book.class).list(), true);
    }

    @Override
    public Book getById(long id) {
        return SFUtil.runHibernateCode(s -> s.get(Book.class, id), true);
    }

    @Override
    public void save(Book book) {
        SFUtil.runHibernateCode(s -> {
            s.persist(book);
            return null;
        }, true);
    }

    @Override
    public void update(Book book) {
        SFUtil.runHibernateCode(s ->s.merge(book), true);
    }

    @Override
    public void delete(Book book) {
        SFUtil.runHibernateCode(s -> {
            s.remove(book);
            return null;
        }, true);
    }
}
