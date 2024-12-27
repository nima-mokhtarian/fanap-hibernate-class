package com.fanap.service;

import com.fanap.model.Author;
import com.fanap.util.SFUtil;
import org.hibernate.Hibernate;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    @Override
    public List<Author> getAll() {
        return SFUtil.runHibernateCode(s -> {
            List<Author> authorList = s.createQuery("from Author", Author.class).list();
            authorList.forEach( a -> Hibernate.initialize(a.getBooks()));
            return authorList;
        }, true);
    }

    @Override
    public Author getById(long id) {
        return SFUtil.runHibernateCode(s -> s.get(Author.class, id), true);
    }

    @Override
    public void save(Author author) {
        SFUtil.runHibernateCode(s -> {
            s.persist(author);
            return null;
        }, true);
    }

    @Override
    public void update(Author author) {
        SFUtil.runHibernateCode(s ->s.merge(author), true);
    }

    @Override
    public void delete(Author author) {
        SFUtil.runHibernateCode(s -> {
            s.remove(author);
            return null;
        }, true);
    }
}
