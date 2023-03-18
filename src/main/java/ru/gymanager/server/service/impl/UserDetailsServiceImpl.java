package ru.gymanager.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gymanager.server.model.UserEntity;
import ru.gymanager.server.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Scope("singleton")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userData = userRepository.findByLogin(username);
        if (userData.isPresent()) {
            UserEntity user = userData.get();
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole().name())));
            return new User(user.getLogin(), user.getPassword(), authorities);
        } else {
            return null;
        }
    }


}
