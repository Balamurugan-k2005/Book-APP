package com.example.BookApp.service;

import com.example.BookApp.dto.BookRequest;
import com.example.BookApp.dto.BookResponse;
import com.example.BookApp.exception.BookNotFoundException;
import com.example.BookApp.exception.IdNotFoundException;
import com.example.BookApp.model.Book;
import com.example.BookApp.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void addBook(BookRequest bookRequest) {
        Book book = modelMapper.map(bookRequest, Book.class);
        book.setIsbn(GenerateISBN());
        bookRepository.save(book);
    }

    public void updateBook(int id,BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Book not found with id: " + id)
        );
        modelMapper.map(bookRequest, existingBook);
        bookRepository.save(existingBook);

    }

    public void deleteBook(int id) {
        boolean book = bookRepository.existsById(id);
        if(!book)
            throw new IdNotFoundException("Book not found with this id : " + id);
        bookRepository.deleteById(id);
    }

    public BookResponse getBookById(int id) {
        Book book = bookRepository.findById(id).orElseThrow(()->
                new IdNotFoundException("Book not found with this id : " + id));
        return modelMapper.map(book, BookResponse.class);
    }

    public Page<BookResponse> getAllBooks(Pageable pageable,String SearchBy) {
        Page<Book> books;

        if(SearchBy == null){
            books = bookRepository.findAll(pageable);
        }
        else{
            books = bookRepository.findByAuthorContainingIgnoreCase(SearchBy, pageable);
        }

        return books
                .map( book -> modelMapper.map(book, BookResponse.class));
    }

    public List<BookResponse> getBookByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);

        if(books.isEmpty()){
            throw  new BookNotFoundException("no Book found with the author name : " + author);
        }
        return books.stream().map(book ->
                modelMapper.map(book, BookResponse.class)).toList();
    }

    private String GenerateISBN(){
        return UUID.randomUUID().toString().substring(0,13);
    }
}
