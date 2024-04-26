package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UserAuthenticationException;
import com.marcomarchionni.strategistapi.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // This is a temporary solution to facilitate testing
        // TODO: remove this when the application is ready for production
        if (username.equals("test.admin")) {
            return User.builder().email("test.admin").password("test.admin").role(User.Role.ADMIN).build();
        }
        // End of temporary solution
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        } else {
            return User.builder().email("marco99@gmail.com").password("password").role(User.Role.ADMIN)
                    .accountId("U1111111").build();
        }
    }

    @Override
    public String getUserAccountId() {
        String accountId = getAuthenticatedUser().getAccountId();
        if (accountId != null) {
            return accountId;
        } else {
            throw new UserAuthenticationException("User account id not found. Please login.");
        }
    }
}
