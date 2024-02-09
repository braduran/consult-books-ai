package com.braduran.chat;

import com.braduran.model.Book;
import com.braduran.model.Completion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {

    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService){
        this.booksService = booksService;
    }

    @GetMapping("/ai/book")
    Completion generate(@RequestParam(value = "country", defaultValue = "colombia") String country,
                               @RequestParam(value = "genre", defaultValue = "literature") String genre){
        return new Completion(booksService.retrieve(country, genre));
    }

    @GetMapping("/ai/book/output")
    Book generateOutput(@RequestParam(value = "country", defaultValue = "colombia") String country,
                         @RequestParam(value = "genre", defaultValue = "literature") String genre){
        return booksService.retrieveWithOutput(country, genre);
    }
}
