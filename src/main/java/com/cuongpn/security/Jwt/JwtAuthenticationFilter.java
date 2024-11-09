package com.cuongpn.security.Jwt;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.TokenBlacklistService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final TokenBlacklistService tokenBlacklistService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String token = getJwtFromCookie(request);
            if(StringUtils.hasText(token) &&
                    jwtProvider.validateToken(token) &&
                    !tokenBlacklistService.isTokenBlacklisted(token)){
                Claims claims = jwtProvider.getClaimsFromToken(token);
                String userName  = claims.getSubject();
                List<String> authorities = new ArrayList<>();
                Object authoritiesClaim = claims.get("authorities");
                if (authoritiesClaim instanceof List<?> authoritiesList) {
                    for (Object authority : authoritiesList) {
                        if (authority instanceof LinkedHashMap<?, ?> authorityMap) {
                            Object authorityValue = authorityMap.get("authority");
                            if (authorityValue instanceof String) {
                                authorities.add((String) authorityValue);
                            }

                        }
                        else if (authority instanceof String) {
                            authorities.add((String) authority);
                        }
                    }

                }
                Long userId = claims.get("id", Long.class);
                List<GrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                UserPrincipal userPrincipal = new UserPrincipal();
                userPrincipal.setId(userId);
                userPrincipal.setEmail(userName);
                userPrincipal.setAuthorities(grantedAuthorities);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userPrincipal,
                        null,
                        userPrincipal.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
        catch(Exception e){
            log.info("Fail to set authentication " + e.getMessage());
        }


        filterChain.doFilter(request,response);
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
