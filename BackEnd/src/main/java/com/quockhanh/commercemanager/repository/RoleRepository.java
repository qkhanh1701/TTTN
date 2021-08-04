package com.quockhanh.commercemanager.repository;

import java.util.Optional;

import com.quockhanh.commercemanager.model.ERole;
import com.quockhanh.commercemanager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
