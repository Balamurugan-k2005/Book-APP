package com.example.BookApp.service;

import com.example.BookApp.dto.BookRequest;
import com.example.BookApp.dto.BookResponse;
import com.example.BookApp.model.Book;
import com.example.BookApp.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addBook(BookRequest bookRequest) {
        Book book = modelMapper.map(bookRequest, Book.class);
        book.setIsbn(GenerateISBN());
        bookRepository.save(book);
    }

    @Override
    public void updateBook(int id,BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Book not found with id: " + id)
        );
        modelMapper.map(bookRequest, existingBook);
        bookRepository.save(existingBook);

    }

    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookResponse getBookById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> modelMapper.map(book, BookResponse.class)).toList();
    }

    @Override
    public List<BookResponse> getBookByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        return books.stream().map(book -> modelMapper.map(book, BookResponse.class)).toList();
    }

    private String GenerateISBN(){
        return UUID.randomUUID().toString().substring(0,13);
    }
}
