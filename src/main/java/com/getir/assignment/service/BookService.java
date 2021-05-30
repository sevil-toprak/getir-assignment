package com.getir.assignment.service;

import com.getir.assignment.controller.exception.AlreadyExistsException;
import com.getir.assignment.controller.exception.BookStockException;
import com.getir.assignment.controller.exception.NotFoundException;
import com.getir.assignment.controller.request.BookCreateRequest;
import com.getir.assignment.controller.request.BookStockUpdateRequest;
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

    private final BookRepository bookRepository;

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
                bookCreateRequest.getPrice(),
                bookCreateRequest.getStock());

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
        book.setStock(bookUpdateRequest.getStock());

        return bookRepository.save(book);
    }

    public Book updateBookStock(BookStockUpdateRequest bookStockUpdateRequest) {
        Book book = bookRepository.findById(bookStockUpdateRequest.getId()).orElseThrow(() -> new NotFoundException(bookStockUpdateRequest.getId()));
        book.setStock(bookStockUpdateRequest.getStock());

        return bookRepository.save(book);
    }

    public Book updateBookStockFromOrder(BookStockUpdateRequest bookStockUpdateRequest) {
        Book book = bookRepository.findById(bookStockUpdateRequest.getId()).orElseThrow(() -> new NotFoundException(bookStockUpdateRequest.getId()));
        long newStock = book.getStock() - bookStockUpdateRequest.getStock();
        if(newStock < 0)
            throw new BookStockException(book.getId());

        book.setStock(newStock);

        book.setStock(newStock);
        return bookRepository.save(book);
    }

    public void deleteBook(String bookId) {
        if (bookRepository.findById(bookId).isEmpty()) {
            throw new NotFoundException(bookId);
        }
        bookRepository.deleteById(bookId);
    }


}
