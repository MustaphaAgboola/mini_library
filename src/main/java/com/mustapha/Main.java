package com.mustapha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/books")
public class Main {

    private final BookRepository bookRepository;

    public Main(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    record NewBookRequest(
            String bookTitle,
            String authorName,
            String genre
    ) {
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody NewBookRequest request) {
        Book book = new Book();
        book.setBookTitle(request.bookTitle());
        book.setAuthorName(request.authorName());
        book.setGenre(request.genre());
        bookRepository.save(book);
        return ResponseEntity.ok("Booked Added Successfully");
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable("bookId") Integer id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok("Book deleted Successfully");
    }

    record UpdateBookRequest(
            String bookTitle,
            String authorName,
            String genre
    ) {
    }

    @PutMapping("{bookId}")
    public ResponseEntity<String> updateBook(@PathVariable("bookId") Integer id, @RequestBody UpdateBookRequest request) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (request.bookTitle() != null) {
                book.setBookTitle(request.bookTitle());
            }
            if (request.authorName() != null) {
                book.setAuthorName(request.authorName());
            }
            if (request.genre() != null) {
                book.setGenre(request.genre());
            }
            bookRepository.save(book);
            return ResponseEntity.ok("Book updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{bookId}")
    public Optional<Book> getBooksByid(@PathVariable("bookId") Integer Id) {
        return bookRepository.findById(Id);
    }
}



