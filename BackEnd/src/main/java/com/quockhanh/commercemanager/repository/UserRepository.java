package com.quockhanh.commercemanager.repository;

import java.util.List;
import java.util.Optional;

import com.quockhanh.commercemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findAllByOrderByIdDesc();
    Optional<User> findByResetToken(String resetToken);
}
