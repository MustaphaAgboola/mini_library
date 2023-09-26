package com.test.minilibrary.repository;//package com.test.minilibrary.repository;


import com.mustapha.Book;
import com.mustapha.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTest {

    private BookRepository bookRepository;
    @Test
    public void BookRepository_SaveAll_ReturnSavedBook(){
//        Arrange
        Book book = Book.builder()
                .bookTitle("Gifted Hands")
                .authorName("Ben Carson")
                .genre("Biography")
                .build();

//        Act
        Book savedBook = bookRepository.save(book);

//        Assert
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isGreaterThan(0);
    }

    @Test
    public void BookRepository_GetAll_ReturnMoreThanOneBook(){
        Book book = Book.builder()
                .bookTitle("Gifted Hands")
                .authorName("Ben Carson")
                .genre("Biography")
                .build();
        Book book2 = Book.builder()
                .bookTitle("Rich dad, Poor dad")
                .authorName("Robert Kiyosaki")
                .genre("Finance")
                .build();

        bookRepository.save(book);
        bookRepository.save(book2);

        List<Book> bookList = bookRepository.findAll();

        Assertions.assertThat(bookList).isNotNull();
        Assertions.assertThat(bookList.size()).isEqualTo(2);
    }
    @Test
    public void BookRepository_FindById_ReturnBook(){
        Book book = Book.builder()
                .bookTitle("Gifted Hands")
                .authorName("Ben Carson")
                .genre("Biography")
                .build();


        bookRepository.save(book);
       Book savedBook =  bookRepository.findById(book.getId()).get();

        Assertions.assertThat(savedBook).isNotNull();

    }
    @Test
    public void BookRepository_UpdateById_ReturnBook(){
        Book book = Book.builder()
                .bookTitle("Gifted Hands")
                .authorName("Ben Carson")
                .genre("Biography")
                .build();

        bookRepository.save(book);

        Book bookSave = bookRepository.findById(book.getId()).get();
        bookSave.setBookTitle("GIFTED HANDS");
        bookSave.setAuthorName("BEN CARSON");

        Book updatedBook = bookRepository.save(bookSave);

        Assertions.assertThat(bookSave.getBookTitle()).isNotNull();
        Assertions.assertThat(bookSave.getAuthorName()).isNotNull();
    }

    @Test
    public void BookRepository_deleteById_ReturnEmptyBook(){
        Book book = Book.builder()
                .bookTitle("Gifted Hands")
                .authorName("Ben Carson")
                .genre("Biography")
                .build();

        bookRepository.save(book);
        bookRepository.deleteById(book.getId());
        Optional<Book> bookReturn = bookRepository.findById(book.getId());
        Assertions.assertThat(bookReturn).isEmpty();

    }
}


