package com.getir.assignment.service;

import com.getir.assignment.controller.exception.AlreadyExistsException;
import com.getir.assignment.controller.exception.NotFoundException;
import com.getir.assignment.controller.request.BookCreateRequest;
import com.getir.assignment.controller.request.BookUpdateRequest;
import com.getir.assignment.domain.Book;
import com.getir.assignment.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final static Logger logger = LoggerFactory.getLogger(BookService.class);

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(BookCreateRequest bookCreateRequest) {
        if (bookRepository.findByName(bookCreateRequest.getName()).isPresent()) {
            throw new AlreadyExistsException(bookCreateRequest.getName());
        }

        Book book = new Book(bookCreateRequest.getName(),
                bookCreateRequest.getAuthor_name(),
                bookCreateRequest.getYear(),
                bookCreateRequest.getPrice());

        return bookRepository.save(book);
    }

    public Book getBookWithId(String bookId) {
        logger.debug("Book get with id: {}", bookId);
        return bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(bookId));
    }

    public Book getBookWithName(String bookName) {
        logger.debug("Book get with bookName: {}", bookName);
        return bookRepository.findByName(bookName).orElseThrow(() -> new NotFoundException(bookName));
    }

    public Page<Book> getAllBooks(Pageable pageable) {
        logger.debug("Get all books..");
        return bookRepository.findAll(pageable);
    }

    public Book updateBook(BookUpdateRequest bookUpdateRequest) {
        Book book = bookRepository.findById(bookUpdateRequest.getId()).orElseThrow(() -> new NotFoundException(bookUpdateRequest.getId()));

        book.setName(bookUpdateRequest.getName());
        book.setAuthor_name(bookUpdateRequest.getAuthor_name());
        book.setYear(bookUpdateRequest.getYear());
        book.setPrice(bookUpdateRequest.getPrice());

        return bookRepository.save(book);
    }

    public void deleteBook(String bookId) {
        if (bookRepository.findById(bookId).isEmpty()) {
            throw new NotFoundException(bookId);
        }
        bookRepository.deleteById(bookId);
    }


}
