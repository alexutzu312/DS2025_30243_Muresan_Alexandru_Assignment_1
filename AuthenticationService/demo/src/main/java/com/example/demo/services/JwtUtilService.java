package com.example.demo.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

// AuthMicroservice/demo/src/main/java/com/example/auth/services/JwtUtilService.java
@Service
public class JwtUtilService {
    // Cheia secretă ar trebui citită din application.properties
    @Value("${jwt.secret:default-secret-key-too-short}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String getRoleFromClaims(Claims claims) {
        if (claims != null && claims.containsKey("roles")) {
            return claims.get("roles", String.class);
        }
        return null;
    }


    // Metodă pentru a genera token-ul JWT (care include rolurile/autorizările)
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .claim("userId", userPrincipal.getId()) // NOU: Adaugă ID-ul utilizatorului
                .claim("role", userPrincipal.getRole()) // Adaugă rolul ca 'claim'
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // NOU: Metodă de extragere a ID-ului din Claims
    public UUID getUserIdFromClaims(Claims claims) {
        if (claims != null && claims.containsKey("user_id")) {
            // ID-ul este Long în entitate, dar cel mai bine este transmis ca String/UUID
            // Presupunem că ID-ul este stocat ca Long, îl extragem ca atare
            Object userId = claims.get("user_id");
            if (userId instanceof Number) {
                return UUID.fromString(userId.toString()); // Conversia Long/Int în UUID (dacă entitatea e UUID)
            } else if (userId instanceof String) {
                return UUID.fromString((String) userId);
            }
        }
        return null;
    }

    // Metodă pentru a valida token-ul
    public Claims validateJwtTokenAndGetClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignInKey()).build()
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            // Loghează erorile (Invalid, Expired, Unsupported, etc.)
            return null; // Returnează null dacă validarea eșuează
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }


    // Alte metode: extractUsername, extractExpiration, isTokenExpired (folosind io.jsonwebtoken.Jwts.parser())
}
