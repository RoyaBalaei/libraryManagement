package com.example.libraryManagement.controller.libraryUser;

import com.example.libraryManagement.exception.LibraryException;
import com.example.libraryManagement.model.user.LibraryUser;
import com.example.libraryManagement.service.user.LibraryUserService;
import com.example.libraryManagement.viewModel.user.LibraryUserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libraryUser")
public class LibraryUserController {
    @Autowired
    private LibraryUserService libraryUserService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public LibraryUser save(@RequestBody LibraryUserViewModel viewModel) throws LibraryException {
        return libraryUserService.saveLibraryUser(viewModel);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public LibraryUser update(@PathVariable("id") Long id,@RequestBody LibraryUserViewModel viewModel) throws LibraryException {
        return libraryUserService.update(id,viewModel);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public LibraryUser findLibraryUser(@PathVariable("id") Long id) throws LibraryException {
        return libraryUserService.findLibraryUser(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<LibraryUser> findAllLibraryUser(){
        return libraryUserService.findAllLibraryUser();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteLibraryUser(@PathVariable("id") Long id) throws LibraryException {
        return libraryUserService.deleteLibraryUser(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public List<LibraryUser> search(String fullName,String email,String phone){
        return libraryUserService.search(fullName,email,phone);
    }

}
