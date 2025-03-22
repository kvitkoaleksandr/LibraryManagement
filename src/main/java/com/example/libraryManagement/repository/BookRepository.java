package com.example.libraryManagement.repository;

import com.example.libraryManagement.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Book save(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    public List<Book> findAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public boolean deleteById(Long id) {
        Book book = entityManager.find(Book.class, id);
        if (book != null) {
            entityManager.remove(book);
            return true;
        }
        return false;
    }

    public List<Book> findByTitleOrAuthorOrGenre(String query) {
        return entityManager.createQuery(
                        "SELECT b FROM Book b WHERE LOWER(b.title) LIKE " +
                                ":query OR LOWER(b.author) LIKE :query OR LOWER(b.genre) LIKE :query", Book.class)
                .setParameter("query", "%" + query.toLowerCase() + "%")
                .getResultList();
    }
}