package com.example.libraryManagement.controller.login;

import com.example.libraryManagement.dto.AuthRequest;
import com.example.libraryManagement.dto.AuthResponse;
import com.example.libraryManagement.security.CustomUserDetails;
import com.example.libraryManagement.security.JwtUtil;
import com.example.libraryManagement.service.user.LibraryUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LibraryUserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request, HttpServletResponse response){
        authManager.authenticate
                (new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

       var userDetail= userService.findByEmail(request.getEmail()).map(CustomUserDetails::new).orElseThrow();
       String token=jwtUtil.generateToken(userDetail);
       response.setHeader("authorities","Bearer "+token);
       return ResponseEntity.ok(new AuthResponse(token));
    }


}
