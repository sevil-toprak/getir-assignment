package com.getir.assignment.controller;

import com.getir.assignment.domain.Book;
import com.getir.assignment.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    TestRestTemplate testRestTemplate;

    @Mock
    BookService bookService;

    @BeforeEach
    void setUp() {
    }

//    @Test
//    public void shouldReturnBookList() {
//        ResponseEntity<Page<Book>> response = testRestTemplate.exchange("/api/book/", HttpMethod.GET, null,
//                new ParameterizedTypeReference<Page<Book>>() {
//                });
//        assertThat(response.getStatusCodeValue()).isEqualTo(200);
//    }

}