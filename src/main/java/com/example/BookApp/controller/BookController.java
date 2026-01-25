package com.example.BookApp.controller;

import com.example.BookApp.dto.BookRequest;
import com.example.BookApp.dto.BookResponse;
import com.example.BookApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    ResponseEntity<Void> addBook(@RequestBody BookRequest bookRequest){
        bookService.addBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateBook(@PathVariable("id") int id,@RequestBody BookRequest bookRequest){
        bookService.updateBook(id,bookRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBook(@PathVariable("id") int id){
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<BookResponse> getBookById(@PathVariable("id") int id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping
    ResponseEntity<List<BookResponse>> getAllBooks(){
        return ResponseEntity.ok( bookService.getAllBooks());

    }

    @GetMapping("/author/{author}")
    ResponseEntity<List<BookResponse>> getBookByAuthor(@PathVariable("author") String author){
        return ResponseEntity.ok(bookService.getBookByAuthor(author));
    }
}
