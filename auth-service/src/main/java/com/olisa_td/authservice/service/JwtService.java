package com.olisa_td.authservice.service;


import com.olisa_td.authservice.dto.TokenResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;


    public String generateToken(String id, String role) {
        return Jwts.builder()
                .setSubject(id)
                .claim("role", role)
                .setIssuer("Olisa")
                .setAudience("justABank")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1_800_000))
                .signWith(getSignInKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        return claims;
    }


    public TokenResponse getEmailAndRoleFromToken(String token) {
        Claims claims = extractAllClaims(token);

        if(claims != null) {
            String id =  claims.getSubject();
            String role = claims.get("role",String.class);

           return new TokenResponse(id,role);

        }
        return null;
    }


    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (SignatureException e) {
            throw new JwtException("Invalid JWT signature");
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT");
        }
    }



    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
