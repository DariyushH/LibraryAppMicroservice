package com.book.bookservice.controller;

import com.book.bookservice.model.Book;
import com.book.bookservice.repository.BookRepository;
import com.book.bookservice.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    private final BookRepository bookRepository;

    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<Void> uploadImage(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        bookService.uploadImage(id, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/download-image")
    public void downloadImage(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        bookService.downloadImage(id, response);
    }

    @GetMapping()
    public List<Book> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PostMapping()
    public void createBook(@RequestBody Book book) {
        bookService.saveBook(book);
    }

    @PatchMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody Book book) {
        bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
