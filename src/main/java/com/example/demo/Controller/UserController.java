package com.example.demo.Controller;


import com.example.demo.Config.AppConstants;
import com.example.demo.payloads.UserDTO;
import com.example.demo.payloads.UserResponse;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<UserResponse> getUsers(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_USER_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder){
        UserResponse userResponse = userService.getAllUsers(pageNumber,pageSize,sortBy, sortOrder);

        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.FOUND);
    }
    @GetMapping("/public/users/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId){
        UserDTO user = userService.getUserById(userId);
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }

    @PutMapping("/public/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long userId){
        UserDTO updatedUser = userService.updateUser(userId,userDTO);

        return new ResponseEntity<UserDTO>(updatedUser,HttpStatus.OK);
    }
    @DeleteMapping("admin/users/{userId}")
    public ResponseEntity<String> deleteUser (@PathVariable Long userId){
        String status = userService.deleteUser(userId);

        return new ResponseEntity<String>(status,HttpStatus.OK);
    }


}
