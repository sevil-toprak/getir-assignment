package com.getir.assignment.repository;

import com.getir.assignment.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByName(String name);

    Optional<Book> findById(String id);

}