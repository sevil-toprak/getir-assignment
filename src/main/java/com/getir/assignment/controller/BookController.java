package com.getir.assignment.controller;

import com.getir.assignment.controller.exception.InvalidDataException;
import com.getir.assignment.controller.request.BookCreateRequest;
import com.getir.assignment.controller.request.BookStockUpdateRequest;
import com.getir.assignment.controller.request.BookUpdateRequest;
import com.getir.assignment.domain.Book;
import com.getir.assignment.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(bindingResult);
        }

        Book book = bookService.createBook(bookCreateRequest);
        logger.debug("Book is created with id: {} and name:{}", book.getId(), book.getName());

        return ResponseEntity.ok(book);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookWithId(@PathVariable String bookId) {
        Book book = bookService.getBookWithId(bookId);
        logger.debug("Book is returned with id:{}", book.getId());

        return ResponseEntity.ok(book);
    }

    @GetMapping("/getWithBookName/{bookName}")
    public ResponseEntity<Book> getBookWithName(@PathVariable String bookName) {
        Book book = bookService.getBookWithName(bookName);
        logger.debug("Book is returned with name:{}", book.getName());

        return ResponseEntity.ok(book);
    }

    @GetMapping("/")
    public ResponseEntity<Page<Book>> getAllBooks(@PageableDefault(size = 20) Pageable pageable) {
        Page<Book> bookPage = bookService.getAllBooks(pageable);
        logger.debug("All books are returned with pageSize:{} and pageNumber: {}", pageable.getPageSize(), pageable.getPageNumber());

        return ResponseEntity.ok(bookPage);
    }

    @PutMapping("/update")
    private ResponseEntity<Book> updateBook(@Valid @RequestBody BookUpdateRequest bookUpdateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(bindingResult);
        }

        Book book = bookService.updateBook(bookUpdateRequest);
        logger.debug("Book is updated with id: {}", book.getId());
        return ResponseEntity.ok(book);
    }

    @PutMapping("/updateStock")
    private ResponseEntity<Book> updateBookStock(@Valid @RequestBody BookStockUpdateRequest bookStockUpdateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(bindingResult);
        }

        Book book = bookService.updateBookStock(bookStockUpdateRequest);
        logger.debug("Book stock is updated with id: {} and stock", book.getId(), book.getStock());
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable String bookId) {
        bookService.deleteBook(bookId);
        logger.debug("Book is deleted with id: {}", bookId);

        return ResponseEntity.ok(true);
    }
}
