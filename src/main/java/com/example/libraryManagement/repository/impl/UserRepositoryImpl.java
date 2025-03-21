package com.example.libraryManagement.repository.impl;

import com.example.libraryManagement.entity.User;
import com.example.libraryManagement.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    @Override
    public boolean existsByPassword(String encodedPassword) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.password = :password", Long.class)
                .setParameter("password", encodedPassword)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
}