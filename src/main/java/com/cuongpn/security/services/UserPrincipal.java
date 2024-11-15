package com.cuongpn.security.services;

import com.cuongpn.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

    private Long id;

    private String email;

    private String fullName;

    @JsonIgnore
    private String password;

    private String avatarUrl;

    private Collection<? extends GrantedAuthority> authorities;
    public static UserPrincipal build(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new UserPrincipal(user.getId(),user.getEmail(),user.getName(),user.getPassword(),user.getAvatarUrl(),authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserPrincipal user = (UserPrincipal) o;
        return Objects.equals(email, user.email);
    }
}
