package com.quockhanh.commercemanager.services.ServicesImpl;

import com.quockhanh.commercemanager.converter.UserConverter;
import com.quockhanh.commercemanager.model.DTO.UserDTO;
import com.quockhanh.commercemanager.model.Role;
import com.quockhanh.commercemanager.model.User;
import com.quockhanh.commercemanager.repository.UserRepository;
import com.quockhanh.commercemanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserConverter userConverter;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<UserDTO> dtoList = new ArrayList<>();
        userRepository.findAllByOrderByIdDesc().stream().forEach(s -> {
            if (s.getDeletestatus() == 0) {
                UserDTO dto = new UserDTO();
                dto = userConverter.toDTO(s);
                dtoList.add(dto);
            }
        });
        return dtoList;
    }

    boolean isAdmid(Set<Role> set) {
        AtomicBoolean check = new AtomicBoolean(false);
        set.stream().forEach(s -> {
            if (s.getId() == 2) {
                check.set(true);
            }
        });
        return check.get();
    }

    @Override
    public List<UserDTO> getTop5User() {
        List<UserDTO> dtoList = new ArrayList<>();
        userRepository.findAllByOrderByIdDesc().stream().forEach(s -> {
            if (!isAdmid(s.getRoles())) {
                UserDTO dto = new UserDTO();
                dto = userConverter.toDTO(s);
                dtoList.add(dto);
            }
        });
        System.out.println(dtoList);
//        dtoList = userRepository.findTop5ByOrderByIdDesc();
        return dtoList;
    }

    @Override
    public Optional findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
