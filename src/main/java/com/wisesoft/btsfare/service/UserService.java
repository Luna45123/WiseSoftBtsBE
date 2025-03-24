package com.wisesoft.btsfare.service;

import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wisesoft.btsfare.entity.Role;
import com.wisesoft.btsfare.entity.User;
import com.wisesoft.btsfare.modal.RegisterRequest;
import com.wisesoft.btsfare.repository.RoleRepository;
import com.wisesoft.btsfare.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

  

     public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public String registerUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // กำหนด Role เป็น ROLE_USER โดย Default
        Role userRole = roleRepository.findByName(Role.RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Default Role not found"));

        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

        return "";
    }
}
