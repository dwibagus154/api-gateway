package com.dwibagus.api.gateway.util;

import com.dwibagus.api.gateway.exception.JwtTokenMalformedException;
import com.dwibagus.api.gateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Log4j2
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;


    public Claims getClaims(final String token){
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        }catch (Exception e){
            System.out.println(e.getMessage() + " up " + e);
        }
        return null;
    }

    public void ValidateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException{
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid Jwt Signature");
//            log.error("Invalid Jwt Signature: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid formed jwt");
//            log.error("Invalid Jwt Token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("Jwt is expired");
//            log.error("Expired Jwt Token: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Jwt is not supported");
//            log.error("Unsupported Jwt token: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException(("jwt claim is Ilegal "));
//            log.error("Jwt claim string is empty: {}", ex.getMessage());
        }
    }
}