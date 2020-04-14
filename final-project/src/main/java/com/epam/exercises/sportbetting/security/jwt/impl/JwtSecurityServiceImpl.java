package com.epam.exercises.sportbetting.security.jwt.impl;

import com.epam.exercises.sportbetting.domain.user.User;
import com.epam.exercises.sportbetting.security.jwt.JwtSecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtSecurityServiceImpl implements JwtSecurityService {
    private static final String TOKEN_BLACK_LIST = "black_list";
    private static final int JWT_TOKEN_EXPIRATION = 5 * 60 * 60 * 1000; //5 hours

    private String key;
    private RedisTemplate<String, String> redisTemplate;
    private List<String> blackList;

    @Autowired
    public JwtSecurityServiceImpl(@Value("${jwt.secret}") String key, RedisTemplate<String, String> redisTemplate) {
        this.key = key;
        this.redisTemplate = redisTemplate;
        this.blackList = new ArrayList<>();
    }

    @Override
    public String generateJwt(User user) {
        Map<String, Object> claims = buildClaims(user);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    @Override
    public String getUserLoginFromJwt(String jwt) {
        return getClaimFromToken(Claims::getSubject, jwt);
    }

    @Override
    public boolean validateJwt(String login, String jwt) {
        String userLoginFromJwt = getUserLoginFromJwt(jwt);

        if(isTokenExpired(jwt)) {
            addJwtToBlackList(jwt);
            return false;
        }

        return userLoginFromJwt.equals(login);
    }

    @Override
    public void addJwtToBlackList(String jwt) {
        blackList.add(jwt);
        redisTemplate.opsForList().rightPushAll(TOKEN_BLACK_LIST, blackList);
    }

    @Override
    public boolean isJwtInBlackList(String jwt) {
        return Objects.requireNonNull(redisTemplate.opsForList().range(TOKEN_BLACK_LIST, 0, -1)).contains(jwt);
    }

    private Boolean isTokenExpired(String jwt) {
        final Date expiration = getClaimFromToken(Claims::getExpiration, jwt);
        return expiration.before(new Date());
    }

    private <T> T getClaimFromToken(Function<Claims, T> function, String jwt) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return function.apply(claims);
    }

    private Map<String, Object> buildClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("userLogin", user.getEmail());
        return claims;
    }

}
