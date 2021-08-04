package com.quockhanh.commercemanager.controller.admin;

import com.quockhanh.commercemanager.converter.UserConverter;
import com.quockhanh.commercemanager.model.DTO.UserDTO;
import com.quockhanh.commercemanager.model.User;
import com.quockhanh.commercemanager.services.UserService;
import com.quockhanh.commercemanager.validation.EmailExistedValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    UserConverter userConverter;

    @Autowired
    private EmailExistedValidator emailExistedValidator;

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping("/get-top-5")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getTop5RecentUser() {
        return userService.getTop5User();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> temp = userService.findById(id);
        if (Objects.isNull(temp)) {
            throw new RuntimeException("User not found");
        }
        User user = new User();
        user = temp.get();
        return ResponseEntity.ok().body(userConverter.toDTO(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDto) {
        Optional<User> temp = userService.findById(id);
        if (Objects.isNull(temp)) {
            throw new RuntimeException(("User not found"));
        }
        User user = temp.get();
        user.setUsername(userDto.getUsername());
        user.setPhone(userDto.getPhone());
        user.setAddress(userDto.getAddress());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        System.out.println(userDto);

        System.out.println(user);
        userService.save(user);
        UserDTO userDTO = userConverter.toDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = new User();
        Optional<User> temp = userService.findById(id);
        if (Objects.isNull(temp)) {
            throw new RuntimeException(("User not found"));
        }
        user = temp.get();
        System.out.println(user.toString());
//        if (user.getOrders().stream().count() == 0) {
//            userService.delete(id);
//            return ResponseEntity.badRequest().body("User has no order");
//        }
        user.setDeletestatus(1);
        userService.save(user);
        return ResponseEntity.ok().body("OK");
    }
}
