package com.ivan.pazar.web.web_service;

import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.persistence.repository.UserRepository;
import com.ivan.pazar.web.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String USERNAME_NOT_FOUND = "Username not found";

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        return optionalUser
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));
    }
}
