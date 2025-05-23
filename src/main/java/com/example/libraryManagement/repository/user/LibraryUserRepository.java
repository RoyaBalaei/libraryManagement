package com.example.libraryManagement.repository.user;

import com.example.libraryManagement.model.user.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibraryUserRepository extends JpaRepository<LibraryUser,Long> {

    @Query("select e from LibraryUser e where ( (:fullName is null or " +
            " lower(e.fullName) like lower(concat('%',:fullName,'%') )) and " +
            "( :email is null or lower(e.email) like lower(concat('%',:email,'%') ) ) and " +
            "( :phone is null or lower(e.phone) like lower(concat('%',:phone,'%') ) ))")
    List<LibraryUser> search(@Param("fullName") String fullName,@Param("email") String email,@Param("phone") String phone);

    Optional<LibraryUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
