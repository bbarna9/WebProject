package com.myproject;

import com.myproject.Model.Book;
import com.myproject.Repo.BookRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)

public class BookRepoTest {
    @Autowired private BookRepo repo;

    @Test
    public void testAddNew(){
        Book book = new Book();
        book.setTitle("Title");
        book.setAuthor("An author");
        book.setGenre("Some genre");
        book.setReleaseYear(1234);

        Book savedBook = repo.save(book);

        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<Book> books = repo.findAll();
        Assertions.assertThat(books).hasSizeGreaterThan(0);

        for(Book book : books){
            System.out.println(book);
        }
    }

    @Test
    public void testUpdate(){
        Integer bookId = 1;
        Optional<Book> optionalBook = repo.findById(bookId);
        Book book = optionalBook.get();
        book.setReleaseYear(4321);
        repo.save(book);

        Book updatedBook = repo.findById(bookId).get();
        Assertions.assertThat(updatedBook.getReleaseYear()).isEqualTo(4321);
    }

    @Test
    public void testGet(){
        Integer bookId = 1;
        Optional<Book> optionalBook = repo.findById(bookId);
        Assertions.assertThat(optionalBook).isPresent();
        System.out.println(optionalBook.get() );
    }

    @Test
    public void testDelete(){
        Integer bookId = 1;
        repo.deleteById(bookId);

        Optional<Book> optionalBook = repo.findById(bookId);
        Assertions.assertThat(optionalBook).isNotPresent();
    }
}
