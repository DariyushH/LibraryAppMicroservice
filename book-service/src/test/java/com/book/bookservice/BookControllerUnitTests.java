package com.book.bookservice;

import com.book.bookservice.controller.BookController;
import com.book.bookservice.model.Book;
import com.book.bookservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookControllerUnitTests {
    @Mock
    private BookService bookService;
    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;
    @InjectMocks
    private BookController bookController;

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        when(bookService.getAllBooks()).thenReturn(List.of(new Book(), new Book()));
        var result = bookController.getAll();
        assertEquals(2, result.size());
    }

    @Test
    void getBookById_ShouldReturnBooks() {
        Long id = 1L;
        Book mockBook = new Book();
        when(bookService.getBook(id)).thenReturn(mockBook);
        var result = bookController.getBook(id);
        assertEquals(mockBook, result);
    }

    @Test
    void uploadImage_ShouldCallService() throws Exception {
        Long id = 1L;
        MultipartFile file = new MockMultipartFile("test.jpg", new byte[10]);
        bookController.uploadImage(id, file);
        verify(bookService).uploadImage(id, file);
    }

    @Test
    void createBook_ShouldInvokeService(){
        Book book = new Book();
        bookController.createBook(book);
        verify(bookService).saveBook(book);
    }

    @Test
    void deleteBook_ShouldCallService(){
        Long id = 1L;
        bookController.deleteBook(id);
        verify(bookService).deleteBook(id);
    }
}
