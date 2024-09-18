package com.cuongpn.security.audit;

import com.cuongpn.entity.User;
import com.cuongpn.security.services.UserPrincipal;

import com.cuongpn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<User> {

    @Autowired
    private UserService userService;

    @Override
    public Optional<User> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(principal -> {
                    if (principal instanceof UserPrincipal userPrincipal) {
                        return userService.getUserByMail(userPrincipal.getEmail()); // Truy vấn User từ cơ sở dữ liệu
                    } else {
                        return null;
                    }
                });
    }
}
