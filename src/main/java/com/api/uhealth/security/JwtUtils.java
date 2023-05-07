package com.api.uhealth.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class JwtUtils {

    private final static String ACCESS_TOKEN_SECRET = "mysecrett0k3dsdsdsdsdsdddsdsddddn";
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    public static String createToken(String nombre, String email, Collection<? extends GrantedAuthority> authorities, String role){
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000; // milisegundos
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("nombre", nombre);
        extra.put("roles", authorities);
        extra.put("role", role);


//        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

//                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))

        //generar token
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .claim("roles", role)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            List<GrantedAuthority> authorities = new ArrayList<>();

            // Obtener roles/autoridades del token
            List<Map<String, String>> roles = (List<Map<String, String>>) claims.get("roles");
            for (Map<String, String> role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.get("authority")));
            }
            return new UsernamePasswordAuthenticationToken(email, null,authorities);
        }catch (JwtException e){
            System.out.println("erroe jwtutils:" + e);
            return null;
        }
    }

    public static Claims extractUserInfoFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            System.out.println("Error en JwtUtils: " + e.getMessage());
            return null;
        }
    }

    private static SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar la clave secreta", e);
        }
    }
}
