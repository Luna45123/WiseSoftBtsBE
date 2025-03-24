package com.wisesoft.btsfare.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.wisesoft.btsfare.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;

        public CustomUserDetailsService(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                com.wisesoft.btsfare.entity.User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                List<GrantedAuthority> authorities = user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                                .collect(Collectors.toList());

                return new org.springframework.security.core.userdetails.User(
                                user.getUsername(),
                                user.getPassword(),
                                authorities);
        }
}
