package com.example.evaluation.global.jwt;



import com.example.evaluation.global.BaseException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.example.evaluation.global.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class JwtService {

    private Logger logger= LoggerFactory.getLogger(JwtService.class);
    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /*
    JWT 생성
    @param userId
    @return String
     */
    public String createJwt(Long userId){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userId",userId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }


    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
    JWT에서 userId 추출
    @return int
    @throws BaseException
     */
    public Long getUserId() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(DATABASE_ERROR);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)  // 수정: 올바른 서명 키 사용
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(DATABASE_ERROR);
        }

        // 3. userId 추출
        return claims.getBody().get("userId",Long.class);
    }

}