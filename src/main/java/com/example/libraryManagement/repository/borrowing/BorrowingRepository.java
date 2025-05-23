package com.example.libraryManagement.repository.borrowing;

import com.example.libraryManagement.model.borrowing.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing,Long> {
    @Query("select e from Borrowing e where e.user.id =:userId")
    List<Borrowing> findByUserId(@Param("userId") Long userId);

    @Query("select e from Borrowing e where e.book.id =:bookId")
    Borrowing findByBookId(@Param("bookId") Long bookId);
}
