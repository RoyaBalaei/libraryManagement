package com.example.libraryManagement.service.borrowing;

import com.example.libraryManagement.exception.LibraryException;
import com.example.libraryManagement.model.book.Book;
import com.example.libraryManagement.model.borrowing.Borrowing;
import com.example.libraryManagement.model.user.LibraryUser;
import com.example.libraryManagement.repository.borrowing.BorrowingRepository;
import com.example.libraryManagement.service.book.BookService;
import com.example.libraryManagement.service.user.LibraryUserService;
import com.example.libraryManagement.viewModel.borrowing.BorrowingNotReturnedViewModel;
import com.example.libraryManagement.viewModel.borrowing.BorrowingViewModel;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BorrowingService {
    @Autowired
    private BorrowingRepository repository;

    @Autowired
    private BookService bookService;

    @Autowired
    private LibraryUserService libraryUserService;

    @Autowired
    private DozerBeanMapper mapper;

    @Transactional
    public Borrowing save(BorrowingViewModel viewModel) throws LibraryException {
        if (viewModel != null) {
            boolean canUserBorrow = canUserBorrow(viewModel.getUserId());
            if (canUserBorrow) {
                Book book = bookService.findBook(viewModel.getBookId());
                Borrowing hasBook = repository.findByBookId(book.getId());
                System.out.println();
                if (!hasBook.isReturned()) {
                    throw new LibraryException("کتاب امانت می باشد.");
                } else {
                    Borrowing map = mapper.map(viewModel, Borrowing.class);
                    map.setBook(book);
                    map.setUser(libraryUserService.findLibraryUser(viewModel.getUserId()));
                    map.setBorrowingDate(LocalDate.now());
                    map.setDueDate(LocalDate.now().plusDays(14));
                    map.setReturned(false);
                    return repository.save(map);
                }
            } else {
                throw new LibraryException("کاربر بیش از 3 کتاب دارد.");
            }
        } else {
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    public boolean canUserBorrow(Long userId) {
        return repository.findByUserId(userId).stream().filter(b -> !b.isReturned()).toList().size() < 3;
    }

    public List<Borrowing> getOverdue() {
        return repository.findAll().stream().toList().stream()
                .filter(b -> !b.isReturned() && b.getDueDate().isBefore(LocalDate.now())).toList();
    }

    public Borrowing findBorrowing(Long id) throws LibraryException {
        if (id != null && id != -1) {
            Borrowing borrowing = repository.findById(id).get();
            if (borrowing != null) {
                return borrowing;
            } else {
                throw new LibraryException("مقادیر به درستی وارد نشده است.");
            }
        } else {
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    @Transactional
    public Borrowing returned(Long id) throws LibraryException {
        if (id != null && id != -1) {
            Borrowing borrowing = repository.findById(id).get();
            if (borrowing != null) {
                borrowing.setReturned(true);
                borrowing.setReturnDate(LocalDate.now());
                return repository.save(borrowing);
            } else {
                throw new LibraryException("مقادیر به درستی وارد نشده است.");
            }
        } else {
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    public List<Borrowing> getByUser(Long id) throws LibraryException {
        if (id != null && id != -1) {
            LibraryUser user=libraryUserService.findLibraryUser(id);
            if (user !=null){
                return repository.findByUserId(user.getId()).stream().filter(b->!b.isReturned()).toList();
            }else {
                throw new LibraryException("مقادیر به درستی وارد نشده است.");
            }
        }else {
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    public List<BorrowingNotReturnedViewModel> getNotReturned() throws LibraryException {
        List<BorrowingNotReturnedViewModel> listBorrowing=new ArrayList<>();
        List<Borrowing> list = repository.findAll().stream().filter(b -> !b.isReturned()).toList();
        if (list.size() >0) {
            for (Borrowing borrowing : list) {
                BorrowingNotReturnedViewModel viewModel=new BorrowingNotReturnedViewModel();
                LibraryUser libraryUser = libraryUserService.findLibraryUser(borrowing.getUser().getId());
                Book book = bookService.findBook(borrowing.getBook().getId());
                viewModel.setFullName(libraryUser.getFullName());
                viewModel.setPhone(libraryUser.getPhone());
                viewModel.setBookTitle(book.getTitle());
                viewModel.setBorrowingDate(borrowing.getBorrowingDate());
                viewModel.setDueDate(borrowing.getDueDate());
                listBorrowing.add(viewModel);
            }
        }
        return listBorrowing;
    }
}
