//package com.example.demo.Controller;
//
//
//import com.example.demo.IO.ResetRequest;
//import com.example.demo.Service.ResetService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
//
//@RestController
//@RequiredArgsConstructor
//public class ResetController {
//
//    private final ResetService resetService;
//
//    @PostMapping("/send-reset-otp")
//    public void sendResetOtp(@RequestParam String email){
//        try{
//            resetService.sendResetOtp(email);
//        }catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
//        }
//
//    }
//
//    @PostMapping("/reset-password")
//    public void resetPassword(@Valid @RequestBody ResetRequest request){
//        try{
//            resetService.resetPassword(request.getEmail(),request.getOtp(),request.getNewPassword());
//        }catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
//        }
//    }
//
//
//
//
//}
