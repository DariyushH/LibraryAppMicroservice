package com.book.bookservice.service;

import com.book.bookservice.model.Book;
import com.book.bookservice.repository.BookRepository;
import com.book.bookservice.service.client.ImageServiceClient;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ImageServiceClient imageServiceClient;

    public BookService(BookRepository bookRepository, ImageServiceClient imageServiceClient) {
        this.bookRepository = bookRepository;
        this.imageServiceClient = imageServiceClient;
    }

    public void uploadImage(Long bookId, MultipartFile file) throws IOException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        String fileId = imageServiceClient.uploadImage(bookId, file);
        book.setFileId(fileId);
        bookRepository.save(book);
    }

    public void downloadImage(Long bookId, HttpServletResponse response) throws IOException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        imageServiceClient.downloadImage(book.getFileId());
    }

    @Transactional
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void updateBook(Long id, Book book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book bookToBeUpdated = optionalBook.get();
            bookToBeUpdated.setName(bookToBeUpdated.getName());
            bookToBeUpdated.setAuthor(bookToBeUpdated.getAuthor());
            bookToBeUpdated.setDescription(bookToBeUpdated.getDescription());

            bookRepository.save(bookToBeUpdated);
        }
    }
}