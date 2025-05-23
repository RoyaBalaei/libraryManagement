package com.example.libraryManagement.service.user;

import com.example.libraryManagement.exception.LibraryException;
import com.example.libraryManagement.model.user.LibraryUser;
import com.example.libraryManagement.model.user.Role;
import com.example.libraryManagement.repository.user.LibraryUserRepository;
import com.example.libraryManagement.viewModel.user.LibraryUserViewModel;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryUserService {

    @Autowired
    private LibraryUserRepository repository;

    @Autowired
    private DozerBeanMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public LibraryUser saveLibraryUser(LibraryUserViewModel viewModel) throws LibraryException {
        LibraryUser map=null;
        if (viewModel !=null){
            map=mapper.map(viewModel,LibraryUser.class);
            if (map.getEmail() !=null && !map.getEmail().isEmpty()){
                if (repository.existsByEmail(map.getEmail())){
                    throw new LibraryException("ایمیل وارد شده تکراری می باشد.");
                }else {
                    map.setPassword(passwordEncoder.encode(map.getPassword()));
                    if (viewModel.getRole().equals(1) ){
                        map.setRole(Role.ADMIN);
                    }else {
                        map.setRole(Role.USER);
                    }
                    map.setActive(true);
                    return repository.save(map);
                }
            }else {
                throw new LibraryException("ایمیل وارد نشده است.");
            }
        }else {
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    @Transactional
    public LibraryUser update(Long id, LibraryUserViewModel viewModel) throws LibraryException {
        LibraryUser libraryUser;
        if (id !=null && id !=-1){
            libraryUser=repository.findById(id).get();
            if (libraryUser !=null){
                if (viewModel.getFullName() !=null && !viewModel.getFullName().isEmpty()) {
                    libraryUser.setFullName(viewModel.getFullName());
                }
                if (viewModel.getEmail() !=null && !viewModel.getEmail().isEmpty()) {
                    libraryUser.setEmail(viewModel.getEmail());
                }
                if (viewModel.getPassword() !=null && !viewModel.getPassword().isEmpty()) {
                    libraryUser.setPassword(passwordEncoder.encode(viewModel.getPassword()));
                }
                if (viewModel.getPhone() !=null && !viewModel.getPhone().isEmpty()) {
                    libraryUser.setPhone(viewModel.getPhone());
                }
                if (viewModel.getActive() !=null) {
                    libraryUser.setActive(viewModel.getActive());
                }

                return repository.save(libraryUser);
            }else {
                throw new LibraryException("مقادیر به درستی وارد نشده است.");
            }
        }else {
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    public LibraryUser findLibraryUser(Long id) throws LibraryException {
        if (id !=null && id !=-1){
            LibraryUser libraryUser=repository.findById(id).get();
            if (libraryUser !=null && libraryUser.getId() !=null){
                return libraryUser;
            }else {
                throw new LibraryException("مقادیر به درستی وارد نشده است.");
            }
        }else {
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    public List<LibraryUser> findAllLibraryUser() {
        List<LibraryUser> allLibraryUser = repository.findAll();
        if (allLibraryUser.size() > 0){
            return allLibraryUser;
        }else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public Boolean deleteLibraryUser(Long id) throws LibraryException {
        if (id !=null && id !=-1){
            LibraryUser libraryUser=repository.findById(id).get();
            if (libraryUser !=null && libraryUser.getId() !=null){
                repository.deleteById(id);
                return true;
            }else {
                throw new LibraryException("کاربر پیدا نشد.");
            }
        }else {
            throw new LibraryException("مقادیر به درستی وارد نشده است.");
        }
    }

    public List<LibraryUser> search(String fullName, String email, String phone) {
        return repository.search(fullName,email,phone);
    }

    public Optional<LibraryUser> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
