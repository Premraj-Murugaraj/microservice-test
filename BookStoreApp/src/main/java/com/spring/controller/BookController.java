package com.spring.controller;

import com.spring.model.Book;
import com.spring.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    BookService bookService;


    @GetMapping(path = "/books", produces = "application/json")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(bookService.retrieveAllBooks(), HttpStatus.OK);
    }

    @GetMapping(path = "/book/{id}", produces = "application/json")
    public ResponseEntity<Book> getBook(@Valid @PathVariable Integer id){
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
    }

    @PostMapping(path = "/create/book", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
        Book newBook = bookService.getBook(book.getId());
        return newBook != null ? ResponseEntity.internalServerError().body(newBook) : new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete/book/{id}")
    public ResponseEntity deleteBook(@Valid @PathVariable Integer id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
