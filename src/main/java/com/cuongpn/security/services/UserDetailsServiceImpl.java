package com.cuongpn.security.services;

import com.cuongpn.entity.User;
import com.cuongpn.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepo userRepo;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmailAndIsVerification(email,true).orElseThrow(() -> new UsernameNotFoundException("User" +
                " not found with mail: "+email));

        return UserPrincipal.build(user);
    }
}
