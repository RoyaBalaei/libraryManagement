package com.example.libraryManagement.controller.book;

import com.example.libraryManagement.exception.LibraryException;
import com.example.libraryManagement.model.book.Book;
import com.example.libraryManagement.service.book.BookService;
import com.example.libraryManagement.viewModel.book.BookViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book save(@RequestBody BookViewModel book) throws LibraryException {
       return bookService.save(book);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Book update(@PathVariable("id") Long id,@RequestBody BookViewModel book) throws LibraryException{
        return bookService.update(id,book);
    }

    @GetMapping("/{id}")
    public Book findBook(@PathVariable("id") Long id) throws LibraryException{
        return bookService.findBook(id);
    }

    @GetMapping
    public List<Book> findAllBook(){
        return bookService.findAllBook();
    }

    @DeleteMapping("/{id}")
    public Boolean deleteBook(@PathVariable("id") Long id) throws LibraryException{
        return bookService.deleteBook(id);
    }

    @GetMapping("/search")
    public List<Book> search(String title,String author){
        return bookService.search(title,author);
    }
}
