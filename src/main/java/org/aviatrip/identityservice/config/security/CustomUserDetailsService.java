package org.aviatrip.identityservice.config.security;

import jakarta.transaction.Transactional;
import org.aviatrip.identityservice.entity.User;
import org.aviatrip.identityservice.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BadCredentialsException(null));

        return new CustomerUserDetails(user.getId().toString(), user.getPassword(), retrieveAuthorities(user));
    }

    public List<? extends GrantedAuthority> retrieveAuthorities(User user) {
        return (user.getRole() != null) ? List.of(new SimpleGrantedAuthority(user.getRole().name())) : null;
    }
}
