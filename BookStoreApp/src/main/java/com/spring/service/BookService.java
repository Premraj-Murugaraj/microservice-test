package com.spring.service;

import com.spring.model.Book;
import com.spring.repository.BookJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookJPARepository bookJPARepository;

    public List<Book> retrieveAllBooks(){
        return bookJPARepository.findAll();
    }

    public Book getBook(Integer id){
        return bookJPARepository.findById(id).orElse(null);
    }

    public Book addBook(Book book){
        return bookJPARepository.save(book);
    }

    public void deleteBook(Integer id){
        bookJPARepository.deleteById(id);
    }
}
