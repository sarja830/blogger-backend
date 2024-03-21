package com.blog.demo.security;


import com.blog.demo.model.user.User;
import com.blog.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<com.blog.demo.model.user.User> userOptional = userRepository.findByUsername(username);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username));
        if(!user.isEnabled())
        {
            throw  new AccountStatusException("User is not enabled. Please verify your email Address.") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        // authorization check is performed here
        log.info(user.getRoleType().name());
        log.info(user.getRoleType().toString());
        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities(user.getRoleType().name()));
    }


//    #TODO changes for role has been made here
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
            log.info(role);
            return singletonList(new SimpleGrantedAuthority(role));
    }
}
