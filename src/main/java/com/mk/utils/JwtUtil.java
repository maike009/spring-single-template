package com.mk.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String SIGN_KEY = "security#MK#@!key";

    public static String createJwt (String signKey, Long expire, Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS384, signKey) // 设置签名算法
                .setClaims(claims) // 自定义内容
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .compact();
    }

    public static Claims parseJwt(String signKey,String jwt) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * jwt重载，默认过期时间为3天，默认key
     * @return
     */
    public static String createJwt ( Map<String, Object> claims) {
        long expire = 3 * 24 * 60 * 60 * 1000;// 过期时间为3天
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS384, SIGN_KEY) // 设置签名算法
                .setClaims(claims) // 自定义内容
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .compact();

    }

    public static Claims parseJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(SIGN_KEY)
                .parseClaimsJws(jwt)
                .getBody();
    }


}
