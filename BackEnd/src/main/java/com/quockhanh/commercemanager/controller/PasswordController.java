package com.quockhanh.commercemanager.controller;

import com.quockhanh.commercemanager.message.response.MessageResponse;
import com.quockhanh.commercemanager.model.User;
import com.quockhanh.commercemanager.services.EmailService;
import com.quockhanh.commercemanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class PasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/forgot/{email}")
    public ResponseEntity<?> processForgotPassword(@PathVariable String email, HttpServletRequest request){
        Optional<User> optional = userService.findUserByEmail(email);
        if (!optional.isPresent()) {
            return ResponseEntity.ok(new MessageResponse("Email not exist"));
        } else {
            User user = optional.get();
            user.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            userService.save(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("tranminhtuannhj@gmail.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reset?token=" + user.getResetToken());
            emailService.sendEmail(passwordResetEmail);
            return ResponseEntity.ok(new MessageResponse("Send mail successfully!"));
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> setNewPassword(@RequestParam Map<String, String> requestParams){
        Optional<User> user = userService.findUserByResetToken(requestParams.get("token"));
        if (user.isPresent()) {
            User resetUser = user.get();
            resetUser.setPassword(encoder.encode(requestParams.get("password")));
            resetUser.setResetToken(null);
            userService.save(resetUser);
            return ResponseEntity.ok(new MessageResponse("Change password successfully!"));
        } else {
            return ResponseEntity.ok(new MessageResponse("User not exist"));
        }
    }

    /*@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}
