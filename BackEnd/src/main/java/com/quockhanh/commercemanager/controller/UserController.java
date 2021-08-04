package com.quockhanh.commercemanager.controller;

import com.quockhanh.commercemanager.converter.UserConverter;
import com.quockhanh.commercemanager.services.UserService;
import com.quockhanh.commercemanager.validation.EmailExistedValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    UserConverter userConverter;

    @Autowired
    private EmailExistedValidator emailExistedValidator;

    @PostMapping("/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody Map<String, Object> inputData) {
        String email = (String)inputData.get("email");

        Boolean bool = emailExistedValidator.emailExists(email);

        return ResponseEntity.status(HttpStatus.OK).body(bool);
    }
}
