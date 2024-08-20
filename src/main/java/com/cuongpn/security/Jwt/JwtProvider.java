package com.cuongpn.security.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.data.secret.key}")
    private String jwtSecret;

    @Value("${jwt.data.accessToken.expirationSecond}")
    private Long accessTokenExpirationTime;
    @Value("${jwt.data.refreshToken.expirationSecond}")
    private Long refreshTokenExpirationTime;

    public String generateAcessToken(String username){
        Date now = new Date();
        Date expirationTimemlss = new Date(now.getTime() + accessTokenExpirationTime);
        return Jwts
                .builder()
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationTimemlss)
                .compact();
    }
    public String generateRefreshToken(String username){
        Date now = new Date();
        Date expirationTimemlss = new Date(now.getTime() + refreshTokenExpirationTime);
        return Jwts
                .builder()
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationTimemlss)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException | IllegalArgumentException  exception){

        }
        return false;
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
