package com.example.libraryManagement.service.book;

import com.example.libraryManagement.exception.LibraryException;
import com.example.libraryManagement.model.book.Book;
import com.example.libraryManagement.repository.book.BookRepository;
import com.example.libraryManagement.viewModel.book.BookViewModel;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

   @Autowired
   private DozerBeanMapper mapper;

   @Transactional
    public Book save(BookViewModel viewModel) throws LibraryException {
        Book map = null;
        if (viewModel !=null) {
            map = mapper.map(viewModel, Book.class);
        }else{
         throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
        if (map !=null) {
            return repository.save(map);
        }else{
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    @Transactional
    public Book update(Long id, BookViewModel viewModel) throws LibraryException{
        Book book;
       if (id !=null && id !=-1){
           book=repository.findById(id).get();
           if (book !=null) {
               book.setTitle(viewModel.getTitle());
               book.setAuthor(viewModel.getAuthor());
               book.setAvailable(viewModel.isAvailable());
               repository.save(book);
           }else {
               throw new LibraryException("مقادیر به درستی وارد نشده است.");
           }
       }else {
           throw new LibraryException("مقادیر به درستی وارد نشده است.");
       }

       return book;
    }

    public Book findBook(Long id) throws LibraryException {
        if (id !=null && id !=-1){
           Book book=repository.findById(id).get();
            if (book !=null && book.getId() !=null ) {
                return book;
            }else {
                throw new LibraryException("مقادیر به درستی وارد نشده است.");
            }
        }else {
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    public List<Book> findAllBook() {
        List<Book> allBook = repository.findAll();
        if (allBook.size() > 0){
            return allBook;
        }else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public Boolean deleteBook(Long id) throws LibraryException {
        if (id !=null && id !=-1){
            Book book=repository.findById(id).get();
            if (book !=null) {
                repository.deleteById(id);
                return true;
            }else {
                throw new LibraryException("کتاب پیدا نشد.");
            }
        }else {
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    public List<Book> search(String title,String author) {
       return repository.search(title,author);
    }
}
