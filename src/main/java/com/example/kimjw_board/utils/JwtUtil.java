package com.example.kimjw_board.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.signing.key}")
    private String singingkey;

    public String createToken(int userUid, String userId, String role, Long expriedMs){
        SecretKey key = Keys.hmacShaKeyFor(singingkey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .claim("userUid", userUid)
                .claim("username", userId)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expriedMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getRole (String token){
        SecretKey key = Keys.hmacShaKeyFor(singingkey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.substring("Bearer".length()))
                .getBody()
                .get("role", String.class);
    }

    public int getUserUid (String token){
        SecretKey key = Keys.hmacShaKeyFor(singingkey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.substring("Bearer".length()))
                .getBody()
                .get("userUid", Integer.class);  // Integer 안됨 왜안되는지 코드 전문 작성해보고 찾아볼것*************************
    }

    public boolean isExpired(String token){
        SecretKey key = Keys.hmacShaKeyFor(singingkey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.substring("Bearer ".length()))
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}