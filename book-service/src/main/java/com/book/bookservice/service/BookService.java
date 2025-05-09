package com.book.bookservice.service;

import com.book.bookservice.model.Book;
import com.book.bookservice.repository.BookRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "image_topic";

    public BookService(BookRepository bookRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.bookRepository = bookRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    public void uploadImage(Long bookId, MultipartFile file) throws IOException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Map<String, String> message = new HashMap<>();
        message.put("bookId", bookId.toString());
        String fileId = "someFileId";
        message.put("fileId", fileId);
        kafkaTemplate.send(TOPIC, message.toString());
        book.setFileId(fileId);
        bookRepository.save(book);
    }

    public void downloadImage(Long bookId, HttpServletResponse response) throws IOException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @KafkaListener(topics = "image_topic", groupId = "book_group")
    public void listen(String message) {
        // Parse the message to extract bookId and fileId
        Map<String, String> messageMap = new HashMap<>();
        String[] pairs = message.substring(1, message.length() - 1).split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            messageMap.put(keyValue[0].trim(), keyValue[1].trim());
        }

        Long bookId = Long.parseLong(messageMap.get("bookId"));
        String fileId = messageMap.get("fileId");

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setFileId(fileId);
        bookRepository.save(book);
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