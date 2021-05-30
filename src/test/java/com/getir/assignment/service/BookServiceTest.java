package com.getir.assignment.service;

import com.getir.assignment.TestDataGenerator;
import com.getir.assignment.controller.exception.AlreadyExistsException;
import com.getir.assignment.domain.Book;
import com.getir.assignment.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static com.getir.assignment.TestDataGenerator.createBook;
import static com.getir.assignment.TestDataGenerator.getBookCreateRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void whenGetBookWithId_validRequestShouldBeReturnBook() {
        when(bookRepository.findById(any())).thenReturn(Optional.of(createBook()));
        Optional<Book> book = bookRepository.findById(any());
        assertNotNull(book);
        assertEquals(createBook(), book.get());
    }

    @Test
    public void whenGetBookWithName_validRequestShouldBeReturnBook() {
        when(bookRepository.findByName(any())).thenReturn(Optional.of(createBook()));
        Optional<Book> book = bookRepository.findByName(any());
        assertNotNull(book);
        assertEquals(createBook(), book.get());
    }

    @Test
    public void whenCreateBook_validRequestShouldBeReturnBook() {
        when(bookRepository.findByName(any())).thenReturn(null);
        Optional<Book> book = bookRepository.findByName(any());
        assertNull(book);

        //when(bookService.createBook(getBookCreateRequest())).thenReturn(createBook());
        Book createdBook = bookService.createBook(getBookCreateRequest());
        assertEquals(book, createdBook);
    }

//    @Test
//    void whenCreateBook_throwAlreadyExistsException() {
//        when(bookRepository.findByName(any())).thenReturn(Optional.of(createBook()));
//        Optional<Book> book = bookRepository.findByName(any());
//        assertNotNull(book);
//
//        when(bookService.createBook(any())).thenReturn(createBook());
//        assertThrows(AlreadyExistsException.class, () -> bookService.createBook(getBookCreateRequest()));
//
////        Throwable thrown = assertThrows(AlreadyExistsException.class, () -> bookService.createBook(null));
////        assertEquals("Attempted to withdraw 100 with a balance of 0", thrown.getMessage());
//
//    }



}