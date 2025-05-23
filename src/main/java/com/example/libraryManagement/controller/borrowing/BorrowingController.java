package com.example.libraryManagement.controller.borrowing;

import com.example.libraryManagement.exception.LibraryException;
import com.example.libraryManagement.model.borrowing.Borrowing;
import com.example.libraryManagement.service.borrowing.BorrowingService;
import com.example.libraryManagement.viewModel.borrowing.BorrowingNotReturnedViewModel;
import com.example.libraryManagement.viewModel.borrowing.BorrowingViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowing")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Borrowing save(@RequestBody BorrowingViewModel viewModel) throws LibraryException {
        return borrowingService.save(viewModel);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Borrowing findBorrowing(@PathVariable("id")Long id) throws LibraryException {
        return borrowingService.findBorrowing(id);
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Borrowing> getOverdue(){
        return borrowingService.getOverdue();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Borrowing returned(@PathVariable("id")Long id) throws LibraryException{
        return borrowingService.returned(id);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Borrowing> getByUser(@PathVariable("userId")Long userId) throws LibraryException{
        return borrowingService.getByUser(userId);
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public List<BorrowingNotReturnedViewModel> getNotReturned() throws LibraryException {
        return borrowingService.getNotReturned();
    }

}
