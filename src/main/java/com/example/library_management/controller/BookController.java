package com.example.library_management.controller;

import com.example.library_management.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // In-memory List collection to store Book records
    private final List<Book> bookList = new ArrayList<>();

    // (b) Add a new book using @PostMapping and @RequestBody (2 Marks)
    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        if (book.getId() == null) {
            return new ResponseEntity<>("Book ID is required.", HttpStatus.BAD_REQUEST);
        }
        for (Book existing : bookList) {
            if (existing.getId().equals(book.getId())) {
                return new ResponseEntity<>("Book with ID " + book.getId() + " already exists.", HttpStatus.BAD_REQUEST);
            }
        }
        if (book.getAvailableCopies() != null && book.getAvailableCopies() < 0) {
            return new ResponseEntity<>("Available copies cannot be negative.", HttpStatus.BAD_REQUEST);
        }
        bookList.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // (c1) Retrieve all books using @GetMapping (1 Mark)
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    // (c2) Retrieve a specific book by ID using @GetMapping (1 Mark)
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Integer id) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                return new ResponseEntity<>(book, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Book not found with ID: " + id, HttpStatus.NOT_FOUND);
    }

    // (d) Update a book's details (title, author, category, availableCopies) using @PutMapping (2 Marks)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setCategory(updatedBook.getCategory());
                book.setAvailableCopies(updatedBook.getAvailableCopies());
                return new ResponseEntity<>(book, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Book not found with ID: " + id, HttpStatus.NOT_FOUND);
    }

    // (e) Delete a book by ID using @DeleteMapping (2 Marks)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId().equals(id)) {
                bookList.remove(i);
                return new ResponseEntity<>("Book deleted successfully.", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Book not found with ID: " + id, HttpStatus.NOT_FOUND);
    }
}