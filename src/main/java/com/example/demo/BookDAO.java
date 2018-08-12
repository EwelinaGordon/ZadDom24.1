package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;


@Component
public class BookDAO {

    @PersistenceUnit
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public void save(Book book) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Book> readBooks(String sortCode) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String sortQuery = "";
        if(!StringUtils.isEmpty(sortCode)){
            sortQuery = "order by "  +sortCode+ " ASC";
        }
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b "+sortQuery , Book.class);
        List<Book> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    public Book readBookByID(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b where id=:numer", Book.class);
        query.setParameter("numer", id);
        List<Book> resultList = query.getResultList();
        entityManager.close();
        return resultList.get(0);
    }

    public void updateBook(Book book){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b where id=:numer", Book.class);
        query.setParameter("numer", book.getId());
        List<Book> resultList = query.getResultList();
        Book bookToUpdate = resultList.get(0);
        entityManager.getTransaction().begin();
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setPublishedDate(book.getPublishedDate());
        bookToUpdate.setIsbn(book.getIsbn());
        bookToUpdate.setCategory(book.getCategory());
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public void delete(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Book book = entityManager.find(Book.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(book);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
