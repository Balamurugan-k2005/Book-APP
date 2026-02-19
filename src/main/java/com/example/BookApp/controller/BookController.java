package com.example.BookApp.controller;

import com.example.BookApp.dto.BookRequest;
import com.example.BookApp.dto.BookResponse;
import com.example.BookApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<BookResponse> getBookById(@PathVariable("id") int id){
        BookResponse bookResponse = bookService.getBookById(id);
        return new ResponseEntity<>(bookResponse,HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<Page<BookResponse>> getAllBooks(@RequestParam(required = false,defaultValue = "1") int pageNo,
                                                   @RequestParam(required = false,defaultValue = "5") int pageSize,
                                                   @RequestParam(required = false, defaultValue = "id") String SortBy,
                                                   @RequestParam(required = false, defaultValue = "ASC") String SortDir,
                                                   @RequestParam(required = false) String SearchBy
                                                   ){

        Sort sort = null;
        if(SortDir.equalsIgnoreCase("ASC")){
            sort = Sort.by(SortBy).ascending();
        }
        else{
            sort = Sort.by(SortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNo - 1,pageSize,sort);
        Page<BookResponse> bookResponses =  bookService.getAllBooks(pageable,SearchBy);
        return new ResponseEntity<>(bookResponses,HttpStatus.OK);
    }

    @GetMapping("/author/{author}")
    ResponseEntity<List<BookResponse>> getBookByAuthor(@PathVariable("author") String author){
        List<BookResponse> bookResponses = bookService.getBookByAuthor(author);
        return new ResponseEntity<>(bookResponses,HttpStatus.OK);
    }
}
