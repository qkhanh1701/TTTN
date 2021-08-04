package com.quockhanh.commercemanager.services;
import com.quockhanh.commercemanager.model.DTO.UserDTO;
import com.quockhanh.commercemanager.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
    List<UserDTO> getAllUser();
    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserByResetToken(String resetToken);
    public void save(User user);
    void delete(Long id);
    List<UserDTO> getTop5User();
//    List<User> getAllUsers();
//    User findByUsername(String username);
//    User save(User user);
//    void deleteByUsername(String delete);
//    void delete(User user);
//    List<User> getUserByUsername(String username);
//    List<User> getUserByFirstname(String firstname);
//    List<User> getUserByLastname(String lastname);
//    List<User> getUserByEmail(String email);
}
