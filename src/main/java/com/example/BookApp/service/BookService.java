package com.example.BookApp.service;

import com.example.BookApp.dto.BookRequest;
import com.example.BookApp.dto.BookResponse;
import com.example.BookApp.exception.BookNotFoundException;
import com.example.BookApp.exception.IdNotFoundException;

import java.util.List;

public interface BookService {
    void addBook(BookRequest bookRequest);
    void updateBook(int id,BookRequest bookRequest);
    void deleteBook(int id);

    BookResponse getBookById(int id) throws IdNotFoundException;
    List<BookResponse> getAllBooks();
    List<BookResponse> getBookByAuthor(String author) throws BookNotFoundException;
}
