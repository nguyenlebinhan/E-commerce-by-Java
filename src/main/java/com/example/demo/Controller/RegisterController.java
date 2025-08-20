//package com.example.demo.Controller;
//
//import com.example.demo.IO.RegisterRequest;
//import com.example.demo.IO.RegisterResponse;
//import com.example.demo.Service.EmailService;
//import com.example.demo.Service.RegisterService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.annotation.CurrentSecurityContext;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequiredArgsConstructor
//public class RegisterController {
//
//    private final RegisterService profileService;
//
//    private final EmailService emailService;
//
//
//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.CREATED)
//    public RegisterResponse register(@Valid @RequestBody RegisterRequest request){
//        RegisterResponse response=profileService.createProfile(request);
//        emailService.sendWelcomeEmail(response.getEmail());
//        return response;
//    }
//
//
//    @GetMapping("/profile")
//    public RegisterResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name")String email){
//        return profileService.getProfile(email);
//    }
//
//
//}
