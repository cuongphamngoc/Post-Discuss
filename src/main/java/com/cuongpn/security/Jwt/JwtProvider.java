package com.cuongpn.security.Jwt;

import com.cuongpn.entity.User;
import com.cuongpn.exception.InvalidTokenException;
import com.cuongpn.security.services.UserPrincipal;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    @Value("${jwt.data.secret.key}")
    private String SECRET_KEY ;

    @Value("${jwt.data.accessToken.expirationSecond}")
    private Long accessTokenExpirationTime;
    @Value("${jwt.data.refreshToken.expirationSecond}")
    private Long refreshTokenExpirationTime;

    public String generateAccessToken(UserPrincipal userPrincipal){
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + accessTokenExpirationTime);

        return Jwts
                .builder()
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .claim("authorities",userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .claim("id",userPrincipal.getId())
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }
    public String generateAccessToken(User user){
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + accessTokenExpirationTime);

        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .claim("authorities",user.getRoles().stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()))
                .claim("id",user.getId())
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }
    public String generateRefreshToken(UserPrincipal userPrincipal){
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + refreshTokenExpirationTime);
        return Jwts
                .builder()
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .claim("authorities",userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .claim("id",userPrincipal.getId())
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }
    public String generateRefreshToken(User user){
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + refreshTokenExpirationTime);
        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .claim("authorities",user.getRoles().stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()))
                .claim("id",user.getId())
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }
        catch (SignatureException ex) {
            throw new InvalidTokenException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new InvalidTokenException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new InvalidTokenException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new InvalidTokenException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new InvalidTokenException("JWT claims string is empty");
        }

    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    public String generateAccessTokenFromClaims(Claims claims){
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + accessTokenExpirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }
    public String generateRefreshTokenFromClaims(Claims claims){
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + refreshTokenExpirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }
}
