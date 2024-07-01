package com.mk.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {


    public static String createJwt (String signKey, Long expire, Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS384, signKey) // 设置签名算法
                .setClaims(claims) // 自定义内容
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .compact();
        return jwt;
    }
    public static Claims parseJwt(String signKey,String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

//    public static void main(String[] args) {
//        /*IntStream intStream = Stream.of(32, 24, 42, 34, 53, 53, 75, 2, 31, 14, 15, 15)
//                .mapToInt(u -> u);*/
//        Stream<String> sorted = Stream.of("apple", "blue", "cherry", "pear", "hello")
//                .sorted(Comparator.comparing(String::length).reversed());
//        List.of("A","B","C","D","F","G","H").parallelStream()
//                .map(String::toLowerCase)
//                .forEachOrdered(System.out::println);
//
//    }


}
