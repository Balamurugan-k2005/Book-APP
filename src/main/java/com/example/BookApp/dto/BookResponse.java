package com.example.BookApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Integer id;
    private String title;
    private String author;
    private String price;
    private String isbn;
}
