package com.example.yeefstore.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
//生成token的jwt
@Component
public class JwtUtil {
    /**
     * JWT密钥
     */
    private static final SecretKey secret = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    /**
     * Token过期时间
     */
//过期时间为2天
    private final long expire = 2 * 24 * 60 * 60 * 1000L;


    /**
     * 生成Token
     * @param username 用户名
     * @return Token字符串
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expire);

        return Jwts.builder()
                .setSubject(username)  // 设置主题（用户名）
                .setIssuedAt(now)      // 设置签发时间
                .setExpiration(expiration)  // 设置过期时间
                .signWith(secret,SignatureAlgorithm.HS256)  // 签名算法和密钥
                .compact();
    }
    /**
     * 从Token中获取用户名
     * @param token Token字符串
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * 验证Token是否有效
     * @param token Token字符串
     * @return true-有效，false-无效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
