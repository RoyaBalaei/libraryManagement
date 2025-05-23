package com.example.libraryManagement.repository.book;

import com.example.libraryManagement.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("select e from Book e where ( :title is null or lower(e.title) " +
            " like lower(concat('%',:title,'%')) and " +
            " (:author is null or lower(e.author) like lower(concat('%',:author,'%' )))) ")
    List<Book> search(@Param("title") String title, @Param("author") String author);
}
